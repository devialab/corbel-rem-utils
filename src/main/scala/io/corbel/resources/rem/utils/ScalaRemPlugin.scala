package io.corbel.resources.rem.utils

import io.corbel.resources.rem.plugin.RemPlugin
import io.corbel.resources.rem.service.RemService

/**
 * @author Alexander De Leon <alex.deleon@devialab.com>
 */
private[rem] abstract class ScalaRemPlugin extends RemPlugin {
  implicit def implicitRemService: RemService = remService
}
