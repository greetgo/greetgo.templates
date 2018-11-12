///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.test.util

import org.testng.ISuite
import org.testng.ISuiteListener

class TestNgRecreateDbListener : ISuiteListener {
  override fun onFinish(suite: ISuite) {}

  override fun onStart(suite: ISuite) {
    if (suite.name != "All Db Test Suite") {
      return
    }

    prepareDatabases()

  }

  private fun prepareDatabases() {
    val postgresDbWorker = TestsBeanContainerCreator.create().dbWorker()
    postgresDbWorker.cleanConfigsForTeamcity()
    postgresDbWorker.recreateAll()
  }
}
