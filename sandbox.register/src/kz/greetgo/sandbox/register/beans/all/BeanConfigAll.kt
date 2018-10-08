///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.beans.all

import kz.greetgo.depinject.core.BeanConfig
import kz.greetgo.depinject.core.BeanScanner
import kz.greetgo.depinject.core.Include
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.controller.BeanConfigControllers
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.dao.postgres.BeanConfigPostgresDao
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.impl.BeanConfigRegisterImpl

@BeanConfig
@BeanScanner
@Include(BeanConfigRegisterImpl::class, BeanConfigPostgresDao::class, BeanConfigControllers::class)
class BeanConfigAll
