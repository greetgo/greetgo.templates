///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.beans.all

import kz.greetgo.db.InTransaction
import kz.greetgo.depinject.core.Bean
import kz.greetgo.depinject.core.BeanGetter
import kz.greetgo.depinject.core.replace.ReplaceWithAnn
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.configs.DbConfig
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.util.LocalSessionFactory
import org.apache.commons.dbcp2.BasicDataSource

import javax.sql.DataSource

@Bean
@ReplaceWithAnn(InTransaction::class)
class DbSessionFactory : LocalSessionFactory() {

  lateinit var dbConfig: BeanGetter<DbConfig>

  override fun createDataSource(): DataSource {
    val pool = BasicDataSource()

    pool.driverClassName = "org.postgresql.Driver"
    pool.url = dbConfig.get().url()
    pool.username = dbConfig.get().username()
    pool.password = dbConfig.get().password()

    pool.initialSize = 0

    return pool
  }

  override fun databaseEnvironmentId(): String {
    return "DB_OPER"
  }
}
