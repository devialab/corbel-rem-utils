package io.corbel.resources.rem.utils

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
trait CorbelDomain {
  def get: String
}

object CorbelDomain {
  def apply(domain: String) = new CorbelDomain {
    override def get = domain
  }
}