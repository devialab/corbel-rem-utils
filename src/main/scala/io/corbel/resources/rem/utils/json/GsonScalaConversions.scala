package io.corbel.resources.rem.utils.json

import com.google.gson.{Gson, JsonElement}
import org.json4s.Formats
import org.json4s.native.Serialization._


/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
object GsonScalaConversions {

  implicit class GsonWrapper(val gson: Gson) extends AnyVal {
    def scalaToJsonTree[T <: AnyRef](obj: T)(implicit format: Formats) = gson.fromJson(write[T](obj), classOf[JsonElement])

    def jsonTreeToScala[T <: AnyRef](json: JsonElement)(implicit format: Formats,  manifest: Manifest[T]) = read[T](json.toString)
  }

}
