package io.corbel.resources.rem.utils.json

import com.google.gson.{JsonParser, JsonObject, Gson}
import org.json4s.DefaultFormats
import org.scalatest.{Matchers, FlatSpec}

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
class GsonScalaConversionsTest extends FlatSpec with Matchers {

  import GsonScalaConversions._

  behavior of "GsonScalaConversions"

  val gson = new Gson()
  implicit val format = DefaultFormats

  it should "convert JsonObject to Scala" in {
    val json = new JsonObject
    json.addProperty("a", "test")
    json.addProperty("b", 2)

    val converted = gson.jsonTreeToScala[TestObject](json)
    converted.a should be("test")
    converted.b should be(2)
  }

  it should "convert JsonArray to Scala" in {
    val json =  new JsonParser().parse("""[{"a":"t1", b:1}, {"a":"t2", b:2}]""")
    val converted = gson.jsonTreeToScala[Seq[TestObject]](json)
    converted.head.a should be("t1")
    converted.head.b should be(1)
    converted(1).a should be("t2")
    converted(1).b should be(2)
  }

  it should "convert Scala to JsonObject" in {
    val obj = TestObject("a", 1)
    val json = gson.scalaToJsonTree(obj).getAsJsonObject
    json.get("a").getAsString should be ("a")
    json.get("b").getAsInt should be (1)
  }

  it should "ignore extra fields" in {
    val json = new JsonParser().parse("""[{"a":"a", "b": 1, "c":"ignore"}]""")
    val converted = gson.jsonTreeToScala[Seq[TestObject]](json)
    converted.head.a should be("a")
    converted.head.b should be(1)
  }
}

case class TestObject(a: String, b: Int)