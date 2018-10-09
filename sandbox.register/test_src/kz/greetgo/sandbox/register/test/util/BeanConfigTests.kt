///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.test.util

import kz.greetgo.depinject.core.BeanConfig
import kz.greetgo.depinject.core.Include
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.beans.all.BeanConfigAll
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.test.beans.BeanConfigTestBeans
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.test.dao.postgres.BeanConfigTestDao

@BeanConfig
@Include(BeanConfigTestDao::class, BeanConfigTestBeans::class, BeanConfigAll::class)
class BeanConfigTests
