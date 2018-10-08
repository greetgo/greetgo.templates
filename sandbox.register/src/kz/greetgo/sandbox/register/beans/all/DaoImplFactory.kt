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

  override val sqlSession: SqlSession
    get() = dbSessionFactory.get().sqlSession()

  override val configuration: Configuration
    get() = dbSessionFactory.get().configuration
}
