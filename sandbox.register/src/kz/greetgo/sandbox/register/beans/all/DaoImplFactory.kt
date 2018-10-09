///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.beans.all

import kz.greetgo.depinject.core.Bean
import kz.greetgo.depinject.core.BeanGetter
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.util.AbstractMybatisDaoImplFactory
import org.apache.ibatis.session.Configuration
import org.apache.ibatis.session.SqlSession

@Bean
class DaoImplFactory : AbstractMybatisDaoImplFactory() {

  lateinit var dbSessionFactory: BeanGetter<DbSessionFactory>

  override fun getSqlSession(): SqlSession {
    return dbSessionFactory.get().sqlSession()
  }

  override fun getConfiguration(): Configuration {
    return dbSessionFactory.get().getConfiguration()
  }
}
