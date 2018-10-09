///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.debug.launchers

import kz.greetgo.depinject.Depinject
import kz.greetgo.depinject.gen.DepinjectUtil
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.util.Modules
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.debug.bean_containers.DebugBeanContainer

object LaunchDebugServer {

  @JvmStatic
  fun main(args: Array<String>) {
    DepinjectUtil.implementAndUseBeanContainers(
///MODIFY replace sandbox {PROJECT_NAME}
      "kz.greetgo.sandbox.debug",
      Modules.debugDir().toString() + "/build/src_bean_containers")

    val container = Depinject.newInstance(DebugBeanContainer::class.java)

    container.server().start().join()
  }

}
