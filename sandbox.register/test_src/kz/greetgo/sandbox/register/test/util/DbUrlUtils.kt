///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.test.util

object DbUrlUtils {
  fun changeUrlDbName(url: String, dbName: String): String {
    val idx = url.lastIndexOf('/')
    return url.substring(0, idx + 1) + dbName
  }

  fun extractDbName(url: String): String {
    val idx = url.lastIndexOf('/')
    return url.substring(idx + 1)
  }
}
