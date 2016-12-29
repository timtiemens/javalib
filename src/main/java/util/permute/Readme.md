PermuteUtil in Java
===================
Java implementation of Lexicographic permutations given the permutation number.

Given a list size N, and a permutation number from 1 to at most N!, generate a shuffled list
whose order is the lexicographic permutation corresponding to the permutation number.


Written because there are so many bad, wrong, confusing implementations.
Most of the wrong implementations simply implement factorial(int n), without making note that 64-bit values start failing at 21!.

This implementation:
 * has a very permissive license ([MIT])
 * can handle lists with 30,000 elements in 0.6 seconds [because of a caching implementation of factorial]
 * with 4GB Heap, can handle lists with 60,000 elements in 4.1 seconds
 * is implemented with generics for the List
 * has extensive unit-test coverage, including a "mini" implementation that allows cross-checking at small sizes
 

This implementation is in the public domain - copy it into your repository, rename it if you want, remove any/all
comments, etc.  At least for Java, you never, ever, have to implement this again.

Example Command Line
----
You can use util.permute.PermuteUtil from the command line.
First, build the .jar with gradle jar.

Then, try these command lines:
  * java -cp build/libs/javalib-0.5.5.jar util.permute.PermuteUtil 1000000 10
    This is Project Euler problem number 24.
    It prints
    [2, 7, 8, 3, 9, 1, 5, 4, 6, 0]

  * java -cp build/libs/javalib-0.5.5.jar util.permute.PermuteUtil 5934 a b c d e f g h
    You can specify the items in the list as arguments.
    It prints
    [b, c, d, f, a, h, g, e]

  * java -cp build/libs/javalib-0.5.5.jar  util.permute.PermuteUtil 1024 9 | tr , \\n | sed 's/\[/ /' |sed 's/\]//'
    Shows how to change into "1 line per permutation".
    It prints
 0
 1
 3
 5
 6
 7
 4
 8
 2



Documentation
----
 * [Wikipedia] - formal math definition of the algorithm
 * [ProjectEuler24] - Project Euler challenge 24 on Lexicographic permutations
 * [StackOverflow] - Best explanation with real code



[StackOverflow]:http://stackoverflow.com/questions/7918806/finding-n-th-permutation-without-computing-others
[MathStack]:http://math.stackexchange.com/questions/60742/finding-the-n-th-lexicographic-permutation-of-a-string
[ProjectEuler24]:https://projecteuler.net/index.php?section=problems&id=24
[Wikipedia]:https://en.wikipedia.org/wiki/Permutation#Generation_in_lexicographic_order
[MIT]:https://opensource.org/licenses/MIT
