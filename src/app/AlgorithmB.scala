package app

/**
  * Created by emma on 21/02/2018.
  */
object AlgorithmB extends PrimeGenerator {

  //creates an infinite stream of all prime numbers, lazily evaluates them
  val infinitePrimes: Stream[Int] = {
    def loop(v: Int): Stream[Int] = v #:: loop(v + 1)
    loop(0).filter( potentialPrime => isPrime(potentialPrime))
  }

  //takes any value in the infinitePrimes stream up to and inclusive of n
  def obtainPrimesUpToN(n: Int): Seq[Int] = {
    infinitePrimes.takeWhile(prime => prime <= n)
  }

}
