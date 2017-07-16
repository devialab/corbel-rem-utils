package io.corbel.resources.rem.utils

import java.net.URI
import java.util
import java.util.Optional
import javax.ws.rs.core.Response

import com.google.gson.{JsonArray, JsonElement, JsonObject}
import io.corbel.resources.rem.Rem
import io.corbel.resources.rem.request.{CollectionParameters, RelationParameters, RequestParameters, ResourceId}
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, Matchers}
import org.springframework.http.HttpMethod

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
class ResmiStreamsTest extends FlatSpec with Matchers with MockFactory with ResmiTest with ParametersBuilders {

  implicit val domain = CorbelDomain("test")

  val testPath = "/test"
  val streams = new ResmiStreams {
    override val rem: Rem[JsonObject] = resmiProvider(HttpMethod.GET)
  }

  val json1 = new JsonObject()
  val json2 = new JsonObject()
  val json3 = new JsonObject()

  "collectionStream" should "iterate over all pages" in {
    inSequence {
      (resmiGetMock.collection(_: String, _: RequestParameters[CollectionParameters], _:URI, _:Optional[JsonObject]))
        .when("t", *, *, Optional.empty[JsonObject]())
        .returns(Response.ok(jsonArray(json1, json2)).build()).noMoreThanOnce()
      (resmiGetMock.collection(_: String, _: RequestParameters[CollectionParameters], _:URI, _:Optional[JsonObject]))
        .when("t", *, *, Optional.empty[JsonObject]())
        .returns(Response.ok(jsonArray(json3)).build()).noMoreThanOnce()
      (resmiGetMock.collection(_: String, _: RequestParameters[CollectionParameters], _:URI, _:Optional[JsonObject]))
        .when("t", *, *, Optional.empty[JsonObject]())
        .returns(Response.ok(jsonArray()).build()).once()
    }

    val observable = streams.collectionStream("t", collectionEmptyParameters, null, None, None, 2)

    observable.toBlocking.toIterable.toList should be(List(json1, json2, json3))
  }

  "relationStream" should "iterate over all pages" in {
    val id = new ResourceId("id")
    inSequence {
      (resmiGetMock.relation(_: String, _: ResourceId, _: String,  _: RequestParameters[RelationParameters], _:Optional[JsonObject]))
        .when("t", id, "r", *, Optional.empty[JsonObject]())
        .returns(Response.ok(jsonArray(json1, json2)).build()).noMoreThanOnce()
      (resmiGetMock.relation(_: String, _: ResourceId, _: String,  _: RequestParameters[RelationParameters], _:Optional[JsonObject]))
        .when("t", id, "r", *, Optional.empty[JsonObject]())
        .returns(Response.ok(jsonArray(json3)).build()).noMoreThanOnce()
      (resmiGetMock.relation(_: String, _: ResourceId, _: String,  _: RequestParameters[RelationParameters], _:Optional[JsonObject]))
        .when("t", id, "r", *, Optional.empty[JsonObject]())
        .returns(Response.ok(jsonArray()).build()).once()
    }

    val observable = streams.relationStream("t", id, "r", relationEmptyParameters, None, None, 2)

    observable.toBlocking.toIterable.toList should be(List(json1, json2, json3))
  }

  private def jsonArray(e: JsonElement*): JsonArray = {
    val array = new JsonArray()
    e.foreach(array.add)
    array
  }
}
