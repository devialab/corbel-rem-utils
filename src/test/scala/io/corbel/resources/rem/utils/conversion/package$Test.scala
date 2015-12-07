package io.corbel.resources.rem.utils.conversion

import java.util.Optional

import org.scalatest.{FlatSpec, Matchers, FunSuite}

/**
 * @author Alexander De Leon <alex.deleon@devialab.com>
 */
class package$Test extends FlatSpec with Matchers {

  "Optinal conversion" should "convert to Some[T]" in {
    import io.corbel.resources.rem.utils.conversion._
    val optional = Optional.of(1)
    val option: Option[Int] = optional
    option should be (Some(1))
  }
  it should "convert to None" in {
    import io.corbel.resources.rem.utils.conversion._
    val optional = Optional.empty[Int]()
    val option: Option[Int] = optional
    option should be (None)
  }

}
