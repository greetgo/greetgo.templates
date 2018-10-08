///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.util

interface LiquibaseManager {
  @Throws(Exception::class)
  fun apply()
}
