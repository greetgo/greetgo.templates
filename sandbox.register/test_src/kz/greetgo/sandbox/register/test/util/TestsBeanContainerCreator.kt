///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.test.util

import kz.greetgo.depinject.Depinject
import kz.greetgo.depinject.NoImplementor
import kz.greetgo.depinject.gen.DepinjectUtil

import java.util.Date

object TestsBeanContainerCreator {
  fun create(): TestsBeanContainer {
    try {
      return Depinject.newInstance(TestsBeanContainer::class.java)
    } catch (ignore: NoImplementor) {

      try {
        DepinjectUtil.implementAndUseBeanContainers(
///MODIFY replace sandbox {PROJECT_NAME}
          "kz.greetgo.sandbox.register.test",
          "build/create/recreate_src/" + Date().time
        )
      } catch (e: Exception) {
        throw RuntimeException(e)
      }

      return Depinject.newInstance(TestsBeanContainer::class.java)
    }

  }
}
