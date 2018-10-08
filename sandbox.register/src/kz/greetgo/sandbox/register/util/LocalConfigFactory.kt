///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.util

import kz.greetgo.conf.hot.FileConfigFactory

abstract class LocalConfigFactory : FileConfigFactory() {
  override fun getBaseDir(): String {
    return App.appDir() + "/conf"
  }
}
