package io.corbel.resources.rem

import com.google.gson.JsonObject
import io.corbel.resources.rem.service.RemService
import org.springframework.http.{HttpMethod, MediaType}

import scala.collection.JavaConversions._

/**
 * @author Alexander De Leon <alex.deleon@devialab.com>
 */
package object utils {
  type RemProvider[T] = HttpMethod => Rem[T]

  def rem[T](path: String, acceptedMediaTypes: Seq[MediaType], excludingRems: Seq[Rem[_]] = Seq.empty)(implicit remService: RemService): RemProvider[T] =
    (method: HttpMethod) => Option(remService.getRem(path, acceptedMediaTypes, method, excludingRems)).map(_.asInstanceOf[Rem[T]]) match {
      case Some(rem) => rem
      case None =>  throw new IllegalStateException(s"REM not found for: $path : $acceptedMediaTypes : $method")
    }

  def resmi(path: String, excludingRems: Seq[Rem[_]] = Seq.empty)(implicit remService: RemService) = rem[JsonObject](path, Seq(MediaType.APPLICATION_JSON), excludingRems)

}
