///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.test.util

import kz.greetgo.depinject.Depinject
import kz.greetgo.depinject.NoImplementor
import kz.greetgo.depinject.gen.DepinjectUtil
import java.util.*

object TestsBeanContainerCreator {
  fun create(): TestsBeanContainer {
    return try {
      Depinject.newInstance(TestsBeanContainer::class.java)
    } catch (ignore: NoImplementor) {

      DepinjectUtil.implementAndUseBeanContainers(
        ///MODIFY replace sandbox {PROJECT_NAME}
        "kz.greetgo.sandbox.register.test",
        "build/create/recreate_src/" + Date().time
      )

      Depinject.newInstance(TestsBeanContainer::class.java)
    }

  }
}
