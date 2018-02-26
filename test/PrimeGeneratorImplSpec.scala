import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}
import prime.{AlgorithmA, AlgorithmB, PrimeGenerator}

/**
  * Created by emma on 26/02/2018.
  */
class PrimeGeneratorImplSpec extends WordSpec with Matchers with ScalatestRouteTest {
/*usually I'd have a separate spec for each class, but it feels excessive here,
* I've tested the implementations of PrimeGenerator and the concrete functions of
* the trait it self in the same spec*/

  val primesUpTo30 = Seq(2,3,5,7,11,13,17,19,23,29)

  def scenarios(algorithm: PrimeGenerator): Unit = {
    "have an obtainPrimesUpToN function" which {
      "returns the existing primes up to a supplied limit" in {
        algorithm.obtainPrimesUpToN(30) shouldBe primesUpTo30
      }

      "handles <2 cases effectively" in {
        algorithm.obtainPrimesUpToN(1) shouldBe Nil
        algorithm.obtainPrimesUpToN(-7) shouldBe Nil
      }
    }
  }

  "AlgorithmA" should {
    scenarios(AlgorithmA)
  }

  "AlgorithmB" should {
    scenarios(AlgorithmB)
  }

  "PrimeGenerator" should {
    "have an isPrime that can accurately determine whether a number is prime" in {
      val stub = new PrimeGenerator{
        override def obtainPrimesUpToN(n: Int): Seq[Int] = Nil //don't care about this
      }
      primesUpTo30.forall(prime => stub.isPrime(prime)) shouldBe true
    }
  }
}
