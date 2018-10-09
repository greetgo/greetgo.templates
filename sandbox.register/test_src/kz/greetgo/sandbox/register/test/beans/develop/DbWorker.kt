///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.test.beans.develop

import kz.greetgo.depinject.core.Bean
import kz.greetgo.depinject.core.BeanGetter
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.beans.all.AllConfigFactory
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.configs.DbConfig
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.test.util.DbUrlUtils
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.util.App
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.util.LiquibaseManager
import kz.greetgo.util.ServerUtil
import org.apache.log4j.Logger
import org.postgresql.util.PSQLException

import java.io.PrintStream
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.util.HashSet

import kz.greetgo.conf.sys_params.SysParams.pgAdminPassword
import kz.greetgo.conf.sys_params.SysParams.pgAdminUrl
import kz.greetgo.conf.sys_params.SysParams.pgAdminUserid

@Bean
class DbWorker {
  internal val logger = Logger.getLogger(javaClass)

  lateinit var postgresDbConfig: BeanGetter<DbConfig>
  lateinit var allPostgresConfigFactory: BeanGetter<AllConfigFactory>
  lateinit var liquibaseManager: BeanGetter<LiquibaseManager>

  private val alreadyRecreatedUsers = HashSet<String>()

  val postgresAdminConnection: Connection
    @Throws(Exception::class)
    get() {
      Class.forName("org.postgresql.Driver")
      return DriverManager.getConnection(pgAdminUrl(), pgAdminUserid(), pgAdminPassword())
    }

  @Throws(Exception::class)
  fun recreateAll() {
    prepareDbConfig()
    recreateDb()

    liquibaseManager.get().apply()
    App.do_not_run_liquibase_on_deploy_war().createNewFile()
  }

  @Throws(Exception::class)
  private fun recreateDb() {

    val dbName = DbUrlUtils.extractDbName(postgresDbConfig.get().url())
    val username = postgresDbConfig.get().username()
    val password = postgresDbConfig.get().password()

    postgresAdminConnection.use { con ->

      try {
        con.createStatement().use { stt ->
          logger.info("drop database $dbName")
          stt.execute("drop database $dbName")
        }
      } catch (e: PSQLException) {
        System.err.println(e.serverErrorMessage)
      }

      if (!alreadyRecreatedUsers.contains(username)) {
        alreadyRecreatedUsers.add(username)

        try {
          con.createStatement().use { stt ->
            logger.info("drop user $username")
            stt.execute("drop user $username")
          }
        } catch (e: SQLException) {
          println(e.message)
          //ignore
        }

        try {
          con.createStatement().use { stt ->
            logger.info("create user $username encrypted password '$password'")
            stt.execute("create user $username encrypted password '$password'")
          }
        } catch (e: PSQLException) {
          val sem = e.serverErrorMessage
          if ("CreateRole" == sem.routine) {
            throw RuntimeException("Невозможно создать пользователя " + username + ". Возможно кто-то" +
              " приконектился к базе данных под этим пользователем и поэтому он не удаляется." +
              " Попробуйте разорвать коннект с БД или перезапустить БД. После повторите операцию снова", e)
          }

          throw e
        }

      }

      con.createStatement().use { stt ->
        logger.info("create database $dbName")
        stt.execute("create database $dbName")
      }
      con.createStatement().use { stt ->
        logger.info("grant all on database $dbName to $username")
        stt.execute("grant all on database $dbName to $username")
      }
    }
  }


  fun cleanConfigsForTeamcity() {
    if (System.getProperty("user.name").startsWith("teamcity")) {
      ServerUtil.deleteRecursively(App.appDir())
    }
  }

  @Throws(Exception::class)
  private fun prepareDbConfig() {
    val file = allPostgresConfigFactory.get().storageFileFor(DbConfig::class.java)

    if (!file.exists()) {
      file.parentFile.mkdirs()
      writeDbConfigFile()
    } else if (postgresDbConfig.get().url() == "null") {
      writeDbConfigFile()
      allPostgresConfigFactory.get().reset()
    }
  }

  @Throws(Exception::class)
  private fun writeDbConfigFile() {
    val file = allPostgresConfigFactory.get().storageFileFor(DbConfig::class.java)
    PrintStream(file, "UTF-8").use { out ->

///MODIFY replace sandbox {PROJECT_NAME}
      val name = "sandbox"

      out.println("url=" + DbUrlUtils.changeUrlDbName(pgAdminUrl(), System.getProperty("user.name") + "_" + name))
      out.println("username=" + System.getProperty("user.name") + "_" + name)
      out.println("password=111")

    }
  }
}
