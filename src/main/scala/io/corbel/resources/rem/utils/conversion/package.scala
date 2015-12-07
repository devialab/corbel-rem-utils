package io.corbel.resources.rem.utils

/**
 * @author Alexander De Leon <alex.deleon@devialab.com>
 */
package object conversion {

  implicit def toScalaOption[T](optional: java.util.Optional[T]): Option[T] =
    Option(optional.orElse(null.asInstanceOf[T]))


}
