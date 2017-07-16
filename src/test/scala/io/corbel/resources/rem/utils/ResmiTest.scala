package io.corbel.resources.rem.utils

import java.io.InputStream
import java.net.URI
import java.util
import java.util.Optional
import javax.ws.rs.core.Response

import com.google.gson.JsonObject
import io.corbel.resources.rem.Rem
import io.corbel.resources.rem.request._
import org.scalamock.scalatest.MockFactory
import org.scalatest.Suite
import org.springframework.http.HttpMethod

/**
 * @author Alexander De Leon <alex.deleon@devialab.com>
 */
trait ResmiTest extends Suite with MockFactory {

  val resmiGetMock = stub[MockableRem]
  val resmiPutMock = stub[MockableRem]
  val resmiPostMock = stub[MockableRem]
  val resmiDeleteMock = stub[MockableRem]
  val resmiProvider = mockFunction[HttpMethod, Rem[JsonObject]]

  val aclGetMock = stub[AclMockableRem]
  val aclPutMock = stub[AclMockableRem]
  val aclPostMock = stub[AclMockableRem]
  val aclDeleteMock = stub[AclMockableRem]
  val aclProvider = mockFunction[HttpMethod, Rem[InputStream]]

  //Provide RESMI for GET method
  resmiProvider.expects(HttpMethod.GET).returning(resmiGetMock).anyNumberOfTimes()
  //Provide RESMI for PUT method
  resmiProvider.expects(HttpMethod.PUT).returning(resmiPutMock).anyNumberOfTimes()
  //Provide RESMI for POST method
  resmiProvider.expects(HttpMethod.POST).returning(resmiPostMock).anyNumberOfTimes()
  //Provide RESMI for DELETE method
  resmiProvider.expects(HttpMethod.DELETE).returning(resmiDeleteMock).anyNumberOfTimes()

  //Provide ACL for GET method
  aclProvider.expects(HttpMethod.GET).returning(aclGetMock).anyNumberOfTimes()
  //Provide ACL for PUT method
  aclProvider.expects(HttpMethod.PUT).returning(aclPutMock).anyNumberOfTimes()
  //Provide ACL for POST method
  aclProvider.expects(HttpMethod.POST).returning(aclPostMock).anyNumberOfTimes()
  //Provide ACL for DELETE method
  aclProvider.expects(HttpMethod.DELETE).returning(aclDeleteMock).anyNumberOfTimes()
}


trait MockableRem extends Rem[JsonObject] {
  override def resource(`type`: String, id: ResourceId, parameters: RequestParameters[ResourceParameters], entity: Optional[JsonObject], excludedRems: Optional[util.List[Rem[_]]]): Response = ???

  override def collection(`type`: String, parameters: RequestParameters[CollectionParameters], uri: URI, entity: Optional[JsonObject], excludedRems: Optional[util.List[Rem[_]]]): Response = ???

  override def relation(`type`: String, id: ResourceId, relation: String, parameters: RequestParameters[RelationParameters], entity: Optional[JsonObject]): Response = ???

  override def relation(`type`: String, id: ResourceId, relation: String, parameters: RequestParameters[RelationParameters], entity: Optional[JsonObject], excludedRems: Optional[util.List[Rem[_]]]): Response = ???

  override def resource(`type`: String, id: ResourceId, parameters: RequestParameters[ResourceParameters], entity: Optional[JsonObject]): Response = ???

  override def collection(`type`: String, parameters: RequestParameters[CollectionParameters], uri: URI, entity: Optional[JsonObject]): Response = ???
}

trait AclMockableRem extends Rem[InputStream] {
  override def resource(`type`: String, id: ResourceId, parameters: RequestParameters[ResourceParameters], entity: Optional[InputStream], excludedRems: Optional[util.List[Rem[_]]]): Response = ???

  override def collection(`type`: String, parameters: RequestParameters[CollectionParameters], uri: URI, entity: Optional[InputStream], excludedRems: Optional[util.List[Rem[_]]]): Response = ???

  override def relation(`type`: String, id: ResourceId, relation: String, parameters: RequestParameters[RelationParameters], entity: Optional[InputStream]): Response = ???

  override def relation(`type`: String, id: ResourceId, relation: String, parameters: RequestParameters[RelationParameters], entity: Optional[InputStream], excludedRems: Optional[util.List[Rem[_]]]): Response = ???

  override def resource(`type`: String, id: ResourceId, parameters: RequestParameters[ResourceParameters], entity: Optional[InputStream]): Response = ???

  override def collection(`type`: String, parameters: RequestParameters[CollectionParameters], uri: URI, entity: Optional[InputStream]): Response = ???
}
