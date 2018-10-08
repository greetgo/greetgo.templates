///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.beans.all

import kz.greetgo.depinject.core.Bean
import kz.greetgo.depinject.core.BeanGetter
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.configs.DbConfig
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.util.LiquibaseManager
import liquibase.Liquibase
import liquibase.database.core.PostgresDatabase
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import java.sql.DriverManager

@Bean
class LiquibaseManagerImpl : LiquibaseManager {

  lateinit var dbConfig: BeanGetter<DbConfig>

  @Throws(Exception::class)
  override fun apply() {

    Class.forName("org.postgresql.Driver")

    DriverManager.getConnection(
      dbConfig.get().url(),
      dbConfig.get().username(),
      dbConfig.get().password()
    ).use { connection ->
      val database = PostgresDatabase()

      database.connection = JdbcConnection(connection)

      Liquibase(
        "liquibase/postgres/changelog-master.xml",
        ClassLoaderResourceAccessor(), database
      ).update("")
    }
  }
}
