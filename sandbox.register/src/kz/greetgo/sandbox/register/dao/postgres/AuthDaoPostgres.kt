///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.dao.postgres

import kz.greetgo.depinject.core.Bean
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.dao.AuthDao

@Bean
interface AuthDaoPostgres : AuthDao
