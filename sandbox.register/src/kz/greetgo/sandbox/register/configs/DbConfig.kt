///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.configs


import kz.greetgo.conf.hot.DefaultStrValue
import kz.greetgo.conf.hot.Description

@Description("Параметры доступа к БД (используется только БД Postgresql)")
interface DbConfig {

  @Description("URL доступа к БД")
  @DefaultStrValue("jdbc:postgres:host:5432/db_name")
  fun url(): String

  @Description("Пользователь для доступа к БД")
  @DefaultStrValue("Some_User")
  fun username(): String

  @Description("Пароль для доступа к БД")
  @DefaultStrValue("Secret")
  fun password(): String
}
