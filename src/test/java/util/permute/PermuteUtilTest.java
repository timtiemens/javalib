package util.permute;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class PermuteUtilTest {

    // stateless
    private final PermuteUtil permuteUtil = new PermuteUtil();



    @Test
    public void testSizeZero() {
        List<Integer> a1 = new ArrayList<Integer>();
        List<Integer> answer = permuteUtil.nthPermutation(a1, BigInteger.valueOf(1));
        Assert.assertEquals(0, answer.size());
    }

    @Test
    public void testSizeOne() {
        List<Integer> a1 = Arrays.asList(1);
        List<Integer> answer = permuteUtil.nthPermutation(a1, BigInteger.valueOf(1));
        List<Integer> expected = Arrays.asList(1);
        Assert.assertEquals(expected, answer);
    }

    @Test
    public void testSizeOneTooBig() {
        List<Integer> a1 = Arrays.asList(1);
        try {
            List<Integer> answer = permuteUtil.nthPermutation(a1, BigInteger.valueOf(2));
            Assert.fail("should have thrown exception: " + answer.size());
        } catch (IllegalArgumentException e) {
            System.out.println("(CORRECT MESSAGE)" + e.getMessage());
        }
    }

    @Test
    public void testSizeThreeNumberSix() {
        List<Integer> a1 = Arrays.asList(1, 2, 3);
        List<Integer> answer = permuteUtil.nthPermutation(a1, BigInteger.valueOf(6));
        List<Integer> expected = Arrays.asList(3, 2, 1);
        Assert.assertEquals(expected, answer);
    }


    @Test
    public void testSizeThreeNumberSevenWhichIsOneTooBig() {
        List<Integer> a1 = Arrays.asList(1, 2, 3);
        try {
            List<Integer> answer = permuteUtil.nthPermutation(a1, BigInteger.valueOf(7));
            Assert.fail("should have thrown exception: " + answer.size());
        } catch (IllegalArgumentException e) {
            System.out.println("(CORRECT MESSAGE)" + e.getMessage());
        }
    }

    @Test
    public void testSizeOneTooSmall() {
        List<Integer> a1 = Arrays.asList(1);
        try {
            List<Integer> answer = permuteUtil.nthPermutation(a1, BigInteger.valueOf(0));
            Assert.fail("should have thrown exception: " + answer.size());
        } catch (IllegalArgumentException e) {
            System.out.println("(CORRECT MESSAGE)" + e.getMessage());
        }
    }


    @Test
    public void testAgainstEachOtherFast() {
        List<Integer> a123 = Arrays.asList(1, 2, 3);
        for (int p = 1, n = 3 * 2 * 1; p <= n; p++) {
            subtestAgainst(a123, p);
        }
    }

    // This test can take a while - ~10 seconds, hence "slow".
    @Test
    public void testAgainstEachOtherSlow() {
        List<Integer> a110 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        for (int p = 1, n = 10 * 9 * 8 * 7 * 6 * 5 * 4 * 3 * 2 * 1; p <= n; p++) {
            subtestAgainst(a110, p);
        }

    }

    private void subtestAgainst(List<Integer> input, int p) {
        List<Integer> one = permuteUtil.nthPermutation(input, BigInteger.valueOf(p));
        List<Integer> two = localReimplementPermutation(input, p);

        Assert.assertEquals(one, two);
    }


    @Test
    public void testHelperSizeThreeOne() {
        List<Integer> actual = permuteUtil.getPermutationIndexes(3, 1);
        List<Integer> expected = Arrays.asList(0, 1, 2);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testHelperSizeThreeTwo() {
        List<Integer> actual = permuteUtil.getPermutationIndexes(3, 2);
        List<Integer> expected = Arrays.asList(0, 2, 1);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testHelperSizeThreeThree() {
        List<Integer> actual = permuteUtil.getPermutationIndexes(3, 3);
        List<Integer> expected = Arrays.asList(1, 0, 2);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testHelperSizeThreeFour() {
        List<Integer> actual = permuteUtil.getPermutationIndexes(3, 4);
        List<Integer> expected = Arrays.asList(1, 2, 0);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testHelperHugeThree() {
        List<Integer> actual = permuteUtil.getPermutationIndexes(300, 3);
        Assert.assertEquals(300, actual.size());
    }

    @Test
    public void testHelperHugeMaxInt() {
        List<Integer> actual = permuteUtil.getPermutationIndexes(300, Integer.MAX_VALUE);
        Assert.assertEquals(300, actual.size());
    }

    @Test
    public void testHelperHugeMaxIntTimesTenThousand() {
        List<Integer> actual = permuteUtil.getPermutationIndexes(300, BigInteger.valueOf(Integer.MAX_VALUE).multiply(BigInteger.valueOf(10000)));
        Assert.assertEquals(300, actual.size());
        // 1-282 are in order, then shuffling starts:  ... 280, 281, 282, 284, 283, 291, 289, 295, ...
        // System.out.println("TenThousand=" + actual);
    }

    @Test
    public void testHelperHugeMaxIntTimesTenMillion() {
        List<Integer> actual = permuteUtil.getPermutationIndexes(300, BigInteger.valueOf(Integer.MAX_VALUE).multiply(BigInteger.valueOf(10000000)));
        Assert.assertEquals(300, actual.size());
        // 1-280 are in order, then shuffling starts:  ... 278, 279, 280, 284, 288, 289, 290,
        //System.out.println("TenMillion=" + actual);
    }

    @Test
    public void testHelperHugeMaxIntTimesMaxInt() {
        List<Integer> actual = permuteUtil.getPermutationIndexes(300, BigInteger.valueOf(Integer.MAX_VALUE).multiply(BigInteger.valueOf(Integer.MAX_VALUE)));
        Assert.assertEquals(300, actual.size());
        // 1-278 are in order, then shuffling starts:  ... 276, 277, 278, 280, 297, 298,
        //System.out.println("Max*Max=" + actual);
    }

    @Test
    public void testHelperHugeMaxIntTimesMax4() {
        BigInteger max2 = BigInteger.valueOf(Integer.MAX_VALUE).multiply(BigInteger.valueOf(Integer.MAX_VALUE));
        BigInteger max4 = max2.multiply(max2).multiply(max2).multiply(max2);
        // max4:452312846898269724422641179697543667450922081019251166843171382875033436161
        // System.out.println("max4:" + max4);
        List<Integer> actual = permuteUtil.getPermutationIndexes(300, max4);
        Assert.assertEquals(300, actual.size());
        // 1-243 are in order, then shuffling starts:  ... 241, 242, 243, 279, 278, 265,
        //System.out.println("Max4=" + actual);
    }

    @Test
    public void testHelperThirtyThousand() {
        // A "default" JVM gives 890mb, which is large enough for 30,000
        final int size = 30000;
        List<Integer> actual = permuteUtil.getPermutationIndexes(size, 50);
        Assert.assertEquals(size, actual.size());
        // 1-29994 are in order, then shuffling starts:  29992, 29993, 29994, 29997, 29995, 29996,
        //System.out.println("Max30000=" + actual);
    }

    @Test
    public void testHelperFindLargest() {
        final int mb = 1024*1024;
        Runtime runtime = Runtime.getRuntime();
        int mbmemory = (int) (runtime.maxMemory() / mb);
        int size = 100;
        if (mbmemory > 890) {
            size = 30000;
        }
        if (mbmemory > 2159) {
            size = 45000; // for 2160  50,000 is too big
        }
        if (mbmemory > 3640) {
            size = 60000;  // for 3641 62,500 is too big
        }
        System.out.println("Max Memory:" + mbmemory + " size=" + size);
        List<Integer> actual = permuteUtil.getPermutationIndexes(size, 12345);
        Assert.assertEquals(size, actual.size());
        // 1-29994 are in order, then shuffling starts:  29992, 29993, 29994, 29997, 29995, 29996,
        //System.out.println("Max30000=" + actual);
    }

    @Test
    public void testWiki() {
        // https://en.wikipedia.org/wiki/Permutation#Generation_in_lexicographic_order
        List<Integer> input = Arrays.asList(1, 2, 3, 4);
        List<Integer> actual, expected;

        //
        actual = permuteUtil.nthPermutation(input, BigInteger.valueOf(2));
        expected = Arrays.asList(1,2,4,3);
        Assert.assertEquals(expected, actual);

        // the next lexicographic permutation will be [1,3,2,4], and the 24th permutation will be [4,3,2,1]
        actual = permuteUtil.nthPermutation(input, BigInteger.valueOf(3));
        expected = Arrays.asList(1,3,2,4);

        actual = permuteUtil.nthPermutation(input, BigInteger.valueOf(24));
        expected = Arrays.asList(4,3,2,1);
    }

    @Test
    public void testProjectEuler() {
        // https://projecteuler.net/index.php?section=problems&id=24
        List<Integer> input = Arrays.asList(0,1,2,3,4,5,6,7,8,9);
        BigInteger permutationNumber = BigInteger.valueOf(1000000);
        List<Integer> actual = permuteUtil.nthPermutation(input, permutationNumber);
        List<Integer> expected = Arrays.asList(2,7,8,3,9,1,5,4,6,0);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testFactorialAgainstEachOther() {
        // Integer return: max 16, 17 overflows
        // Long return:    max 20, 21 overflows
        for (int i = 0, n = 21; i < n; i++) {
            subtestFactorialAgainstEachOther(i);
        }
    }
    private void subtestFactorialAgainstEachOther(int i) {
        long one = localReimplementFactorial(i);
        BigInteger two = permuteUtil.factorial(i);

        Assert.assertEquals("i=" + i, BigInteger.valueOf(one),  two);
        Assert.assertTrue("i= " + i + " ret not positive: " + one, one > 0);
    }

    @Test
    public void testLocalFactorialAtOverflow() {
        int overflowLong = 21;
        long one = localReimplementFactorial(overflowLong);
        BigInteger two = permuteUtil.factorial(overflowLong);

        // Look at this test - it passes!!
        Assert.assertEquals("i=" + overflowLong, one,  two.longValue());

        // The correct test reveals the overflow:
        Assert.assertNotEquals("i=" + overflowLong, BigInteger.valueOf(one), two);
    }

    @Test
    public void testLargestPermutationNumber() {
        Assert.assertEquals(BigInteger.valueOf(6), permuteUtil.getLargestPermutationNumberForListSize(3));
        Assert.assertEquals(BigInteger.valueOf(3628800), permuteUtil.getLargestPermutationNumberForListSize(10));

        // factorial(30000) has 112,000+ digits, and has 7,508 "0"s at the end of it
        // Assert.assertEquals(BigInteger.valueOf(BIG'N), permuteUtil.getLargestPermutationNumberForListSize(30000));
        BigInteger api = permuteUtil.getLargestPermutationNumberForListSize(30000);
        BigInteger expected = permuteUtil.factorial(30000);
        Assert.assertEquals(expected, api);
    }



    //
    //
    //

    /**
     * @param numberList, size N
     * @param k  range 1 to N!
     * @return
     */
    private <T> List<T> localReimplementPermutation(List<T> originalList, int k) {

        List<T> numberList = new ArrayList<T>(originalList);
        final int n = numberList.size();

        // change k to be index
        k--;

        int mod = (int) localReimplementFactorial(n);

        List<T> result = new ArrayList<T>();

        // find sequence
        for (int i = 0; i < n; i++) {
            mod = mod / (n - i);
            // find the right number(curIndex) of
            int curIndex = k / mod;
            // update k
            k = k % mod;

            // get number according to curIndex
            result.add(numberList.get(curIndex));
            // remove from list
            numberList.remove(curIndex);
        }

        return result;
    }
    private long localReimplementFactorial(final int n) {
        if (n < 0) {
            return 0;
        }
        long ret = 1;
        for (int i = 1; i <= n; i++) {
            ret = ret * i;
        }
        return ret;
    }

}
