/*
*  By downloading the PROGRAM you agree to the following terms of use:
*  
*  BROAD INSTITUTE - SOFTWARE LICENSE AGREEMENT - FOR ACADEMIC NON-COMMERCIAL RESEARCH PURPOSES ONLY
*  
*  This Agreement is made between the Broad Institute, Inc. with a principal address at 7 Cambridge Center, Cambridge, MA 02142 (BROAD) and the LICENSEE and is effective at the date the downloading is completed (EFFECTIVE DATE).
*  
*  WHEREAS, LICENSEE desires to license the PROGRAM, as defined hereinafter, and BROAD wishes to have this PROGRAM utilized in the public interest, subject only to the royalty-free, nonexclusive, nontransferable license rights of the United States Government pursuant to 48 CFR 52.227-14; and
*  WHEREAS, LICENSEE desires to license the PROGRAM and BROAD desires to grant a license on the following terms and conditions.
*  NOW, THEREFORE, in consideration of the promises and covenants made herein, the parties hereto agree as follows:
*  
*  1. DEFINITIONS
*  1.1 PROGRAM shall mean copyright in the object code and source code known as GATK2 and related documentation, if any, as they exist on the EFFECTIVE DATE and can be downloaded from http://www.broadinstitute/GATK on the EFFECTIVE DATE.
*  
*  2. LICENSE
*  2.1   Grant. Subject to the terms of this Agreement, BROAD hereby grants to LICENSEE, solely for academic non-commercial research purposes, a non-exclusive, non-transferable license to: (a) download, execute and display the PROGRAM and (b) create bug fixes and modify the PROGRAM. 
*  The LICENSEE may apply the PROGRAM in a pipeline to data owned by users other than the LICENSEE and provide these users the results of the PROGRAM provided LICENSEE does so for academic non-commercial purposes only.  For clarification purposes, academic sponsored research is not a commercial use under the terms of this Agreement.
*  2.2  No Sublicensing or Additional Rights. LICENSEE shall not sublicense or distribute the PROGRAM, in whole or in part, without prior written permission from BROAD.  LICENSEE shall ensure that all of its users agree to the terms of this Agreement.  LICENSEE further agrees that it shall not put the PROGRAM on a network, server, or other similar technology that may be accessed by anyone other than the LICENSEE and its employees and users who have agreed to the terms of this agreement.
*  2.3  License Limitations. Nothing in this Agreement shall be construed to confer any rights upon LICENSEE by implication, estoppel, or otherwise to any computer software, trademark, intellectual property, or patent rights of BROAD, or of any other entity, except as expressly granted herein. LICENSEE agrees that the PROGRAM, in whole or part, shall not be used for any commercial purpose, including without limitation, as the basis of a commercial software or hardware product or to provide services. LICENSEE further agrees that the PROGRAM shall not be copied or otherwise adapted in order to circumvent the need for obtaining a license for use of the PROGRAM.  
*  
*  3. OWNERSHIP OF INTELLECTUAL PROPERTY 
*  LICENSEE acknowledges that title to the PROGRAM shall remain with BROAD. The PROGRAM is marked with the following BROAD copyright notice and notice of attribution to contributors. LICENSEE shall retain such notice on all copies.  LICENSEE agrees to include appropriate attribution if any results obtained from use of the PROGRAM are included in any publication.
*  Copyright 2012 Broad Institute, Inc.
*  Notice of attribution:  The GATK2 program was made available through the generosity of Medical and Population Genetics program at the Broad Institute, Inc.
*  LICENSEE shall not use any trademark or trade name of BROAD, or any variation, adaptation, or abbreviation, of such marks or trade names, or any names of officers, faculty, students, employees, or agents of BROAD except as states above for attribution purposes.
*  
*  4. INDEMNIFICATION
*  LICENSEE shall indemnify, defend, and hold harmless BROAD, and their respective officers, faculty, students, employees, associated investigators and agents, and their respective successors, heirs and assigns, (Indemnitees), against any liability, damage, loss, or expense (including reasonable attorneys fees and expenses) incurred by or imposed upon any of the Indemnitees in connection with any claims, suits, actions, demands or judgments arising out of any theory of liability (including, without limitation, actions in the form of tort, warranty, or strict liability and regardless of whether such action has any factual basis) pursuant to any right or license granted under this Agreement.
*  
*  5. NO REPRESENTATIONS OR WARRANTIES
*  THE PROGRAM IS DELIVERED AS IS.  BROAD MAKES NO REPRESENTATIONS OR WARRANTIES OF ANY KIND CONCERNING THE PROGRAM OR THE COPYRIGHT, EXPRESS OR IMPLIED, INCLUDING, WITHOUT LIMITATION, WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, NONINFRINGEMENT, OR THE ABSENCE OF LATENT OR OTHER DEFECTS, WHETHER OR NOT DISCOVERABLE. BROAD EXTENDS NO WARRANTIES OF ANY KIND AS TO PROGRAM CONFORMITY WITH WHATEVER USER MANUALS OR OTHER LITERATURE MAY BE ISSUED FROM TIME TO TIME.
*  IN NO EVENT SHALL BROAD OR ITS RESPECTIVE DIRECTORS, OFFICERS, EMPLOYEES, AFFILIATED INVESTIGATORS AND AFFILIATES BE LIABLE FOR INCIDENTAL OR CONSEQUENTIAL DAMAGES OF ANY KIND, INCLUDING, WITHOUT LIMITATION, ECONOMIC DAMAGES OR INJURY TO PROPERTY AND LOST PROFITS, REGARDLESS OF WHETHER BROAD SHALL BE ADVISED, SHALL HAVE OTHER REASON TO KNOW, OR IN FACT SHALL KNOW OF THE POSSIBILITY OF THE FOREGOING.
*  
*  6. ASSIGNMENT
*  This Agreement is personal to LICENSEE and any rights or obligations assigned by LICENSEE without the prior written consent of BROAD shall be null and void.
*  
*  7. MISCELLANEOUS
*  7.1 Export Control. LICENSEE gives assurance that it will comply with all United States export control laws and regulations controlling the export of the PROGRAM, including, without limitation, all Export Administration Regulations of the United States Department of Commerce. Among other things, these laws and regulations prohibit, or require a license for, the export of certain types of software to specified countries.
*  7.2 Termination. LICENSEE shall have the right to terminate this Agreement for any reason upon prior written notice to BROAD. If LICENSEE breaches any provision hereunder, and fails to cure such breach within thirty (30) days, BROAD may terminate this Agreement immediately. Upon termination, LICENSEE shall provide BROAD with written assurance that the original and all copies of the PROGRAM have been destroyed, except that, upon prior written authorization from BROAD, LICENSEE may retain a copy for archive purposes.
*  7.3 Survival. The following provisions shall survive the expiration or termination of this Agreement: Articles 1, 3, 4, 5 and Sections 2.2, 2.3, 7.3, and 7.4.
*  7.4 Notice. Any notices under this Agreement shall be in writing, shall specifically refer to this Agreement, and shall be sent by hand, recognized national overnight courier, confirmed facsimile transmission, confirmed electronic mail, or registered or certified mail, postage prepaid, return receipt requested.  All notices under this Agreement shall be deemed effective upon receipt. 
*  7.5 Amendment and Waiver; Entire Agreement. This Agreement may be amended, supplemented, or otherwise modified only by means of a written instrument signed by all parties. Any waiver of any rights or failure to act in a specific instance shall relate only to such instance and shall not be construed as an agreement to waive any rights or fail to act in any other instance, whether or not similar. This Agreement constitutes the entire agreement among the parties with respect to its subject matter and supersedes prior agreements or understandings between the parties relating to its subject matter. 
*  7.6 Binding Effect; Headings. This Agreement shall be binding upon and inure to the benefit of the parties and their respective permitted successors and assigns. All headings are for convenience only and shall not affect the meaning of any provision of this Agreement.
*  7.7 Governing Law. This Agreement shall be construed, governed, interpreted and applied in accordance with the internal laws of the Commonwealth of Massachusetts, U.S.A., without regard to conflict of laws principles.
*/

package org.broadinstitute.sting.gatk.walkers.haplotypecaller;

/**
 * Created by IntelliJ IDEA.
 * User: rpoplin
 * Date: 3/14/12
 */

import org.broadinstitute.sting.BaseTest;
import org.broadinstitute.sting.utils.MathUtils;
import org.broadinstitute.sting.utils.Utils;
import org.broadinstitute.sting.utils.pairhmm.PairHMM;
import org.broadinstitute.sting.utils.recalibration.covariates.RepeatCovariate;
import org.broadinstitute.sting.utils.recalibration.covariates.RepeatLengthCovariate;
import org.broadinstitute.variant.variantcontext.*;
import org.broadinstitute.variant.vcf.VCFConstants;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

/**
 * Unit tests for PairHMMLikelihoodCalculationEngine
 */
public class PairHMMLikelihoodCalculationEngineUnitTest extends BaseTest {

    Allele Aref, T, C, G, Cref, ATC, ATCATC;

    @BeforeSuite
    public void setup() {
        // alleles
        Aref = Allele.create("A", true);
        Cref = Allele.create("C", true);
        T = Allele.create("T");
        C = Allele.create("C");
        G = Allele.create("G");
        ATC = Allele.create("ATC");
        ATCATC = Allele.create("ATCATC");
    }

    @Test
    public void testNormalizeDiploidLikelihoodMatrixFromLog10() {
        double[][] likelihoodMatrix = {
            {-90.2,     0,      0},
            {-190.1, -2.1,      0},
            {-7.0,  -17.5,  -35.9}
        };
        double[][] normalizedMatrix = {
            {-88.1,     0,      0},
            {-188.0,  0.0,      0},
            {-4.9,  -15.4,  -33.8}
        };


        Assert.assertTrue(compareDoubleArrays(PairHMMLikelihoodCalculationEngine.normalizeDiploidLikelihoodMatrixFromLog10(likelihoodMatrix), normalizedMatrix));

        double[][] likelihoodMatrix2 = {
                {-90.2,     0,      0,        0},
                {-190.1, -2.1,      0,        0},
                {-7.0,  -17.5,  -35.9,        0},
                {-7.0,  -17.5,  -35.9,  -1000.0},
        };
        double[][] normalizedMatrix2 = {
                {-88.1,     0,      0,        0},
                {-188.0,  0.0,      0,        0},
                {-4.9,  -15.4,  -33.8,        0},
                {-4.9,  -15.4,  -33.8,   -997.9},
        };
        Assert.assertTrue(compareDoubleArrays(PairHMMLikelihoodCalculationEngine.normalizeDiploidLikelihoodMatrixFromLog10(likelihoodMatrix2), normalizedMatrix2));
    }

    @DataProvider(name = "PcrErrorModelTestProvider")
    public Object[][] createPcrErrorModelTestData() {
        List<Object[]> tests = new ArrayList<Object[]>();

        for ( final String repeat : Arrays.asList("A", "AC", "ACG", "ACGT") ) {
            for ( final int repeatLength : Arrays.asList(1, 2, 3, 5, 10, 15) ) {
                tests.add(new Object[]{repeat, repeatLength});
            }
        }

        return tests.toArray(new Object[][]{});
    }

    @Test(dataProvider = "PcrErrorModelTestProvider", enabled = true)
    public void createPcrErrorModelTest(final String repeat, final int repeatLength) {

        final PairHMMLikelihoodCalculationEngine engine = new PairHMMLikelihoodCalculationEngine((byte)0, false,
                PairHMM.HMM_IMPLEMENTATION.ORIGINAL, 0.0, true,
                PairHMMLikelihoodCalculationEngine.PCR_ERROR_MODEL.CONSERVATIVE);

        final String readString = Utils.dupString(repeat, repeatLength);
        final byte[] insQuals = new byte[readString.length()];
        final byte[] delQuals = new byte[readString.length()];
        Arrays.fill(insQuals, (byte)PairHMMLikelihoodCalculationEngine.INITIAL_QSCORE);
        Arrays.fill(delQuals, (byte)PairHMMLikelihoodCalculationEngine.INITIAL_QSCORE);

        engine.applyPCRErrorModel(readString.getBytes(), insQuals, delQuals);

        final RepeatCovariate repeatCovariate = new RepeatLengthCovariate();
        repeatCovariate.initialize(PairHMMLikelihoodCalculationEngine.MAX_STR_UNIT_LENGTH, PairHMMLikelihoodCalculationEngine.MAX_REPEAT_LENGTH);

        for ( int i = 1; i < insQuals.length; i++ ) {

            final int repeatLengthFromCovariate = repeatCovariate.findTandemRepeatUnits(readString.getBytes(), i-1).getSecond();
            final byte adjustedScore = PairHMMLikelihoodCalculationEngine.getErrorModelAdjustedQual(repeatLengthFromCovariate, 3.0);

            Assert.assertEquals(insQuals[i-1], adjustedScore);
            Assert.assertEquals(delQuals[i-1], adjustedScore);
        }
    }

    /*
    private class BasicLikelihoodTestProvider extends TestDataProvider {
        public Double readLikelihoodForHaplotype1;
        public Double readLikelihoodForHaplotype2;
        public Double readLikelihoodForHaplotype3;
        
        public BasicLikelihoodTestProvider(double a, double b) {
            super(BasicLikelihoodTestProvider.class, String.format("Diploid haplotype likelihoods for reads %f / %f",a,b));
            readLikelihoodForHaplotype1 = a;
            readLikelihoodForHaplotype2 = b;
            readLikelihoodForHaplotype3 = null;
        }

        public BasicLikelihoodTestProvider(double a, double b, double c) {
            super(BasicLikelihoodTestProvider.class, String.format("Diploid haplotype likelihoods for reads %f / %f / %f",a,b,c));
            readLikelihoodForHaplotype1 = a;
            readLikelihoodForHaplotype2 = b;
            readLikelihoodForHaplotype3 = c;
        }
        
        public double[][] expectedDiploidHaplotypeMatrix() {
            if( readLikelihoodForHaplotype3 == null ) {
                double maxValue = Math.max(readLikelihoodForHaplotype1,readLikelihoodForHaplotype2);
                double[][] normalizedMatrix = {
                        {readLikelihoodForHaplotype1 - maxValue, Double.NEGATIVE_INFINITY},
                        {Math.log10(0.5*Math.pow(10,readLikelihoodForHaplotype1) + 0.5*Math.pow(10,readLikelihoodForHaplotype2)) - maxValue, readLikelihoodForHaplotype2 - maxValue}
                };
                return normalizedMatrix;
            } else {
                double maxValue = MathUtils.max(readLikelihoodForHaplotype1,readLikelihoodForHaplotype2,readLikelihoodForHaplotype3);
                double[][] normalizedMatrix = {
                        {readLikelihoodForHaplotype1 - maxValue, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY},
                        {Math.log10(0.5*Math.pow(10,readLikelihoodForHaplotype1) + 0.5*Math.pow(10,readLikelihoodForHaplotype2)) - maxValue, readLikelihoodForHaplotype2 - maxValue, Double.NEGATIVE_INFINITY},
                        {Math.log10(0.5*Math.pow(10,readLikelihoodForHaplotype1) + 0.5*Math.pow(10,readLikelihoodForHaplotype3)) - maxValue,
                         Math.log10(0.5*Math.pow(10,readLikelihoodForHaplotype2) + 0.5*Math.pow(10,readLikelihoodForHaplotype3)) - maxValue, readLikelihoodForHaplotype3 - maxValue}
                };
                return normalizedMatrix;
            }
        }
        
        public double[][] calcDiploidHaplotypeMatrix() {
            ArrayList<Haplotype> haplotypes = new ArrayList<Haplotype>();
            for( int iii = 1; iii <= 3; iii++) {
                Double readLikelihood = ( iii == 1 ? readLikelihoodForHaplotype1 : ( iii == 2 ? readLikelihoodForHaplotype2 : readLikelihoodForHaplotype3) );
                int readCount = 1;
                if( readLikelihood != null ) {
                    Haplotype haplotype = new Haplotype( (iii == 1 ? "AAAA" : (iii == 2 ? "CCCC" : "TTTT")).getBytes() );
                    haplotype.addReadLikelihoods("myTestSample", new double[]{readLikelihood}, new int[]{readCount});
                    haplotypes.add(haplotype);
                }
            }
            final HashSet<String> sampleSet = new HashSet<String>(1);
            sampleSet.add("myTestSample");
            return PairHMMLikelihoodCalculationEngine.computeDiploidHaplotypeLikelihoods(sampleSet, haplotypes);
        }
    }

    @DataProvider(name = "BasicLikelihoodTestProvider")
    public Object[][] makeBasicLikelihoodTests() {
        new BasicLikelihoodTestProvider(-1.1, -2.2);
        new BasicLikelihoodTestProvider(-2.2, -1.1);
        new BasicLikelihoodTestProvider(-1.1, -1.1);
        new BasicLikelihoodTestProvider(-9.7, -15.0);
        new BasicLikelihoodTestProvider(-1.1, -2000.2);
        new BasicLikelihoodTestProvider(-1000.1, -2.2);
        new BasicLikelihoodTestProvider(0, 0);
        new BasicLikelihoodTestProvider(-1.1, 0);
        new BasicLikelihoodTestProvider(0, -2.2);
        new BasicLikelihoodTestProvider(-100.1, -200.2);

        new BasicLikelihoodTestProvider(-1.1, -2.2, 0);
        new BasicLikelihoodTestProvider(-2.2, -1.1, 0);
        new BasicLikelihoodTestProvider(-1.1, -1.1, 0);
        new BasicLikelihoodTestProvider(-9.7, -15.0, 0);
        new BasicLikelihoodTestProvider(-1.1, -2000.2, 0);
        new BasicLikelihoodTestProvider(-1000.1, -2.2, 0);
        new BasicLikelihoodTestProvider(0, 0, 0);
        new BasicLikelihoodTestProvider(-1.1, 0, 0);
        new BasicLikelihoodTestProvider(0, -2.2, 0);
        new BasicLikelihoodTestProvider(-100.1, -200.2, 0);

        new BasicLikelihoodTestProvider(-1.1, -2.2, -12.121);
        new BasicLikelihoodTestProvider(-2.2, -1.1, -12.121);
        new BasicLikelihoodTestProvider(-1.1, -1.1, -12.121);
        new BasicLikelihoodTestProvider(-9.7, -15.0, -12.121);
        new BasicLikelihoodTestProvider(-1.1, -2000.2, -12.121);
        new BasicLikelihoodTestProvider(-1000.1, -2.2, -12.121);
        new BasicLikelihoodTestProvider(0, 0, -12.121);
        new BasicLikelihoodTestProvider(-1.1, 0, -12.121);
        new BasicLikelihoodTestProvider(0, -2.2, -12.121);
        new BasicLikelihoodTestProvider(-100.1, -200.2, -12.121);

        return BasicLikelihoodTestProvider.getTests(BasicLikelihoodTestProvider.class);
    }

    @Test(dataProvider = "BasicLikelihoodTestProvider", enabled = true)
    public void testOneReadWithTwoOrThreeHaplotypes(BasicLikelihoodTestProvider cfg) {
        double[][] calculatedMatrix = cfg.calcDiploidHaplotypeMatrix();
        double[][] expectedMatrix = cfg.expectedDiploidHaplotypeMatrix();
        logger.warn(String.format("Test: %s", cfg.toString()));
        Assert.assertTrue(compareDoubleArrays(calculatedMatrix, expectedMatrix));
    }
    */

    //Private function to compare 2d arrays
    private boolean compareDoubleArrays(double[][] b1, double[][] b2) {
        if( b1.length != b2.length ) {
            return false; // sanity check
        }

        for( int i=0; i < b1.length; i++ ){
            if( b1[i].length != b2[i].length) {
                return false; // sanity check
            }
            for( int j=0; j < b1.length; j++ ){
                if ( MathUtils.compareDoubles(b1[i][j], b2[i][j]) != 0 && !Double.isInfinite(b1[i][j]) && !Double.isInfinite(b2[i][j]))
                    return false;
            }
        }
        return true;
    }


    private String arraysEq(int[] a, int[] b) {
        if ( a.length != b.length ) {
            return String.format("NEQ: %s | %s",Arrays.toString(a),Arrays.toString(b));
        }
        for ( int idx = 0; idx < a.length; idx++) {
            if ( a[idx] - b[idx] > 1 || b[idx] - a[idx] > 1) {
                return String.format("NEQ: %s | %s",Arrays.toString(a),Arrays.toString(b));
            }
        }

        return "";
    }

    private int[] _mleparse(List<Integer> s) {
        int[] mle = new int[s.size()];
        for ( int idx = 0; idx < mle.length; idx ++) {
            mle[idx] = s.get(idx);
        }

        return mle;
    }

    private Genotype makeGwithPLs(String sample, Allele a1, Allele a2, double[] pls) {
        Genotype gt = new GenotypeBuilder(sample, Arrays.asList(a1, a2)).PL(pls).make();
        if ( pls != null && pls.length > 0 ) {
            Assert.assertNotNull(gt.getPL());
            Assert.assertTrue(gt.getPL().length > 0);
            for ( int i : gt.getPL() ) {
                Assert.assertTrue(i >= 0);
            }
            Assert.assertNotEquals(Arrays.toString(gt.getPL()),"[0]");
        }
        return gt;
    }

    private Genotype makeG(String sample, Allele a1, Allele a2) {
        return GenotypeBuilder.create(sample, Arrays.asList(a1, a2));
    }

    private Genotype makeG(String sample, Allele a1, Allele a2, int... pls) {
        return new GenotypeBuilder(sample, Arrays.asList(a1, a2)).PL(pls).make();
    }

    private VariantContext makeVC(String source, List<Allele> alleles, Genotype... genotypes) {
        int start = 10;
        int stop = start; // alleles.contains(ATC) ? start + 3 : start;
        return new VariantContextBuilder(source, "1", start, stop, alleles).genotypes(Arrays.asList(genotypes)).filters(null).make();
    }

    @Test
    private void testCalculatePosteriorNoExternalData() {
        VariantContext test1 = makeVC("1",Arrays.asList(Aref,T), makeG("s1",Aref,T,20,0,10),
                makeG("s2",T,T,60,40,0),
                makeG("s3",Aref,Aref,0,30,90));
        test1 = new VariantContextBuilder(test1).attribute(VCFConstants.MLE_ALLELE_COUNT_KEY,3).make();
        VariantContext test1result = PairHMMLikelihoodCalculationEngine.calculatePosteriorGLs(test1, new ArrayList<VariantContext>(), 0, 0.001, true, false, false);
        Genotype test1exp1 = makeGwithPLs("s1",Aref,T,new double[]{-2.20686, -0.03073215, -1.20686});
        Assert.assertTrue(test1exp1.hasPL());
        Genotype test1exp2 = makeGwithPLs("s2",T,T,new double[]{-6.000066, -3.823938, -6.557894e-05});
        Genotype test1exp3 = makeGwithPLs("s3",Aref,Aref,new double[]{-0.0006510083, -2.824524, -9.000651});
        Assert.assertEquals("java.util.ArrayList",test1result.getGenotype(0).getAnyAttribute(VCFConstants.GENOTYPE_POSTERIORS_KEY).getClass().getCanonicalName());
        Assert.assertEquals(arraysEq(test1exp1.getPL(), _mleparse((List<Integer>)test1result.getGenotype(0).getAnyAttribute(VCFConstants.GENOTYPE_POSTERIORS_KEY))), "");
        Assert.assertEquals(arraysEq(test1exp2.getPL(),_mleparse((List<Integer>)test1result.getGenotype(1).getAnyAttribute(VCFConstants.GENOTYPE_POSTERIORS_KEY))), "");
        Assert.assertEquals(arraysEq(test1exp3.getPL(),_mleparse((List<Integer>)test1result.getGenotype(2).getAnyAttribute(VCFConstants.GENOTYPE_POSTERIORS_KEY))), "");

        // AA AB BB AC BC CC
        // AA AC CC AT CT TT
        VariantContext test2 = makeVC("2",Arrays.asList(Aref,C,T),
                makeG("s1",Aref,T,30,10,60,0,15,90),
                makeG("s2",Aref,C,40,0,10,30,40,80),
                makeG("s3",Aref,Aref,0,5,8,15,20,40),
                makeG("s4",C,T,80,40,12,20,0,10));
        test2 = new VariantContextBuilder(test2).attribute(VCFConstants.MLE_ALLELE_COUNT_KEY,new ArrayList<Integer>(Arrays.asList(2,2))).make();
        VariantContext test2result = PairHMMLikelihoodCalculationEngine.calculatePosteriorGLs(test2,new ArrayList<VariantContext>(),5,0.001,true,false,false);
        Genotype test2exp1 = makeGwithPLs("s1",Aref,T,new double[]{-2.647372, -1.045139, -6.823193, -0.04513873, -2.198182, -9.823193});
        Genotype test2exp2 = makeGwithPLs("s2",Aref,C,new double[]{-3.609957, -0.007723248, -1.785778, -3.007723, -4.660767, -8.785778});
        Genotype test2exp3 = makeGwithPLs("s3",Aref,Aref,new double[] {-0.06094877, -0.9587151, -2.03677,-1.958715,  -3.111759, -5.23677});
        Genotype test2exp4 = makeGwithPLs("s4",C,T,new double[]{-7.016534, -3.4143, -1.392355, -1.4143, -0.06734388, -1.192355});
        Assert.assertEquals(arraysEq(test2exp1.getPL(),(int[]) _mleparse((List<Integer>)test2result.getGenotype(0).getAnyAttribute(VCFConstants.GENOTYPE_POSTERIORS_KEY))), "");
        Assert.assertEquals(arraysEq(test2exp2.getPL(),(int[]) _mleparse((List<Integer>)test2result.getGenotype(1).getAnyAttribute(VCFConstants.GENOTYPE_POSTERIORS_KEY))), "");
        Assert.assertEquals(arraysEq(test2exp3.getPL(),(int[]) _mleparse((List<Integer>)test2result.getGenotype(2).getAnyAttribute(VCFConstants.GENOTYPE_POSTERIORS_KEY))), "");
        Assert.assertEquals(arraysEq(test2exp4.getPL(),(int[]) _mleparse((List<Integer>)test2result.getGenotype(3).getAnyAttribute(VCFConstants.GENOTYPE_POSTERIORS_KEY))), "");
    }

    @Test
    private void testCalculatePosteriorSamplePlusExternal() {
        VariantContext testOverlappingBase = makeVC("1", Arrays.asList(Aref,T), makeG("s1",T,T,40,20,0),
                makeG("s2",Aref,T,18,0,24),
                makeG("s3",Aref,T,22,0,12));
        List<VariantContext> supplTest1 = new ArrayList<>(3);
        supplTest1.add(new VariantContextBuilder(makeVC("2",Arrays.asList(Aref,T))).attribute(VCFConstants.MLE_ALLELE_COUNT_KEY,2).attribute(VCFConstants.ALLELE_NUMBER_KEY,10).make());
        supplTest1.add(new VariantContextBuilder(makeVC("3",Arrays.asList(Aref,T))).attribute(VCFConstants.ALLELE_COUNT_KEY,4).attribute(VCFConstants.ALLELE_NUMBER_KEY,22).make());
        supplTest1.add(makeVC("4",Arrays.asList(Aref,T),
                makeG("s_1",T,T),
                makeG("s_2",Aref,T)));
        VariantContext test1result = PairHMMLikelihoodCalculationEngine.calculatePosteriorGLs(testOverlappingBase,supplTest1,0,0.001,true,false,false);
        // the counts here are ref=30, alt=14
        Genotype test1exp1 = makeGwithPLs("t1",T,T,new double[]{-3.370985, -1.415172, -0.01721766});
        Genotype test1exp2 = makeGwithPLs("t2",Aref,T,new double[]{-1.763792, -0.007978791, -3.010024});
        Genotype test1exp3 = makeGwithPLs("t3",Aref,T,new double[]{-2.165587, -0.009773643, -1.811819});
        Assert.assertEquals(arraysEq(test1exp1.getPL(),_mleparse((List<Integer>) test1result.getGenotype(0).getAnyAttribute(VCFConstants.GENOTYPE_POSTERIORS_KEY))), "");
        Assert.assertEquals(arraysEq(test1exp2.getPL(),_mleparse((List<Integer>) test1result.getGenotype(1).getAnyAttribute(VCFConstants.GENOTYPE_POSTERIORS_KEY))), "");
        Assert.assertEquals(arraysEq(test1exp3.getPL(),_mleparse((List<Integer>) test1result.getGenotype(2).getAnyAttribute(VCFConstants.GENOTYPE_POSTERIORS_KEY))), "");

        VariantContext testNonOverlapping = makeVC("1", Arrays.asList(Aref,T), makeG("s1",T,T,3,1,0));
        List<VariantContext> other = Arrays.asList(makeVC("2",Arrays.asList(Aref,C),makeG("s2",C,C,10,2,0)));
        VariantContext test2result = PairHMMLikelihoodCalculationEngine.calculatePosteriorGLs(testNonOverlapping,other,0,0.001,true,false,false);
        Genotype test2exp1 = makeGwithPLs("SGV",T,T,new double[]{-4.078345, -3.276502, -0.0002661066});
        Assert.assertEquals(arraysEq(test2exp1.getPL(),_mleparse((List<Integer>) test2result.getGenotype(0).getAnyAttribute(VCFConstants.GENOTYPE_POSTERIORS_KEY))), "");
    }

    private double[] pl2gl(int[] pl) {
        double[] gl = new double[pl.length];
        for ( int idx = 0; idx < gl.length; idx++ ) {
            gl[idx] = pl[idx]/(-10.0);
        }

        return MathUtils.normalizeFromLog10(gl,true);
    }

    @Test
    private void testCalculatePosterior() {
        int[][] likelihood_PLs  = new int[][]{
                new int[]{3,0,3},
                new int[]{99,0,99},
                new int[]{50,20,0},
                new int[]{10,0,50},
                new int[]{80,60,0},
                new int[]{0,42,44}};

        int[] altCounts = new int[]{10,40,90};
        int[] altAlleleNum = new int[]{100,500,1000};

        double[] expected_post_10_100 = new double[] {
                9.250326e-03, 3.020208e-01, 6.887289e-01,
                7.693433e-12, 1.000000e+00, 5.728111e-10,
                1.340156e-07, 2.192982e-03, 9.978069e-01,
                6.073718e-03, 9.938811e-01, 4.522159e-05,
                1.343101e-10, 2.197802e-07, 9.999998e-01,
                9.960193e-01, 1.028366e-03, 2.952290e-03
        };

        double[] expected_post_10_500 = new double[] {
                4.226647e-04, 7.513277e-02, 9.244446e-01,
                1.413080e-12, 1.000000e+00, 3.090662e-09,
                4.570232e-09, 4.071661e-04, 9.995928e-01,
                1.120916e-03, 9.986339e-01, 2.451646e-04,
                4.572093e-12, 4.073320e-08, 1.000000e+00,
                9.151689e-01, 5.144399e-03, 7.968675e-02
        };

        double[] expected_post_10_1000 = new double[] {
                1.077685e-04, 3.870477e-02, 9.611875e-01,
                6.994030e-13, 1.000000e+00, 6.237975e-09,
                1.120976e-09, 2.017756e-04, 9.997982e-01,
                5.549722e-04, 9.989500e-01, 4.949797e-04,
                1.121202e-12, 2.018163e-08, 1.000000e+00,
                7.318346e-01, 8.311615e-03, 2.598538e-01
        };

        double[] expected_post_40_100 = new double[] {
                1.102354e-01, 6.437516e-01, 2.460131e-01,
                4.301328e-11, 1.000000e+00, 9.599306e-11,
                4.422850e-06, 1.294493e-02, 9.870507e-01,
                3.303763e-02, 9.669550e-01, 7.373032e-06,
                4.480868e-09, 1.311474e-06, 9.999987e-01,
                9.997266e-01, 1.846199e-04, 8.882157e-05
        };

        double[] expected_post_40_500 = new double[] {
                5.711785e-03, 2.557266e-01, 7.385617e-01,
                5.610428e-12, 1.000000e+00, 7.254558e-10,
                7.720262e-08, 1.732352e-03, 9.982676e-01,
                4.436495e-03, 9.955061e-01, 5.736604e-05,
                7.733659e-11, 1.735358e-07, 9.999998e-01,
                9.934793e-01, 1.406575e-03, 5.114153e-03
        };

        double[] expected_post_40_1000 = new double[] {
                1.522132e-03, 1.422229e-01, 8.562549e-01,
                2.688330e-12, 1.000000e+00, 1.512284e-09,
                1.776184e-08, 8.317737e-04, 9.991682e-01,
                2.130611e-03, 9.977495e-01, 1.198547e-04,
                1.777662e-11, 8.324661e-08, 9.999999e-01,
                9.752770e-01, 2.881677e-03, 2.184131e-02
        };

        double[] expected_post_90_100 = new double[] {
                6.887289e-01, 3.020208e-01, 9.250326e-03,
                5.728111e-10, 1.000000e+00, 7.693433e-12,
                6.394346e-04, 1.405351e-01, 8.588255e-01,
                3.127146e-01, 6.872849e-01, 4.200075e-07,
                7.445327e-07, 1.636336e-05, 9.999829e-01,
                9.999856e-01, 1.386699e-05, 5.346906e-07
        };

        double[] expected_post_90_500 = new double[] {
                2.528165e-02, 4.545461e-01, 5.201723e-01,
                1.397100e-11, 1.000000e+00, 2.874546e-10,
                4.839050e-07, 4.360463e-03, 9.956391e-01,
                1.097551e-02, 9.890019e-01, 2.258221e-05,
                4.860244e-10, 4.379560e-07, 9.999996e-01,
                9.986143e-01, 5.677671e-04, 8.179741e-04
        };

        double[] expected_post_90_1000 = new double[] {
                7.035938e-03, 2.807708e-01, 7.121932e-01,
                6.294627e-12, 1.000000e+00, 6.371561e-10,
                9.859771e-08, 1.971954e-03, 9.980279e-01,
                4.974874e-03, 9.949748e-01, 5.035678e-05,
                9.879252e-11, 1.975850e-07, 9.999998e-01,
                9.947362e-01, 1.255272e-03, 4.008518e-03
        };

        double[][] expectations = new double[][] {
                expected_post_10_100,
                expected_post_10_500,
                expected_post_10_1000,
                expected_post_40_100,
                expected_post_40_500,
                expected_post_40_1000,
                expected_post_90_100,
                expected_post_90_500,
                expected_post_90_1000
        };

        int testIndex = 0;
        for ( int altCount : altCounts ) {
            for ( int numAlt : altAlleleNum ) {
                double[] knownCounts = new double[2];
                knownCounts[0] = altCount;
                knownCounts[1] = numAlt-altCount;
                int expected_index = 0;
                for ( int gl_index = 0; gl_index < likelihood_PLs.length; gl_index++ ) {
                    double[] post = PairHMMLikelihoodCalculationEngine.calculatePosteriorGLs(pl2gl(likelihood_PLs[gl_index]), knownCounts, 2);
                    for ( int i = 0; i < post.length; i++ ) {
                        double expected = expectations[testIndex][expected_index++];
                        double observed = Math.pow(10.0,post[i]);
                        double err = Math.abs( (expected-observed)/expected );
                        Assert.assertTrue(err < 1e-4, String.format("Counts: %s | Expected: %e | Observed: %e | pre %s | prior %s | post %s",
                                Arrays.toString(knownCounts), expected,observed, Arrays.toString(pl2gl(likelihood_PLs[gl_index])),
                                Arrays.toString(PairHMMLikelihoodCalculationEngine.getDirichletPrior(knownCounts,2)),Arrays.toString(post)));
                    }
                }
                testIndex++;
            }
        }
    }

    private boolean arraysApproxEqual(double[] a, double[] b, double tol) {
        if ( a.length != b.length ) {
            return false;
        }

        for ( int idx = 0; idx < a.length; idx++ ) {
            if ( Math.abs(a[idx]-b[idx]) > tol ) {
                return false;
            }
        }

        return true;
    }

    private String errMsgArray(double[] a, double[] b) {
        return String.format("Expected %s, Observed %s", Arrays.toString(a), Arrays.toString(b));
    }

    @Test
    private void testPosteriorMultiAllelic() {
        // AA AB BB AC BC CC AD BD CD DD
        int[] PL_one = new int[] {40,20,30,0,15,25};
        int[] PL_two = new int[] {0,20,10,99,99,99};
        int[] PL_three = new int[] {50,40,0,30,30,10,20,40,80,50};
        int[] PL_four  = new int[] {99,90,85,10,5,30,40,20,40,30,0,12,20,14,5};
        int[] PL_five = new int[] {60,20,30,0,40,10,8,12,18,22,40,12,80,60,20};
        double[] counts_one = new double[]{100.001,40.001,2.001};
        double[] counts_two = new double[]{2504.001,16.001,218.001};
        double[] counts_three = new double[]{10000.001,500.001,25.001,0.001};
        double[] counts_four = new double[]{4140.001,812.001,32.001,104.001,12.001};
        double[] counts_five = new double[]{80.001,40.001,8970.001,200.001,1922.001};

        double expected_one[] = new double[] { -2.684035, -0.7852596, -2.4735, -0.08608339, -1.984017, -4.409852 };
        double expected_two[] = new double[] { -5.736189e-05, -3.893688, -5.362878, -10.65938, -12.85386, -12.0186};
        double expected_three[] = new double[] {-2.403234, -2.403276, -0.004467802, -2.70429, -4.005319, -3.59033, -6.102247, -9.403276, -14.70429, -13.40284};
        double expected_four[] = new double[] {-7.828677, -7.335196, -7.843136, -0.7395892, -0.947033, -5.139092, -3.227715,
                -1.935159, -5.339552, -4.124552, -0.1655353, -2.072979, -4.277372, -3.165498, -3.469589 };
        double expected_five[] = new double[] { -9.170334, -5.175724, -6.767055, -0.8250021, -5.126027, -0.07628661, -3.276762,
                -3.977787, -2.227065, -4.57769, -5.494041, -2.995066, -7.444344, -7.096104, -2.414187};

        double[] post1 = PairHMMLikelihoodCalculationEngine.calculatePosteriorGLs(pl2gl(PL_one),counts_one,2);
        double[] post2 = PairHMMLikelihoodCalculationEngine.calculatePosteriorGLs(pl2gl(PL_two),counts_two,2);
        double[] post3 = PairHMMLikelihoodCalculationEngine.calculatePosteriorGLs(pl2gl(PL_three),counts_three,2);
        double[] post4 = PairHMMLikelihoodCalculationEngine.calculatePosteriorGLs(pl2gl(PL_four),counts_four,2);
        double[] post5 = PairHMMLikelihoodCalculationEngine.calculatePosteriorGLs(pl2gl(PL_five),counts_five,2);

        double[] expecPrior5 = new double[] {-4.2878195, -4.2932090, -4.8845400, -1.9424874, -2.2435120, -0.1937719, -3.5942477,
                -3.8952723, -1.5445506, -3.4951749, -2.6115263, -2.9125508, -0.5618292, -2.2135895,
                -1.5316722};

        Assert.assertTrue(arraysApproxEqual(expecPrior5, PairHMMLikelihoodCalculationEngine.getDirichletPrior(counts_five,2),1e-5),errMsgArray(expecPrior5,PairHMMLikelihoodCalculationEngine.getDirichletPrior(counts_five,2)));

        Assert.assertTrue(arraysApproxEqual(expected_one,post1,1e-6),errMsgArray(expected_one,post1));
        Assert.assertTrue(arraysApproxEqual(expected_two,post2,1e-5),errMsgArray(expected_two,post2));
        Assert.assertTrue(arraysApproxEqual(expected_three,post3,1e-5),errMsgArray(expected_three,post3));
        Assert.assertTrue(arraysApproxEqual(expected_four,post4,1e-5),errMsgArray(expected_four,post4));
        Assert.assertTrue(arraysApproxEqual(expected_five,post5,1e-5),errMsgArray(expected_five,post5));
    }
}
