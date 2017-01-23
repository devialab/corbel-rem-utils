package io.corbel.resources.rem.utils

import java.util.Optional

import io.corbel.lib.queries.builder.QueryParametersBuilder
import io.corbel.resources.rem.request._
import io.corbel.resources.rem.request.builder.RequestParametersBuilder
import org.springframework.http.MediaType

trait ParametersBuilders {

  protected def resourceParametersBuilder(implicit corbelDomain: CorbelDomain) =
    new RequestParametersBuilder[ResourceParameters](corbelDomain.get)
      .acceptedMediaType(MediaType.APPLICATION_JSON)

  protected def relationParametersBuilder(implicit corbelDomain: CorbelDomain) =
    new RequestParametersBuilder[RelationParameters](corbelDomain.get)
      .acceptedMediaType(MediaType.APPLICATION_JSON)

  protected def collectionParametersBuilder(implicit corbelDomain: CorbelDomain) =
    new RequestParametersBuilder[CollectionParameters](corbelDomain.get)
      .acceptedMediaType(MediaType.APPLICATION_JSON)

  protected def collectionEmptyParameters(implicit corbelDomain: CorbelDomain): RequestParameters[CollectionParameters] =
    collectionParametersBuilder.apiParameters(new CollectionParametersImpl(new QueryParametersBuilder().build())).build()

  protected def resourceEmptyParameters(implicit corbelDomain: CorbelDomain): RequestParameters[ResourceParameters] =
    resourceParametersBuilder.apiParameters(new ResourceParametersImpl(new QueryParametersBuilder().build())).build()

  protected def relationEmptyParameters(implicit corbelDomain: CorbelDomain): RequestParameters[RelationParameters] =
    relationParametersBuilder.apiParameters(new RelationParametersImpl(new QueryParametersBuilder().build(), Optional.empty())).build()

  protected def relationWithRelationIdParameters(relationId: String)(implicit corbelDomain: CorbelDomain): RequestParameters[RelationParameters] =
    relationParametersBuilder.apiParameters(new RelationParametersImpl(new QueryParametersBuilder().build(), Optional.of(relationId))).build()
}