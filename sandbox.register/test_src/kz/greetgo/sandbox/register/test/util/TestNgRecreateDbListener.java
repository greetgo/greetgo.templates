///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.test.util;

///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.test.beans.develop.DbWorker;
import org.testng.ISuite;
import org.testng.ISuiteListener;

public class TestNgRecreateDbListener implements ISuiteListener {
  @Override
  public void onFinish(ISuite suite) {}

  @Override
  public void onStart(ISuite suite) {
    if (!"All Db Test Suite".equals(suite.getName())) {
      return;
    }

    try {
      prepareDatabases();
    } catch (RuntimeException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static void prepareDatabases() throws Exception {
    {
      DbWorker postgresDbWorker = TestsBeanContainerCreator.create().dbWorker();
      postgresDbWorker.cleanConfigsForTeamcity();
      postgresDbWorker.recreateAll();
    }
  }
}
