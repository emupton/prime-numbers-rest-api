package app
import scala.collection.immutable.Range.Inclusive

/**
  * Created by emma on 19/02/2018.
  */
object AlgorithmA extends PrimeGenerator {

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
