package io.corbel.resources.rem.utils

import io.corbel.resources.rem.plugin.RemPlugin
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.support.GenericApplicationContext

import scala.reflect.{ClassTag, classTag}

/**
  * @author Alexander De Leon (alex.deleon@devialab.com)
  */
trait InjectableServices extends ScalaRemPlugin {

  def bootstrapApplicationContext[IoC: ClassTag]: ApplicationContext = {
    val parent: GenericApplicationContext = new GenericApplicationContext
    parent.getBeanFactory.registerSingleton("remService", implicitRemService)
    parent.getBeanFactory.registerSingleton("serviceLocator", implicitServiceLocator)
    parent.refresh
    val context: AnnotationConfigApplicationContext = new AnnotationConfigApplicationContext
    context.register(classTag[IoC].runtimeClass)
    context.setParent(parent)
    context.refresh
    return context
  }

}

/**
  * @deprecated use InjectableServices
  */
trait InjectableRemService extends InjectableServices