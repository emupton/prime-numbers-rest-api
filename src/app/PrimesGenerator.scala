package app
import scala.collection.immutable.Range.Inclusive

/**
  * Created by emma on 19/02/2018.
  */
object PrimesGenerator {

  /*Takes in an integer and returns true or false based on whether that integer is a prime number.
  * -> Checks edge case that number isn't less than 2
  * -> Generates a collection of potential divisors for the prospective prime number
  * -> Iterates through this collection against a predicate that finds the modulus of a division of the prospective prime
  *     over the current element in the collection, ensuring it isn't 0 (and by extension that the prospective prime isn't divisible)
  * */
  def isPrime(prospectivePrime :Int): Boolean = {
    val candidatePrimeDivisors: Inclusive = (2 to math.sqrt(prospectivePrime).toInt)
    val isPrime: Boolean = candidatePrimeDivisors.forall(counter => prospectivePrime % counter != 0)

    prospectivePrime >= 2 && isPrime
  }

  //generates a collection of all the integers between 0 and n, and then filters that collection based on whether they're a prime
  def obtainPrimesUpToN(n: Int): Seq[Int] = {
    if(n<2) {
      Nil
    }
    else {
      val numbersToN: Inclusive = (2 to n)
      numbersToN.filter(x => isPrime(x))
    }
  }

}
