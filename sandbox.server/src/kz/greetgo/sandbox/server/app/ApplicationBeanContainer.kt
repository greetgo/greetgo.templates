///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.server.app

import kz.greetgo.depinject.core.BeanContainer
import kz.greetgo.depinject.core.Include
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.server.beans.AppInitializer

@Include(BeanConfigApplication::class)
interface ApplicationBeanContainer : BeanContainer {
  fun appInitializer(): AppInitializer
}
