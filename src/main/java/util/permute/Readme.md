PermuteUtil in Java
===================
Java implementation of Lexicographic permutations given the permutation number.

Given a list size N, and a permutation number from 1 to at most N!, generate a shuffled list
whose order is the lexicographic permutation corresponding to the permutation number.


Written because there are so many bad, wrong, confusing implementations.
Most of the wrong implementations simply implement factorial(int n), without making note that 64-bit values start failing at 21!.

This implementation:
 * is in the public domain
 * can handle lists with 30,000 elements in 0.6 seconds [because of a caching implementation of factorial]
 * with 4GB Heap, can handle lists with 60,000 elements in 4.1 seconds
 * is implemented with generics for the List
 * has extensive unit-test coverage, including a "mini" implementation that allows cross-checking at small sizes
 

This implementation is in the public domain - copy it into your repository, rename it if you want, remove any/all
comments, etc.  At least for Java, you never, ever, have to implement this again.





Documentation
----
 * [Wikipedia] - formal math definition of the algorithm
 * [ProjectEuler24] - Project Euler challenge 24 on Lexicographic permutations
 * [StackOverflow] - Best explanation with real code


[StackOverflow]:http://stackoverflow.com/questions/7918806/finding-n-th-permutation-without-computing-others
[MathStack]:http://math.stackexchange.com/questions/60742/finding-the-n-th-lexicographic-permutation-of-a-string
[ProjectEuler24]:https://projecteuler.net/index.php?section=problems&id=24
[Wikipedia]:https://en.wikipedia.org/wiki/Permutation#Generation_in_lexicographic_order