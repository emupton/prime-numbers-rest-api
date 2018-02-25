package prime

import scala.collection.immutable.Range.Inclusive

/**
  * Created by emma on 21/02/2018.
  */
trait PrimeGenerator {

  /*Takes in an integer and returns true or false based on whether that integer is a prime number.
* -> Checks edge case that number isn't less than 2
* -> Generates a collection of potential divisors for the prospective prime number
* -> Iterates through this collection against a predicate that finds the modulus of a division of the prospective prime
*     over the current element in the collection, ensuring it isn't 0 (and by extension that the prospective prime isn't divisible)
* */
  def isPrime(prospectivePrime :Int): Boolean = {
    val candidatePrimeDivisors: Inclusive = 2 to math.sqrt(prospectivePrime).toInt
    val isPrime: Boolean = candidatePrimeDivisors.forall(counter => prospectivePrime % counter != 0)

    prospectivePrime >= 2 && isPrime
  }

  //Will obtain the prime numbers up to and including N
  def obtainPrimesUpToN(n: Int): Seq[Int]

}
