package io.corbel.resources.rem

import javax.ws.rs.core.Response

import com.google.gson.JsonObject
import io.corbel.resources.rem.service.RemService
import org.json4s.Formats
import org.json4s.native.Serialization.write
import org.springframework.http.{HttpMethod, MediaType}

import scala.collection.JavaConversions._
import scala.util.{Failure, Success, Try}

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


  implicit class responseWrapper(val response: Response) extends AnyVal {
    /***
      *
      * @return true if status is between 200 and 299
      */
    def isOk = response.getStatus / 100 == 2
  }

  implicit class ResponseBuilderWrapper(val builder: Response.ResponseBuilder) extends AnyVal {
    def json[T <: AnyRef](obj: T)(implicit formats: Formats) =
      builder
        .`type`(javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE)
        .entity(write[T](obj))
  }


  def asResmiResponse(f: =>Response): ResmiRespose = Try(f) match {
    case Success(resp: Response) => resp
    case Failure(e) => Error(e)
  }

  sealed trait ResmiRespose
  case class SuccessfulResponse(response: Response) extends ResmiRespose
  case class FailedResponse(response: Response) extends ResmiRespose
  case class Error(exception: Throwable) extends ResmiRespose

  object ResmiRespose {
    implicit def responseAsResmiResponse(resp: Response): ResmiRespose = if(resp.isOk) SuccessfulResponse(resp) else FailedResponse(resp)
  }

}
