///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.beans.all

import kz.greetgo.db.DbType
import kz.greetgo.depinject.core.Bean
import kz.greetgo.depinject.core.BeanGetter
///MODIFY replace sandbox {PROJECT_NAME}
///MODIFY replace Sandbox {PROJECT_CC_NAME}
import kz.greetgo.sandbox.register.util.JdbcSandbox
import kz.greetgo.security.session.SessionService

import kz.greetgo.security.SecurityBuilders.newCryptoBuilder
import kz.greetgo.security.SecurityBuilders.newSessionServiceBuilder
import kz.greetgo.security.SecurityBuilders.newSessionStorageBuilder

@Bean
class SessionServiceFactory {

///MODIFY replace Sandbox {PROJECT_CC_NAME}
  lateinit var jdbc: BeanGetter<JdbcSandbox>

  @Bean
  fun createSessionService(): SessionService {

    val crypto = newCryptoBuilder()
      .inDb(DbType.Postgres, jdbc.get())
      .setTableName("all_params")
      .setValueFieldName("value_blob")
      .setIdFieldName("name")
      .setPrivateKeyIdValue("session.primary.key")
      .setPublicKeyIdValue("session.public.key")
      .build()

    val sessionStorage = newSessionStorageBuilder()
      .setJdbc(DbType.Postgres, jdbc.get())
      .setTableName("session_storage")
      .build()

    return newSessionServiceBuilder()
      .setSaltGeneratorOnCrypto(crypto, 17)
      .setSessionIdLength(17)
      .setTokenLength(17)
      .setStorage(sessionStorage)
      .build()
  }

}
