///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.beans.all

import kz.greetgo.depinject.core.Bean
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.configs.DbConfig
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.util.LocalConfigFactory

@Bean
class AllConfigFactory : LocalConfigFactory() {

  @Bean
  fun createPostgresDbConfig(): DbConfig {
    return createConfig(DbConfig::class.java)
  }

}
