///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.debug.bean_containers

import kz.greetgo.depinject.core.BeanContainer
import kz.greetgo.depinject.core.Include
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.debug.beans.DebugServer

@Include(BeanConfigForDebugBeanContainer::class)
interface DebugBeanContainer : BeanContainer {
  fun server(): DebugServer
}
