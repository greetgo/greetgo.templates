///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.util

import java.io.File

object App {
  fun appDir(): String {
///MODIFY replace sandbox {PROJECT_NAME}
    return System.getProperty("user.home") + "/sandbox.d"
  }

  fun securityDir(): String {
    return appDir() + "/security"
  }

  @Suppress("FunctionName")
  fun do_not_run_liquibase_on_deploy_war(): File {
    return File(appDir() + "/do_not_run_liquibase_on_deploy_war")
  }
}
