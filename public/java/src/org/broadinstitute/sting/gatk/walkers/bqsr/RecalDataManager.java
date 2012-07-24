/*
 * Copyright (c) 2010 The Broad Institute
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.broadinstitute.sting.gatk.walkers.bqsr;

import org.apache.log4j.Logger;
import org.broadinstitute.sting.gatk.report.GATKReport;
import org.broadinstitute.sting.gatk.report.GATKReportTable;
import org.broadinstitute.sting.utils.BaseUtils;
import org.broadinstitute.sting.utils.R.RScriptExecutor;
import org.broadinstitute.sting.utils.Utils;
import org.broadinstitute.sting.utils.classloader.PluginManager;
import org.broadinstitute.sting.utils.collections.NestedIntegerArray;
import org.broadinstitute.sting.utils.collections.NestedHashMap;
import org.broadinstitute.sting.utils.collections.Pair;
import org.broadinstitute.sting.utils.exceptions.DynamicClassResolutionException;
import org.broadinstitute.sting.utils.exceptions.ReviewedStingException;
import org.broadinstitute.sting.utils.exceptions.UserException;
import org.broadinstitute.sting.utils.io.Resource;
import org.broadinstitute.sting.utils.recalibration.RecalibrationTables;
import org.broadinstitute.sting.utils.sam.GATKSAMReadGroupRecord;
import org.broadinstitute.sting.utils.sam.GATKSAMRecord;
import org.broadinstitute.sting.utils.sam.ReadUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: rpoplin
 * Date: Nov 6, 2009
 *
 * This helper class holds the data HashMap as well as submaps that represent the marginal distributions collapsed over all needed dimensions.
 * It also has static methods that are used to perform the various solid recalibration modes that attempt to correct the reference bias.
 * This class holds the parsing methods that are shared between CountCovariates and TableRecalibration.
 */

public class RecalDataManager {
    public final static String ARGUMENT_REPORT_TABLE_TITLE = "Arguments";
    public final static String QUANTIZED_REPORT_TABLE_TITLE = "Quantized";
    public final static String READGROUP_REPORT_TABLE_TITLE = "RecalTable0";
    public final static String QUALITY_SCORE_REPORT_TABLE_TITLE = "RecalTable1";
    public final static String ALL_COVARIATES_REPORT_TABLE_TITLE = "RecalTable2";

    public final static String ARGUMENT_VALUE_COLUMN_NAME = "Value";
    public final static String QUANTIZED_VALUE_COLUMN_NAME = "QuantizedScore";
    public static final String QUANTIZED_COUNT_COLUMN_NAME = "Count";
    public final static String READGROUP_COLUMN_NAME = "ReadGroup";
    public final static String EVENT_TYPE_COLUMN_NAME = "EventType";
    public final static String EMPIRICAL_QUALITY_COLUMN_NAME = "EmpiricalQuality";
    public final static String ESTIMATED_Q_REPORTED_COLUMN_NAME = "EstimatedQReported";
    public final static String QUALITY_SCORE_COLUMN_NAME = "QualityScore";
    public final static String COVARIATE_VALUE_COLUMN_NAME = "CovariateValue";
    public final static String COVARIATE_NAME_COLUMN_NAME = "CovariateName";
    public final static String NUMBER_OBSERVATIONS_COLUMN_NAME = "Observations";
    public final static String NUMBER_ERRORS_COLUMN_NAME = "Errors";

    private final static String COLOR_SPACE_ATTRIBUTE_TAG = "CS";                            // The tag that holds the color space for SOLID bams
    private final static String COLOR_SPACE_INCONSISTENCY_TAG = "ZC";                        // A new tag made up for the recalibrator which will hold an array of ints which say if this base is inconsistent with its color
    private static boolean warnUserNullPlatform = false;

    private static final String SCRIPT_FILE = "BQSR.R";

    private static final Pair<String, String> covariateValue     = new Pair<String, String>(RecalDataManager.COVARIATE_VALUE_COLUMN_NAME, "%s");
    private static final Pair<String, String> covariateName      = new Pair<String, String>(RecalDataManager.COVARIATE_NAME_COLUMN_NAME, "%s");
    private static final Pair<String, String> eventType          = new Pair<String, String>(RecalDataManager.EVENT_TYPE_COLUMN_NAME, "%s");
    private static final Pair<String, String> empiricalQuality   = new Pair<String, String>(RecalDataManager.EMPIRICAL_QUALITY_COLUMN_NAME, "%.4f");
    private static final Pair<String, String> estimatedQReported = new Pair<String, String>(RecalDataManager.ESTIMATED_Q_REPORTED_COLUMN_NAME, "%.4f");
    private static final Pair<String, String> nObservations      = new Pair<String, String>(RecalDataManager.NUMBER_OBSERVATIONS_COLUMN_NAME, "%d");
    private static final Pair<String, String> nErrors            = new Pair<String, String>(RecalDataManager.NUMBER_ERRORS_COLUMN_NAME, "%d");


    public enum SOLID_RECAL_MODE {
        /**
         * Treat reference inserted bases as reference matching bases. Very unsafe!
         */
        DO_NOTHING,
        /**
         * Set reference inserted bases and the previous base (because of color space alignment details) to Q0. This is the default option.
         */
        SET_Q_ZERO,
        /**
         * In addition to setting the quality scores to zero, also set the base itself to 'N'. This is useful to visualize in IGV.
         */
        SET_Q_ZERO_BASE_N,
        /**
         * Look at the color quality scores and probabilistically decide to change the reference inserted base to be the base which is implied by the original color space instead of the reference.
         */
        REMOVE_REF_BIAS;
        
        public static SOLID_RECAL_MODE recalModeFromString(String recalMode) {
            if (recalMode.equals("DO_NOTHING"))
                return SOLID_RECAL_MODE.DO_NOTHING;
            if (recalMode.equals("SET_Q_ZERO"))
                return SOLID_RECAL_MODE.SET_Q_ZERO;
            if (recalMode.equals("SET_Q_ZERO_BASE_N"))
                return SOLID_RECAL_MODE.SET_Q_ZERO_BASE_N;
            if (recalMode.equals("REMOVE_REF_BIAS"))
                return SOLID_RECAL_MODE.REMOVE_REF_BIAS;

            throw new UserException.BadArgumentValue(recalMode, "is not a valid SOLID_RECAL_MODE value");
        }
    }

    public enum SOLID_NOCALL_STRATEGY {
        /**
         * When a no call is detected throw an exception to alert the user that recalibrating this SOLiD data is unsafe. This is the default option.
         */
        THROW_EXCEPTION,
        /**
         * Leave the read in the output bam completely untouched. This mode is only okay if the no calls are very rare.
         */
        LEAVE_READ_UNRECALIBRATED,
        /**
         * Mark these reads as failing vendor quality checks so they can be filtered out by downstream analyses.
         */
        PURGE_READ;

        public static SOLID_NOCALL_STRATEGY nocallStrategyFromString(String nocallStrategy) {
            if (nocallStrategy.equals("THROW_EXCEPTION"))
                return SOLID_NOCALL_STRATEGY.THROW_EXCEPTION;
            if (nocallStrategy.equals("LEAVE_READ_UNRECALIBRATED"))
                return SOLID_NOCALL_STRATEGY.LEAVE_READ_UNRECALIBRATED;
            if (nocallStrategy.equals("PURGE_READ"))
                return SOLID_NOCALL_STRATEGY.PURGE_READ;

            throw new UserException.BadArgumentValue(nocallStrategy, "is not a valid SOLID_NOCALL_STRATEGY value");
        }
    }

    /**
     * Generates two lists : required covariates and optional covariates based on the user's requests.
     *
     * Performs the following tasks in order:
     *  1. Adds all requierd covariates in order
     *  2. Check if the user asked to use the standard covariates and adds them all if that's the case
     *  3. Adds all covariates requested by the user that were not already added by the two previous steps
     *
     * @param argumentCollection the argument collection object for the recalibration walker
     * @return a pair of ordered lists : required covariates (first) and optional covariates (second)
     */
    public static Pair<ArrayList<Covariate>, ArrayList<Covariate>> initializeCovariates(RecalibrationArgumentCollection argumentCollection) {
        final List<Class<? extends Covariate>> covariateClasses = new PluginManager<Covariate>(Covariate.class).getPlugins();
        final List<Class<? extends RequiredCovariate>> requiredClasses = new PluginManager<RequiredCovariate>(RequiredCovariate.class).getPlugins();
        final List<Class<? extends StandardCovariate>> standardClasses = new PluginManager<StandardCovariate>(StandardCovariate.class).getPlugins();

        final ArrayList<Covariate> requiredCovariates = addRequiredCovariatesToList(requiredClasses);                   // add the required covariates
        ArrayList<Covariate> optionalCovariates = new ArrayList<Covariate>();
        if (!argumentCollection.DO_NOT_USE_STANDARD_COVARIATES)
            optionalCovariates = addStandardCovariatesToList(standardClasses);                                          // add the standard covariates if -standard was specified by the user

        if (argumentCollection.COVARIATES != null) {                                                                    // parse the -cov arguments that were provided, skipping over the ones already specified
            for (String requestedCovariateString : argumentCollection.COVARIATES) {
                boolean foundClass = false;
                for (Class<? extends Covariate> covClass : covariateClasses) {
                    if (requestedCovariateString.equalsIgnoreCase(covClass.getSimpleName())) {                          // -cov argument matches the class name for an implementing class
                        foundClass = true;
                        if (!requiredClasses.contains(covClass) &&
                                (argumentCollection.DO_NOT_USE_STANDARD_COVARIATES || !standardClasses.contains(covClass))) {
                            try {
                                final Covariate covariate = covClass.newInstance();                                     // now that we've found a matching class, try to instantiate it
                                optionalCovariates.add(covariate);
                            } catch (Exception e) {
                                throw new DynamicClassResolutionException(covClass, e);
                            }
                        }
                    }
                }

                if (!foundClass) {
                    throw new UserException.CommandLineException("The requested covariate type (" + requestedCovariateString + ") isn't a valid covariate option. Use --list to see possible covariates.");
                }
            }
        }
        return new Pair<ArrayList<Covariate>, ArrayList<Covariate>>(requiredCovariates, optionalCovariates);
    }

    public static void listAvailableCovariates(Logger logger) {
        // Get a list of all available covariates
        final List<Class<? extends Covariate>> covariateClasses = new PluginManager<Covariate>(Covariate.class).getPlugins();

        // Print and exit if that's what was requested
        logger.info("Available covariates:");
        for (Class<?> covClass : covariateClasses)
            logger.info(covClass.getSimpleName());
        logger.info("");
    }

    private static List<GATKReportTable> generateReportTables(final RecalibrationTables recalibrationTables, final Covariate[] requestedCovariates) {
        List<GATKReportTable> result = new LinkedList<GATKReportTable>();
        int reportTableIndex = 0;
        int rowIndex = 0;

        final Map<Covariate, String> covariateNameMap = new HashMap<Covariate, String>(requestedCovariates.length);
        for (final Covariate covariate : requestedCovariates)
            covariateNameMap.put(covariate, parseCovariateName(covariate));

        for (int tableIndex = 0; tableIndex < recalibrationTables.numTables(); tableIndex++) {

            final ArrayList<Pair<String, String>> columnNames = new ArrayList<Pair<String, String>>();                                     // initialize the array to hold the column names
            columnNames.add(new Pair<String, String>(covariateNameMap.get(requestedCovariates[0]), "%s"));              // save the required covariate name so we can reference it in the future
            if (tableIndex != RecalibrationTables.TableType.READ_GROUP_TABLE.index) {
                columnNames.add(new Pair<String, String>(covariateNameMap.get(requestedCovariates[1]), "%s"));          // save the required covariate name so we can reference it in the future
                if (tableIndex >= RecalibrationTables.TableType.OPTIONAL_COVARIATE_TABLES_START.index) {
                    columnNames.add(covariateValue);
                    columnNames.add(covariateName);
                }
            }

            columnNames.add(eventType);                                                                                 // the order of these column names is important here
            columnNames.add(empiricalQuality);
            if (tableIndex == RecalibrationTables.TableType.READ_GROUP_TABLE.index)
                columnNames.add(estimatedQReported);                                                                    // only the read group table needs the estimated Q reported
            columnNames.add(nObservations);
            columnNames.add(nErrors);

            final GATKReportTable reportTable;
            if (tableIndex <= RecalibrationTables.TableType.OPTIONAL_COVARIATE_TABLES_START.index) {
                reportTable = new GATKReportTable("RecalTable" + reportTableIndex++, "", columnNames.size());
                for (final Pair<String, String> columnName : columnNames)
                    reportTable.addColumn(columnName.getFirst(), columnName.getSecond());
                rowIndex = 0;                                                                                           // reset the row index since we're starting with a new table
            } else {
                reportTable = result.get(RecalibrationTables.TableType.OPTIONAL_COVARIATE_TABLES_START.index);
            }

            final NestedIntegerArray<RecalDatum> table = recalibrationTables.getTable(tableIndex);
            for (final NestedIntegerArray.Leaf row : table.getAllLeaves()) {
                final RecalDatum datum = (RecalDatum)row.value;
                final int[] keys = row.keys;

                int columnIndex = 0;
                int keyIndex = 0;
                reportTable.set(rowIndex, columnNames.get(columnIndex++).getFirst(), requestedCovariates[0].formatKey(keys[keyIndex++]));
                if (tableIndex != RecalibrationTables.TableType.READ_GROUP_TABLE.index) {
                    reportTable.set(rowIndex, columnNames.get(columnIndex++).getFirst(), requestedCovariates[1].formatKey(keys[keyIndex++]));
                    if (tableIndex >= RecalibrationTables.TableType.OPTIONAL_COVARIATE_TABLES_START.index) {
                        final Covariate covariate = requestedCovariates[tableIndex];

                        reportTable.set(rowIndex, columnNames.get(columnIndex++).getFirst(), covariate.formatKey(keys[keyIndex++]));
                        reportTable.set(rowIndex, columnNames.get(columnIndex++).getFirst(), covariateNameMap.get(covariate));
                    }
                }

                final EventType event = EventType.eventFrom(keys[keyIndex]);
                reportTable.set(rowIndex, columnNames.get(columnIndex++).getFirst(), event.toString());

                reportTable.set(rowIndex, columnNames.get(columnIndex++).getFirst(), datum.getEmpiricalQuality());
                if (tableIndex == RecalibrationTables.TableType.READ_GROUP_TABLE.index)
                    reportTable.set(rowIndex, columnNames.get(columnIndex++).getFirst(), datum.getEstimatedQReported());   // we only add the estimated Q reported in the RG table
                reportTable.set(rowIndex, columnNames.get(columnIndex++).getFirst(), datum.numObservations);
                reportTable.set(rowIndex, columnNames.get(columnIndex).getFirst(), datum.numMismatches);

                rowIndex++;
            }
            result.add(reportTable);
        }

        return result;
    }

    private static String parseCovariateName(final Covariate covariate) {
        return covariate.getClass().getSimpleName().split("Covariate")[0];
    }

    public static void outputRecalibrationReport(final RecalibrationArgumentCollection RAC, final QuantizationInfo quantizationInfo, final RecalibrationTables recalibrationTables, final Covariate[] requestedCovariates, final PrintStream outputFile) {
        outputRecalibrationReport(RAC.generateReportTable(), quantizationInfo.generateReportTable(), generateReportTables(recalibrationTables, requestedCovariates), outputFile);
    }

    public static void outputRecalibrationReport(final GATKReportTable argumentTable, final QuantizationInfo quantizationInfo, final RecalibrationTables recalibrationTables, final Covariate[] requestedCovariates, final PrintStream outputFile) {
        outputRecalibrationReport(argumentTable, quantizationInfo.generateReportTable(), generateReportTables(recalibrationTables, requestedCovariates), outputFile);
    }

    private static void outputRecalibrationReport(final GATKReportTable argumentTable, final GATKReportTable quantizationTable, final List<GATKReportTable> recalTables, final PrintStream outputFile) {
        final GATKReport report = new GATKReport();
        report.addTable(argumentTable);
        report.addTable(quantizationTable);
        report.addTables(recalTables);
        report.print(outputFile);
    }

    private static Pair<PrintStream, File> initializeRecalibrationPlot(File filename) {
        final PrintStream deltaTableStream;
        final File deltaTableFileName = new File(filename + ".csv");
        try {
            deltaTableStream = new PrintStream(deltaTableFileName);
        } catch (FileNotFoundException e) {
            throw new UserException.CouldNotCreateOutputFile(deltaTableFileName, "File " + deltaTableFileName + " could not be created");
        }
        return new Pair<PrintStream, File>(deltaTableStream, deltaTableFileName);
    }

    private static void outputRecalibrationPlot(Pair<PrintStream, File> files, boolean keepIntermediates) {
        final File csvFileName = files.getSecond();
        final File plotFileName = new File(csvFileName + ".pdf");
        files.getFirst().close();

        final RScriptExecutor executor = new RScriptExecutor();
        executor.addScript(new Resource(SCRIPT_FILE, RecalDataManager.class));
        executor.addArgs(csvFileName.getAbsolutePath());
        executor.addArgs(plotFileName.getAbsolutePath());
        executor.exec();

        if (!keepIntermediates)
            if (!csvFileName.delete())
                throw new ReviewedStingException("Could not find file " + csvFileName.getAbsolutePath());

    }

    public static void generateRecalibrationPlot(final File filename, final RecalibrationTables original, final Covariate[] requestedCovariates, final boolean keepIntermediates) {
        final Pair<PrintStream, File> files = initializeRecalibrationPlot(filename);
        writeCSV(files.getFirst(), original, "ORIGINAL", requestedCovariates, true);
        outputRecalibrationPlot(files, keepIntermediates);
    }

    public static void generateRecalibrationPlot(final File filename, final RecalibrationTables original, final RecalibrationTables recalibrated, final Covariate[] requestedCovariates, final boolean keepIntermediates) {
        final Pair<PrintStream, File> files = initializeRecalibrationPlot(filename);
        writeCSV(files.getFirst(), recalibrated, "RECALIBRATED", requestedCovariates, true);
        writeCSV(files.getFirst(), original, "ORIGINAL", requestedCovariates, false);
        outputRecalibrationPlot(files, keepIntermediates);
    }

    private static void writeCSV(final PrintStream deltaTableFile, final RecalibrationTables recalibrationTables, final String recalibrationMode, final Covariate[] requestedCovariates, final boolean printHeader) {

        final NestedHashMap deltaTable = new NestedHashMap();

        // add the quality score table to the delta table
        final NestedIntegerArray<RecalDatum> qualTable = recalibrationTables.getTable(RecalibrationTables.TableType.QUALITY_SCORE_TABLE);
        for (final NestedIntegerArray.Leaf leaf : qualTable.getAllLeaves()) {                                           // go through every element in the covariates table to create the delta table
            final int[] newCovs = new int[4];
            newCovs[0] = leaf.keys[0];
            newCovs[1] = requestedCovariates.length;                                                                    // replace the covariate name with an arbitrary (unused) index for QualityScore
            newCovs[2] = leaf.keys[1];
            newCovs[3] = leaf.keys[2];
            addToDeltaTable(deltaTable, newCovs, (RecalDatum)leaf.value);                                               // add this covariate to the delta table
        }

        // add the optional covariates to the delta table
        for (int i = RecalibrationTables.TableType.OPTIONAL_COVARIATE_TABLES_START.index; i < requestedCovariates.length; i++) {
            final NestedIntegerArray<RecalDatum> covTable = recalibrationTables.getTable(i);
            for (final NestedIntegerArray.Leaf leaf : covTable.getAllLeaves()) {
                final int[] covs = new int[4];
                covs[0] = leaf.keys[0];
                covs[1] = i;                                                                                            // reset the quality score covariate to 0 from the keyset (so we aggregate all rows regardless of QS)
                covs[2] = leaf.keys[2];
                covs[3] = leaf.keys[3];
                addToDeltaTable(deltaTable, covs, (RecalDatum) leaf.value);                                             // add this covariate to the delta table
            }
        }

        // output the csv file
        if (printHeader) {
            final List<String> header = new LinkedList<String>();
            header.add("ReadGroup");
            header.add("CovariateValue");
            header.add("CovariateName");
            header.add("EventType");
            header.add("Observations");
            header.add("Errors");
            header.add("EmpiricalQuality");
            header.add("AverageReportedQuality");
            header.add("Accuracy");
            header.add("Recalibration");
            deltaTableFile.println(Utils.join(",", header));
        }

        final Map<Covariate, String> covariateNameMap = new HashMap<Covariate, String>(requestedCovariates.length);
        for (final Covariate covariate : requestedCovariates)
            covariateNameMap.put(covariate, parseCovariateName(covariate));

        // print each data line
        for (final NestedHashMap.Leaf leaf : deltaTable.getAllLeaves()) {
            final List<Object> deltaKeys = generateValuesFromKeys(leaf.keys, requestedCovariates, covariateNameMap);
            final RecalDatum deltaDatum = (RecalDatum)leaf.value;
            deltaTableFile.print(Utils.join(",", deltaKeys));
            deltaTableFile.print("," + deltaDatum.stringForCSV());
            deltaTableFile.println("," + recalibrationMode);
        }
    }

    private static List<Object> generateValuesFromKeys(final List<Object> keys, final Covariate[] covariates, final Map<Covariate, String> covariateNameMap) {
        final List<Object> values = new ArrayList<Object>(4);
        values.add(covariates[RecalibrationTables.TableType.READ_GROUP_TABLE.index].formatKey((Integer)keys.get(0)));
        final int covariateIndex = (Integer)keys.get(1);
        final Covariate covariate = covariateIndex == covariates.length ? covariates[RecalibrationTables.TableType.QUALITY_SCORE_TABLE.index] : covariates[covariateIndex];
        final int covariateKey = (Integer)keys.get(2);
        values.add(covariate.formatKey(covariateKey));
        values.add(covariateNameMap.get(covariate));
        values.add(EventType.eventFrom((Integer)keys.get(3)).prettyPrint());

        return values;
    }

    /**
     * Updates the current RecalDatum element in the delta table.
     *
     * If it doesn't have an element yet, it creates an RecalDatum element and adds it to the delta table.
     *
     * @param deltaTable the delta table
     * @param deltaKey the key to the table
     * @param recalDatum the recal datum to combine with the accuracyDatum element in the table
     */
    private static void addToDeltaTable(final NestedHashMap deltaTable, final int[] deltaKey, final RecalDatum recalDatum) {
        Object[] wrappedKey = wrapKeys(deltaKey);
        final RecalDatum deltaDatum = (RecalDatum)deltaTable.get(wrappedKey);                                           // check if we already have a RecalDatum for this key
        if (deltaDatum == null)
            deltaTable.put(new RecalDatum(recalDatum), wrappedKey);                                                     // if we don't have a key yet, create a new one with the same values as the curent datum
        else
            deltaDatum.combine(recalDatum);                                                                             // if we do have a datum, combine it with this one.
    }

    private static Object[] wrapKeys(final int[] keys) {
        final Object[] wrappedKeys = new Object[keys.length];
        for (int i = 0; i < keys.length; i++)
            wrappedKeys[i] = keys[i];
        return wrappedKeys;
    }

    /**
     * Section of code shared between the two recalibration walkers which uses the command line arguments to adjust attributes of the read such as quals or platform string
     *
     * @param read The read to adjust
     * @param RAC  The list of shared command line arguments
     */
    public static void parsePlatformForRead(final GATKSAMRecord read, final RecalibrationArgumentCollection RAC) {
        GATKSAMReadGroupRecord readGroup = read.getReadGroup();

        if (RAC.FORCE_PLATFORM != null && (readGroup.getPlatform() == null || !readGroup.getPlatform().equals(RAC.FORCE_PLATFORM))) {
            readGroup.setPlatform(RAC.FORCE_PLATFORM);
        }

        if (readGroup.getPlatform() == null) {
            if (RAC.DEFAULT_PLATFORM != null) {
                if (!warnUserNullPlatform) {
                    Utils.warnUser("The input .bam file contains reads with no platform information. " +
                            "Defaulting to platform = " + RAC.DEFAULT_PLATFORM + ". " +
                            "First observed at read with name = " + read.getReadName());
                    warnUserNullPlatform = true;
                }
                readGroup.setPlatform(RAC.DEFAULT_PLATFORM);
            }
            else {
                throw new UserException.MalformedBAM(read, "The input .bam file contains reads with no platform information. First observed at read with name = " + read.getReadName());
            }
        }
    }

    /**
     * Parse through the color space of the read and add a new tag to the SAMRecord that says which bases are 
     * inconsistent with the color space. If there is no call in the color space, this method returns true meaning
     * this read should be skipped
     *
     * @param strategy the strategy used for SOLID no calls
     * @param read     The SAMRecord to parse
     * @return whether or not this read should be skipped   
     */
    public static boolean isColorSpaceConsistent(final SOLID_NOCALL_STRATEGY strategy, final GATKSAMRecord read) {
        if (ReadUtils.isSOLiDRead(read)) {                                                                              // If this is a SOLID read then we have to check if the color space is inconsistent. This is our only sign that SOLID has inserted the reference base
            if (read.getAttribute(RecalDataManager.COLOR_SPACE_INCONSISTENCY_TAG) == null) {                            // Haven't calculated the inconsistency array yet for this read
                final Object attr = read.getAttribute(RecalDataManager.COLOR_SPACE_ATTRIBUTE_TAG);
                if (attr != null) {
                    byte[] colorSpace;
                    if (attr instanceof String)
                        colorSpace = ((String) attr).getBytes();
                    else
                        throw new UserException.MalformedBAM(read, String.format("Value encoded by %s in %s isn't a string!", RecalDataManager.COLOR_SPACE_ATTRIBUTE_TAG, read.getReadName()));
                    
                    byte[] readBases = read.getReadBases();                                                             // Loop over the read and calculate first the inferred bases from the color and then check if it is consistent with the read
                    if (read.getReadNegativeStrandFlag())
                        readBases = BaseUtils.simpleReverseComplement(read.getReadBases());

                    final byte[] inconsistency = new byte[readBases.length];
                    int i;
                    byte prevBase = colorSpace[0];                                                                      // The sentinel
                    for (i = 0; i < readBases.length; i++) {
                        final byte thisBase = getNextBaseFromColor(read, prevBase, colorSpace[i + 1]);
                        inconsistency[i] = (byte) (thisBase == readBases[i] ? 0 : 1);
                        prevBase = readBases[i];
                    }
                    read.setAttribute(RecalDataManager.COLOR_SPACE_INCONSISTENCY_TAG, inconsistency);
                }
                else if (strategy == SOLID_NOCALL_STRATEGY.THROW_EXCEPTION)                                             // if the strategy calls for an exception, throw it
                    throw new UserException.MalformedBAM(read, "Unable to find color space information in SOLiD read. First observed at read with name = " + read.getReadName() + " Unfortunately this .bam file can not be recalibrated without color space information because of potential reference bias.");

                else
                    return true;                                                                                       // otherwise, just skip the read
            }
        }
        return false;
    }

    /**
     * Given the base and the color calculate the next base in the sequence
     *
     * @param read     the read
     * @param prevBase The base
     * @param color    The color
     * @return The next base in the sequence
     */
    private static byte getNextBaseFromColor(GATKSAMRecord read, final byte prevBase, final byte color) {
        switch (color) {
            case '0':
                return prevBase;
            case '1':
                return performColorOne(prevBase);
            case '2':
                return performColorTwo(prevBase);
            case '3':
                return performColorThree(prevBase);
            default:
                throw new UserException.MalformedBAM(read, "Unrecognized color space in SOLID read, color = " + (char) color +
                        " Unfortunately this bam file can not be recalibrated without full color space information because of potential reference bias.");
        }
    }

    /**
     * Check if this base is inconsistent with its color space. If it is then SOLID inserted the reference here and we should reduce the quality
     *
     * @param read   The read which contains the color space to check against
     * @param offset The offset in the read at which to check
     * @return Returns true if the base was inconsistent with the color space
     */
    public static boolean isColorSpaceConsistent(final GATKSAMRecord read, final int offset) {
        final Object attr = read.getAttribute(RecalDataManager.COLOR_SPACE_INCONSISTENCY_TAG);
        if (attr != null) {
            final byte[] inconsistency = (byte[]) attr;
            // NOTE: The inconsistency array is in the direction of the read, not aligned to the reference!
            if (read.getReadNegativeStrandFlag()) { // Negative direction
                return inconsistency[inconsistency.length - offset - 1] == (byte) 0;
            }
            else { // Forward direction
                return inconsistency[offset] == (byte) 0;
            }

            // This block of code is for if you want to check both the offset and the next base for color space inconsistency
            //if( read.getReadNegativeStrandFlag() ) { // Negative direction
            //    if( offset == 0 ) {
            //        return inconsistency[0] != 0;
            //    } else {
            //        return (inconsistency[inconsistency.length - offset - 1] != 0) || (inconsistency[inconsistency.length - offset] != 0);
            //    }
            //} else { // Forward direction
            //    if( offset == inconsistency.length - 1 ) {
            //        return inconsistency[inconsistency.length - 1] != 0;
            //    } else {
            //        return (inconsistency[offset] != 0) || (inconsistency[offset + 1] != 0);
            //    }
            //}

        }
        else { // No inconsistency array, so nothing is inconsistent
            return true;
        }
    }

    /**
     * Computes all requested covariates for every offset in the given read
     * by calling covariate.getValues(..).
     *
     * It populates an array of covariate values where result[i][j] is the covariate
     * value for the ith position in the read and the jth covariate in
     * reqeustedCovariates list.
     *
     * @param read                The read for which to compute covariate values.
     * @param requestedCovariates The list of requested covariates.
     * @return a matrix with all the covariates calculated for every base in the read
     */
    public static ReadCovariates computeCovariates(final GATKSAMRecord read, final Covariate[] requestedCovariates) {
        final ReadCovariates readCovariates = new ReadCovariates(read.getReadLength(), requestedCovariates.length);
        computeCovariates(read, requestedCovariates, readCovariates);
        return readCovariates;
    }

    /**
     * Computes all requested covariates for every offset in the given read
     * by calling covariate.getValues(..).
     *
     * It populates an array of covariate values where result[i][j] is the covariate
     * value for the ith position in the read and the jth covariate in
     * reqeustedCovariates list.
     *
     * @param read                The read for which to compute covariate values.
     * @param requestedCovariates The list of requested covariates.
     * @param resultsStorage      The object to store the covariate values
     */
    public static void computeCovariates(final GATKSAMRecord read, final Covariate[] requestedCovariates, final ReadCovariates resultsStorage) {
        // Loop through the list of requested covariates and compute the values of each covariate for all positions in this read
        for (int i = 0; i < requestedCovariates.length; i++) {
            resultsStorage.setCovariateIndex(i);
            requestedCovariates[i].recordValues(read, resultsStorage);
        }
    }

    /**
     * Perform a certain transversion (A <-> C or G <-> T) on the base.
     *
     * @param base the base [AaCcGgTt]
     * @return the transversion of the base, or the input base if it's not one of the understood ones
     */
    private static byte performColorOne(byte base) {
        switch (base) {
            case 'A':
            case 'a':
                return 'C';
            case 'C':
            case 'c':
                return 'A';
            case 'G':
            case 'g':
                return 'T';
            case 'T':
            case 't':
                return 'G';
            default:
                return base;
        }
    }

    /**
     * Perform a transition (A <-> G or C <-> T) on the base.
     *
     * @param base the base [AaCcGgTt]
     * @return the transition of the base, or the input base if it's not one of the understood ones
     */
    private static byte performColorTwo(byte base) {
        switch (base) {
            case 'A':
            case 'a':
                return 'G';
            case 'C':
            case 'c':
                return 'T';
            case 'G':
            case 'g':
                return 'A';
            case 'T':
            case 't':
                return 'C';
            default:
                return base;
        }
    }

    /**
     * Return the complement (A <-> T or C <-> G) of a base.
     *
     * @param base the base [AaCcGgTt]
     * @return the complementary base, or the input base if it's not one of the understood ones
     */
    private static byte performColorThree(byte base) {
        switch (base) {
            case 'A':
            case 'a':
                return 'T';
            case 'C':
            case 'c':
                return 'G';
            case 'G':
            case 'g':
                return 'C';
            case 'T':
            case 't':
                return 'A';
            default:
                return base;
        }
    }


    /**
     * Adds the required covariates to a covariate list
     *
     * Note: this method really only checks if the classes object has the expected number of required covariates, then add them by hand.
     *
     * @param classes list of classes to add to the covariate list
     * @return the covariate list
     */
    private static ArrayList<Covariate> addRequiredCovariatesToList(List<Class<? extends RequiredCovariate>> classes) {
        ArrayList<Covariate> dest = new ArrayList<Covariate>(classes.size());
        if (classes.size() != 2)
            throw new ReviewedStingException("The number of required covariates has changed, this is a hard change in the code and needs to be inspected");

        dest.add(new ReadGroupCovariate());                                                                             // enforce the order with RG first and QS next.
        dest.add(new QualityScoreCovariate());
        return dest;
    }

    /**
     * Adds the standard covariates to a covariate list
     *
     * @param classes list of classes to add to the covariate list
     * @return the covariate list
     */
    private static ArrayList<Covariate> addStandardCovariatesToList(List<Class<? extends StandardCovariate>> classes) {
        ArrayList<Covariate> dest = new ArrayList<Covariate>(classes.size());
        for (Class<?> covClass : classes) {
            try {
                final Covariate covariate = (Covariate) covClass.newInstance();
                dest.add(covariate);
            } catch (Exception e) {
                throw new DynamicClassResolutionException(covClass, e);
            }
        }
        return dest;
    }
}
