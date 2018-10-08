///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.server.app

import kz.greetgo.depinject.core.BeanConfig
import kz.greetgo.depinject.core.Include
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.beans.all.BeanConfigAll
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.server.beans.BeanConfigServer

@BeanConfig
@Include(BeanConfigServer::class, BeanConfigAll::class)
class BeanConfigApplication
