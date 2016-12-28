package util.permute;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PermuteUtil - rearrange a list to the Nth permutation.
 *
 * Examples:
 *  [0, 1, 2], 1 ==> [0, 1, 2]    // "1st" is "no permutation"
 *  [0, 1, 2], 2 ==> [0, 2, 1]    // start swapping right hand side
 *  [0, 1, 2], 3 ==> [1, 0, 2]
 *  [0, 1, 2], 4 ==> [1, 2, 0]    // again swap right hand side
 *
 *
 * Public Domain.
 * To the extent possible under law, Tim Tiemens has waived all copyright and related or neighboring rights to PermuteUtil.java.
 * This work is published from: United States.
 *
 * @author Tim.Tiemens
 * @version 1.0.0
 *
 */
public class PermuteUtil {

    /**
     * Create best-of-breed permute utility instance.
     * Can permute lists up to 30,000 long in less that 1 second.
     *
     * @return a permute util that will cache up to "N" BigInteger objects (for factorial).
     */
    public PermuteUtil createCaching() {
        return new PermuteUtil();
    }

    /**
     * Not recommended.  It saves on memory, but increases computation time exponentially.
     *
     * @return a permute util that does not cache BigIntegers, which means it is slower for large list sizes.
     *
     */
    public PermuteUtil createSlowerp() {
        return new PermuteUtil(new PermuteUtil.FactorialImpl());
    }



    private Factorial factorial = new FactorialCacheImpl();


    // By default, factorial is caching, which means that calling factorial(30,000) will keep 30,000 BigIntegers in memory.
    public PermuteUtil() {

    }

    // Don't want a cache?  Use this constructor like so:
    //  PermuteUtil permute = new PermuteUtil(new PermuteUtil.FactorialImpl());
    // Or, pass in your own implementation.
    public PermuteUtil(Factorial inFactorial) {
        this.factorial = inFactorial;
    }


    /**
     * @param original list, size N (N is range 1 to Integer.MAX)
     * @param permutationNumber  range [1, N!] inclusive
     * @return List with ordering that matches the permutation specified by permutationNumber
     */
    public <T> List<T> nthPermutation(List<T> original, final BigInteger permutationNumber) {
        if (original == null) {
            throw new IllegalArgumentException("list cannot be null");
        }

        if (permutationNumber == null) {
            throw new IllegalArgumentException("permutation number cannot be null");
        }

        final int size = original.size();

        if (permutationNumber.compareTo(BigInteger.ONE) < 0) {
            throw new IllegalArgumentException("permutationNumber(" + permutationNumber + ") must be >= 1");
        }

        if (permutationNumber.compareTo(factorial(size)) > 0) {
            throw new IllegalArgumentException("For size(" + size + ") permutationNumber(" + permutationNumber + ") is out of bounds");
        }

        // the return list:
        List<T> ret = new ArrayList<T>();
        // local mutable copy of the original list:
        List<T> numbers = new ArrayList<T>(original);


        // Our input permutationNumber is [1,N!], but array indexes are [0,N!-1], so subtract one:
        BigInteger permNum = permutationNumber.subtract(BigInteger.ONE);

        for (int i = 1; i <= size; i++) {
            BigInteger factorialNminusI = factorial(size - i);

            // casting to integer is ok here, because even though permNum _could_ be big,
            //   the factorialNminusI is _always_ big
            int j = permNum.divide(factorialNminusI).intValue();

            permNum = permNum.mod(factorialNminusI);

            // remove item at index j, and put it in the return list at the end
            T item = numbers.remove(j);
            ret.add(item);
        }

        if (numbers.size() != 0) {
            throw new RuntimeException("Programmer error: size=" + numbers.size());
        }

        return ret;
    }


    /**
     * Helper - you provide the size, it generates the List<Integer> for you.
     *
     * @param size N of list to create, range 1-to-Integer.MAX
     * @param permutationNumber  range [1, Min(N!, Long.MAX_VALUE)] inclusive - capped by Long max value
     * @return List with integer indexes in the ordering that matches the permutation specified by permutationNumber
     *   each index is range [0, N-1] inclusive
     */
    public List<Integer> getPermutationIndexes(final int size, final long permutationNumber) {
        return getPermutationIndexes(size, BigInteger.valueOf(permutationNumber));
    }

    /**
     * Helper - you provide the size, it generates the List<Integer> for you.
     *
     * @param size N of list to create, range 1-to-Integer.MAX
     * @param permutationNumber  range [1, N!] inclusive
     * @return List with integer indexes in the ordering that matches the permutation specified by permutationNumber
     *   each index is range [0, N-1] inclusive
     */
    public List<Integer> getPermutationIndexes(final int size, final BigInteger permutationNumber) {
        // create the indexes, 0-to-N-1
        ArrayList<Integer> numberList = new ArrayList<Integer>();
        for (int i = 0; i < size; i++) {
            numberList.add(i);
        }

        return nthPermutation(numberList, permutationNumber);
    }


    public <T> BigInteger getLargestPermutationNumber(List<T> list) {
        return getLargestPermutationNumberForListSize(list.size());
    }

    public BigInteger getLargestPermutationNumberForListSize(final int size) {
        return factorial(size);
    }

    /*default*/ BigInteger factorial(int n) {
        return factorial.factorial(n);
    }

    //
    // Sub-problem:  factorial that can be cached.
    //

    public interface Factorial {
        public BigInteger factorial(int n);
    }


    public class FactorialImpl implements Factorial {
        /**
         * Factorial has a surprisingly low "overflow" limit.
         *   int  - 13! overflows
         *   long - 21! overflows
         * @param n
         * @return n! or 0
         */
        @Override
        public BigInteger factorial(int n) {
            if (n < 0) {
                return BigInteger.ZERO;
            }
            BigInteger ans = BigInteger.ONE;
            for (int i = 1; i <= n; ++i)
                ans = ans.multiply(BigInteger.valueOf(i));
            return ans;
        }
    }

    public class FactorialCacheImpl implements Factorial {
        private final Map<Integer, BigInteger> mapNtoAnswer = new HashMap<Integer, BigInteger>();
        private int mapUpToN = 0;
        public FactorialCacheImpl() {
            mapNtoAnswer.put(0, BigInteger.ONE);
            mapNtoAnswer.put(1, BigInteger.ONE);
            mapUpToN = 1;
        }
        @Override
        public BigInteger factorial(int n) {
            if (! mapNtoAnswer.containsKey(n)) {
                ensureCacheUpTo(n);
            }
            return mapNtoAnswer.get(n);
        }
        // record every answer on the way up to and including "n"
        private void ensureCacheUpTo(int n) {
            if (n > mapUpToN) {
                BigInteger biggest = mapNtoAnswer.get(mapUpToN);
                for (int i = mapUpToN + 1; i <= n; i++) {
                    BigInteger ifactorial = biggest.multiply(BigInteger.valueOf(i));
                    mapNtoAnswer.put(i, ifactorial);
                    mapUpToN = i;
                    biggest = ifactorial;
                }
            }
        }

    }
}
