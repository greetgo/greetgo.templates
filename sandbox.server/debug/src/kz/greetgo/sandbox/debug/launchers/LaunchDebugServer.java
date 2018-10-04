///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.debug.launchers;

import kz.greetgo.depinject.Depinject;
import kz.greetgo.depinject.gen.DepinjectUtil;
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.util.Modules;
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.debug.bean_containers.DebugBeanContainer;

public class LaunchDebugServer {
  public static void main(String[] args) throws Exception {
    new LaunchDebugServer().run();
  }

  private void run() throws Exception {
    DepinjectUtil.implementAndUseBeanContainers(
///MODIFY replace sandbox {PROJECT_NAME}
      "kz.greetgo.sandbox.debug",
      Modules.INSTANCE.debugDir() + "/build/src_bean_containers");

    DebugBeanContainer container = Depinject.newInstance(DebugBeanContainer.class);

    container.server().start().join();
  }
}
