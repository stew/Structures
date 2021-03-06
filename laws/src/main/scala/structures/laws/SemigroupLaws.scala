package structures
package laws

import org.scalacheck.Arbitrary
import org.scalacheck.Prop._
import org.typelevel.discipline.Laws

object SemigroupLaws {
  def apply[A: Semigroup]: SemigroupLaws[A] = new SemigroupLaws[A] {
    def typeClass = Semigroup[A]
  }
}

trait SemigroupLaws[A] extends Laws {

  import Semigroup.Adapter

  implicit def typeClass: Semigroup[A]

  def semigroupProperties(implicit
    arbA: Arbitrary[A]
  ) = Seq(
    "append associativity" -> forAll { (x: A, y: A, z: A) =>
      ((x |+| y) |+| z) == (x |+| (y |+| z))
    }
  )

  def semigroup(implicit arbA: Arbitrary[A]): RuleSet = new RuleSet {
    def name = "semigroup"
    def bases = Nil
    def parents = Nil
    def props = semigroupProperties
  }
}

