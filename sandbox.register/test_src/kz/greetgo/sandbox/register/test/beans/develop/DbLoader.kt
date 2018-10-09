///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.test.beans.develop

///MODIFY replace sandbox {PROJECT_NAME}
///MODIFY replace sandbox {PROJECT_NAME}
///MODIFY replace sandbox {PROJECT_NAME}

import kz.greetgo.depinject.core.Bean
import kz.greetgo.depinject.core.BeanGetter
import kz.greetgo.sandbox.controller.model.UserCan
import kz.greetgo.sandbox.register.beans.all.IdGenerator
import kz.greetgo.sandbox.register.test.dao.AuthTestDao
import kz.greetgo.security.password.PasswordEncoder
import org.apache.log4j.Logger
import java.sql.Timestamp
import java.text.SimpleDateFormat

@Bean
class DbLoader {
  internal val logger = Logger.getLogger(javaClass)


  lateinit var authTestDao: BeanGetter<AuthTestDao>
  lateinit var idGenerator: BeanGetter<IdGenerator>
  lateinit var passwordEncoder: BeanGetter<PasswordEncoder>

  @Throws(Exception::class)
  fun loadTestData() {

    loadPersons()

    logger.info("FINISH")
  }

  @Throws(Exception::class)
  private fun loadPersons() {
    logger.info("Start loading persons...")

    user("Пушкин Александр Сергеевич", "1799-06-06", "pushkin")
    user("Сталин Иосиф Виссарионович", "1878-12-18", "stalin")
    user("Берия Лаврентий Павлович", "1899-03-17", "beria")
    user("Есенин Сергей Александрович", "1895-09-21", "esenin")
    user("Путин Владимир Владимирович", "1952-10-07", "putin")
    user("Назарбаев Нурсултан Абишевич", "1940-07-06", "papa")
    user("Менделеев Дмитрий Иванович", "1834-02-08", "mendeleev")
    user("Ломоносов Михаил Васильевич", "1711-11-19", "lomonosov")
    user("Бутлеров Александр Михайлович", "1828-09-15", "butlerov")

    add_can("pushkin", UserCan.VIEW_USERS)
    add_can("stalin", UserCan.VIEW_USERS)
    add_can("stalin", UserCan.VIEW_ABOUT)

    logger.info("Finish loading persons")
  }

  @Throws(Exception::class)
  private fun user(fioStr: String, birthDateStr: String, accountName: String) {
    val id = idGenerator.get().newId()
    val fio = fioStr.split("\\s+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val birthDate = sdf.parse(birthDateStr)
    val encryptPassword = passwordEncoder.get().encode("111")

    authTestDao.get().insertPerson(id, accountName, encryptPassword)
    authTestDao.get().updatePersonField(id, "birth_date", Timestamp(birthDate.time))
    authTestDao.get().updatePersonField(id, "surname", fio[0])
    authTestDao.get().updatePersonField(id, "name", fio[1])
    authTestDao.get().updatePersonField(id, "patronymic", fio[2])
  }

  private fun add_can(username: String, vararg cans: UserCan) {
    for (can in cans) {
      authTestDao.get().upsert(can.name)
      authTestDao.get().personCan(username, can.name)
    }
  }
}
