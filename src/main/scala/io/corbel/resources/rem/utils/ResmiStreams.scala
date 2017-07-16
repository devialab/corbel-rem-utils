package io.corbel.resources.rem.utils

import java.net.URI
import javax.ws.rs.core.Response

import com.google.gson.{JsonArray, JsonObject}
import io.corbel.lib.queries.request.Pagination
import io.corbel.resources.rem.Rem
import io.corbel.resources.rem.request._
import io.corbel.resources.rem.request.builder.RequestParametersBuilder
import rx.lang.scala.{Observable, Subscriber}

import scala.collection.JavaConversions._
import scala.compat.java8.OptionConverters._

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
trait ResmiStreams {

    val rem: Rem[JsonObject]

    def collectionStream(`type`: String, parameters: RequestParameters[CollectionParameters], uri: URI, entity: Option[JsonObject], excludedRem: Option[List[Rem[_]]] = None, pageSize: Int = 1000): Observable[JsonObject] = Observable(subscriber => {
      val req = (collectionRequest _).curried(`type`)(parameters)(uri)(entity)(excludedRem)(pageSize)
      getPage(req)(subscriber)
    })

    def relationStream(`type`: String, id: ResourceId, relation: String, parameters: RequestParameters[RelationParameters], entity: Option[JsonObject], excludedRem: Option[List[Rem[_]]] = None, pageSize: Int = 1000): Observable[JsonObject] = Observable(subscriber => {
      val req = (relationRequest _).curried(`type`)(id)(relation)(parameters)(entity)(excludedRem)(pageSize)
      getPage(req)(subscriber)
    })

    private def getPage(f: Int => Response, page: Int = 0)(subscriber: Subscriber[JsonObject]): Unit = {
      asResmiResponse(f(page)) match {
        case SuccessfulResponse(resp) =>
          val array = resp.getEntity.asInstanceOf[JsonArray]
          if(array == null || array.isEmpty) {
            subscriber.onCompleted()
          }
          else {
            array.foreach(json => subscriber.onNext(json.getAsJsonObject))
            getPage(f, page + 1)(subscriber)
          }
        case FailedResponse(resp) => subscriber.onError(new RuntimeException(s"Call to REM failed. ${resp.getStatus}: ${resp.getEntity}"))
        case Error(ex) => subscriber.onError(ex)
      }
    }

    private def collectionRequest(`type`: String, parameters: RequestParameters[CollectionParameters], uri: URI, entity: Option[JsonObject], excludedRem: Option[List[Rem[_]]] = None, pageSize: Int, page: Int): Response = {
      val pagination = new Pagination(page, pageSize)
      val builder  = new RequestParametersBuilder[CollectionParameters](parameters).apiParameters(new CollectionParametersImpl(
        pagination,
        parameters.getOptionalApiParameters.asScala.flatMap(_.getSort.asScala).asJava,
        parameters.getOptionalApiParameters.asScala.flatMap(_.getQueries.asScala).asJava,
        parameters.getOptionalApiParameters.asScala.flatMap(_.getConditions.asScala).asJava,
        parameters.getOptionalApiParameters.asScala.flatMap(_.getAggregation.asScala).asJava,
        parameters.getOptionalApiParameters.asScala.flatMap(_.getSearch.asScala).asJava
      ))
      val modifiedParameters = builder.build()
      if(excludedRem.nonEmpty) {
        rem.collection(`type`, modifiedParameters, uri, entity.asJava, excludedRem.map(implicitly[java.util.List[Rem[_]]](_)).asJava)
      }
      else {
        rem.collection(`type`, modifiedParameters, uri, entity.asJava)
      }
    }

    private def relationRequest(`type`: String, id: ResourceId, relation: String, parameters: RequestParameters[RelationParameters], entity: Option[JsonObject], excludedRem: Option[List[Rem[_]]] = None, pageSize: Int, page: Int): Response = {
      val pagination = new Pagination(page, pageSize)
      val builder  = new RequestParametersBuilder[RelationParameters](parameters).apiParameters(new RelationParametersImpl(
        pagination,
        parameters.getOptionalApiParameters.asScala.flatMap(_.getSort.asScala).asJava,
        parameters.getOptionalApiParameters.asScala.flatMap(_.getQueries.asScala).asJava,
        parameters.getOptionalApiParameters.asScala.flatMap(_.getConditions.asScala).asJava,
        parameters.getOptionalApiParameters.asScala.flatMap(_.getAggregation.asScala).asJava,
        parameters.getOptionalApiParameters.asScala.flatMap(_.getSearch.asScala).asJava,
        parameters.getOptionalApiParameters.asScala.flatMap(_.getPredicateResource.asScala).asJava
      ))
      val modifiedParameters = builder.build()
      if(excludedRem.nonEmpty) {
        rem.relation(`type`, id, relation, modifiedParameters, entity.asJava, excludedRem.map(implicitly[java.util.List[Rem[_]]](_)).asJava)
      }
      else {
        rem.relation(`type`, id, relation, modifiedParameters, entity.asJava)
      }
    }

}
