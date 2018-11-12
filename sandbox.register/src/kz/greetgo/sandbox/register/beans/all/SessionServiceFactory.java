///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.beans.all;

import kz.greetgo.db.DbType;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
///MODIFY replace sandbox {PROJECT_NAME}
///MODIFY replace Sandbox {PROJECT_CC_NAME}
import kz.greetgo.sandbox.register.util.JdbcSandbox;
import kz.greetgo.security.crypto.Crypto;
import kz.greetgo.security.session.SessionService;
import kz.greetgo.security.session.SessionStorage;

import static kz.greetgo.security.SecurityBuilders.newCryptoBuilder;
import static kz.greetgo.security.SecurityBuilders.newSessionServiceBuilder;
import static kz.greetgo.security.SecurityBuilders.newSessionStorageBuilder;

@Bean
public class SessionServiceFactory {

  ///MODIFY replace Sandbox {PROJECT_CC_NAME}
  public BeanGetter<JdbcSandbox> jdbcSandbox;

  @Bean
  public SessionService createSessionService() {

    Crypto crypto = newCryptoBuilder()
        ///MODIFY replace Sandbox {PROJECT_CC_NAME}
        .inDb(DbType.Postgres, jdbcSandbox.get())
        .setTableName("all_params")
        .setValueFieldName("value_blob")
        .setIdFieldName("name")
        .setPrivateKeyIdValue("session.primary.key")
        .setPublicKeyIdValue("session.public.key")
        .build();

    SessionStorage sessionStorage = newSessionStorageBuilder()
        ///MODIFY replace Sandbox {PROJECT_CC_NAME}
        .setJdbc(DbType.Postgres, jdbcSandbox.get())
        .setTableName("session_storage")
        .build();

    return newSessionServiceBuilder()
        .setSaltGeneratorOnCrypto(crypto, 17)
        .setSessionIdLength(17)
        .setTokenLength(17)
        .setStorage(sessionStorage)
        .build();
  }

}
