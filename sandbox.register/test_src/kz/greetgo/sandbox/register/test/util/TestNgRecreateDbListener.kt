///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.test.util

///MODIFY replace sandbox {PROJECT_NAME}
import org.testng.ISuite
import org.testng.ISuiteListener

class TestNgRecreateDbListener : ISuiteListener {
  override fun onFinish(suite: ISuite) {}

  override fun onStart(suite: ISuite) {
    if ("All StandDb Test Suite" != suite.name) return

    try {
      prepareDatabases()
    } catch (e: Exception) {
      if (e is RuntimeException) throw e
      throw RuntimeException(e)
    }

  }

  @Throws(Exception::class)
  private fun prepareDatabases() {
    val postgresDbWorker = TestsBeanContainerCreator.create().dbWorker()
    postgresDbWorker.cleanConfigsForTeamcity()
    postgresDbWorker.recreateAll()
  }
}
