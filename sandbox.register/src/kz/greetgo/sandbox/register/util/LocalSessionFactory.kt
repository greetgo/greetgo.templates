///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.util

///MODIFY replace sandbox {PROJECT_NAME}
///MODIFY replace sandbox {PROJECT_NAME}
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.db.DbProxyFactory
import kz.greetgo.db.GreetgoTransactionManager
import kz.greetgo.depinject.core.Bean
import kz.greetgo.depinject.core.HasAfterInject
import kz.greetgo.depinject.core.replace.BeanReplacer
import kz.greetgo.sandbox.controller.model.UserCan
import kz.greetgo.sandbox.register.util.my_batis.CustomBooleanTypeHandler
import kz.greetgo.sandbox.register.util.my_batis.CustomEnumTypeHandler
import org.apache.ibatis.logging.log4j.Log4jImpl
import org.apache.ibatis.mapping.Environment
import org.apache.ibatis.session.Configuration
import org.apache.ibatis.session.SqlSession
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.ibatis.session.SqlSessionFactoryBuilder
import org.apache.ibatis.type.JdbcType
import org.apache.log4j.Logger
import javax.sql.DataSource

abstract class LocalSessionFactory : BeanReplacer, HasAfterInject, DataSourceGetter {
  private val transactionManager = GreetgoTransactionManager()
  private val transactionFactory = LocalTransactionFactory(transactionManager)
  private val dbProxyFactory = DbProxyFactory(transactionManager)

///MODIFY replace Sandbox {PROJECT_CC_NAME}
  private lateinit var jdbcSandbox: JdbcSandbox

  @Bean
///MODIFY replace Sandbox {PROJECT_CC_NAME}
  fun createJdbcSandbox(): JdbcSandbox {
///MODIFY replace Sandbox {PROJECT_CC_NAME}
    return jdbcSandbox
  }

  private lateinit var sqlSessionFactory: SqlSessionFactory

  override lateinit var dataSource: DataSource

  fun getConfiguration(): Configuration {
    return sqlSessionFactory.configuration
  }

  override fun replaceBean(originalBean: Any, returnClass: Class<*>): Any {

    if (!returnClass.isInterface) {
      return originalBean
    }

    return dbProxyFactory.createProxyFor(originalBean, returnClass)

  }

  protected abstract fun createDataSource(): DataSource

  protected abstract fun databaseEnvironmentId(): String

  override fun afterInject() {
    dataSource = createDataSource()

    dataSource = DbLoggingProxyFactory.create(dataSource, object : DbLoggingProxyFactory.AbstractSqlViewer() {
      val logger = Logger.getLogger("DIRECT_SQL")

      override fun logTrace(message: String) {
        if (logger.isTraceEnabled) {
          logger.trace(message)
        }
      }
    })

///MODIFY replace Sandbox {PROJECT_CC_NAME}
    jdbcSandbox = JdbcSandbox(dataSource, transactionManager)

    val environment = Environment(databaseEnvironmentId(), transactionFactory, dataSource)

    val configuration = Configuration(environment)
    configuration.jdbcTypeForNull = JdbcType.NULL
    configuration.logImpl = Log4jImpl::class.java

    configuration.isMapUnderscoreToCamelCase = true

    val typeHandlerRegistry = configuration.typeHandlerRegistry

    typeHandlerRegistry.register(Boolean::class.java, CustomBooleanTypeHandler())
    typeHandlerRegistry.register(Boolean::class.javaPrimitiveType, CustomBooleanTypeHandler())
    typeHandlerRegistry.register(JdbcType.BOOLEAN, CustomBooleanTypeHandler())
    typeHandlerRegistry.register(UserCan::class.java, CustomEnumTypeHandler(UserCan::class.java))

    val sqlSessionFactoryBuilder = SqlSessionFactoryBuilder()

    sqlSessionFactory = sqlSessionFactoryBuilder.build(configuration)
  }

  fun sqlSession(): SqlSession {
    return sqlSessionFactory.openSession(true)
  }
}
