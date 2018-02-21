package app

/**
  * Created by emma on 21/02/2018.
  */
object AlgorithmB extends PrimeGenerator {

  val infinitePrimes: Stream[Int] = {
    def loop(v: Int): Stream[Int] = v #:: loop(v + 1)
    loop(0).filter( potentialPrime => isPrime(potentialPrime))
  }

  def obtainPrimesUpToN(n: Int): Seq[Int] = {
    infinitePrimes.takeWhile(prime => prime <= n)
  }

}
