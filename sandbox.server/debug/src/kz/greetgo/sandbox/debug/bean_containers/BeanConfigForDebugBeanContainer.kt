///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.debug.bean_containers

import kz.greetgo.depinject.core.BeanConfig
import kz.greetgo.depinject.core.Include
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.controller.BeanConfigControllers
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.beans.all.BeanConfigAll
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.debug.beans.BeanConfigStand

@BeanConfig
@Include(BeanConfigStand::class, BeanConfigControllers::class, BeanConfigAll::class)
class BeanConfigForDebugBeanContainer
