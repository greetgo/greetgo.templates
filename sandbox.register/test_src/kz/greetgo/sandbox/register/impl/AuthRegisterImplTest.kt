///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.impl

import kz.greetgo.depinject.core.BeanGetter
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.errors.IllegalLoginOrPassword
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.model.SessionHolder
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.model.UserCan
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.register.AuthRegister
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.test.dao.AuthTestDao
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.test.util.ParentTestNg
import kz.greetgo.security.password.PasswordEncoder
import kz.greetgo.security.session.SessionService
import kz.greetgo.util.RND
import org.fest.assertions.api.Assertions.assertThat
import org.testng.annotations.Test

class AuthRegisterImplTest : ParentTestNg() {

  lateinit var authRegister: BeanGetter<AuthRegister>

  lateinit var passwordEncoder: BeanGetter<PasswordEncoder>
  lateinit var authTestDao: BeanGetter<AuthTestDao>
  lateinit var sessionService: BeanGetter<SessionService>

  @Test
  fun login() {

    val id = RND.str(10)
    val username = RND.str(10)
    val password = RND.str(10)
    val encodedPassword = passwordEncoder.get().encode(password)

    authTestDao.get().insertPerson(id, username, encodedPassword)

    //
    //
    val identity = authRegister.get().login(username, password)
    //
    //

    assertThat(identity).isNotNull
  }

  @Test(expectedExceptions = [IllegalLoginOrPassword::class])
  fun login_noUsername() {
    //
    //
    authRegister.get().login(RND.str(10), RND.str(10))
    //
    //
  }

  @Test(expectedExceptions = [IllegalLoginOrPassword::class])
  fun login_leftPassword() {
    val id = RND.str(10)
    val username = RND.str(10)
    val password = RND.str(10)
    val encodedPassword = passwordEncoder.get().encode(password)

    authTestDao.get().insertPerson(id, username, encodedPassword)

    //
    //
    authRegister.get().login(username, RND.str(10))
    //
    //
  }

  @Test(expectedExceptions = [IllegalLoginOrPassword::class])
  fun login_nullPassword() {
    val id = RND.str(10)
    val username = RND.str(10)
    val password = RND.str(10)
    val encodedPassword = passwordEncoder.get().encode(password)

    authTestDao.get().insertPerson(id, username, encodedPassword)

    //
    //
    authRegister.get().login(username, null)
    //
    //
  }

  @Test(expectedExceptions = [IllegalLoginOrPassword::class])
  fun login_emptyPassword() {
    val id = RND.str(10)
    val username = RND.str(10)
    val password = RND.str(10)
    val encodedPassword = passwordEncoder.get().encode(password)

    authTestDao.get().insertPerson(id, username, encodedPassword)

    //
    //
    authRegister.get().login(username, "")
    //
    //
  }

  @Test
  fun resetThreadLocalAndVerifySession_getSession_ok() {
    val sessionHolder = SessionHolder(RND.str(10), RND.str(10))
    val identity = sessionService.get().createSession(sessionHolder)

    //
    //
    authRegister.get().resetThreadLocalAndVerifySession(identity.id, identity.token)
    val actual = authRegister.get().session
    //
    //

    assertThat(actual).isNotNull
    assertThat(actual.personId).isEqualTo(sessionHolder.personId)
    assertThat(actual.mode).isEqualTo(sessionHolder.mode)
  }

  @Test
  fun resetThreadLocalAndVerifySession_getSession_leftToken() {
    val sessionHolder = SessionHolder(RND.str(10), RND.str(10))
    val identity = sessionService.get().createSession(sessionHolder)

    //
    //
    authRegister.get().resetThreadLocalAndVerifySession(identity.id, RND.str(10))
    val actual = authRegister.get().session
    //
    //

    assertThat(actual).isNull()
  }

  @Test
  fun resetThreadLocalAndVerifySession_getSession_nullToken() {
    val sessionHolder = SessionHolder(RND.str(10), RND.str(10))
    val identity = sessionService.get().createSession(sessionHolder)

    //
    //
    authRegister.get().resetThreadLocalAndVerifySession(identity.id, null)
    val actual = authRegister.get().session
    //
    //

    assertThat(actual).isNull()
  }

  @Test
  fun resetThreadLocalAndVerifySession_getSession_emptyToken() {
    val sessionHolder = SessionHolder(RND.str(10), RND.str(10))
    val identity = sessionService.get().createSession(sessionHolder)

    //
    //
    authRegister.get().resetThreadLocalAndVerifySession(identity.id, "")
    val actual = authRegister.get().session
    //
    //

    assertThat(actual).isNull()
  }

  @Test
  fun resetThreadLocalAndVerifySession_getSession_leftSessionId() {
    //
    //
    authRegister.get().resetThreadLocalAndVerifySession(RND.str(10), null)
    val actual = authRegister.get().session
    //
    //

    assertThat(actual).isNull()
  }

  @Test
  fun displayPerson() {

    val id = RND.str(10)
    val username = RND.str(10)
    val password = RND.str(10)
    val encodedPassword = passwordEncoder.get().encode(password)

    authTestDao.get().insertPerson(id, username, encodedPassword)

    UserCan.values()
      .map { it.name }
      .forEach { authTestDao.get().upsert(it) }

    authTestDao.get().personCan(username, UserCan.VIEW_ABOUT.name)
    authTestDao.get().personCan(username, UserCan.VIEW_USERS.name)

    val unknownCan = RND.str(10)
    authTestDao.get().upsert(unknownCan)
    authTestDao.get().personCan(username, unknownCan)

    val surname = RND.str(10)
    val name = RND.str(10)
    val patronymic = RND.str(10)
    authTestDao.get().updatePersonField(id, "surname", surname)
    authTestDao.get().updatePersonField(id, "name", name)
    authTestDao.get().updatePersonField(id, "patronymic", patronymic)

    //
    //
    val personDisplay = authRegister.get().displayPerson(id)
    //
    //

    assertThat(personDisplay).isNotNull
    assertThat(personDisplay.fio).isEqualTo(surname + ' '.toString() + name + ' '.toString() + patronymic)
    assertThat(personDisplay.username).isEqualTo(username)
    assertThat(personDisplay.cans).contains(UserCan.VIEW_ABOUT)
    assertThat(personDisplay.cans).contains(UserCan.VIEW_USERS)
    assertThat(personDisplay.cans).hasSize(2)

  }

  @Test(expectedExceptions = [NullPointerException::class])
  fun displayPerson_absent() {
    //
    //
    authRegister.get().displayPerson(RND.str(10))
    //
    //
  }

  @Test
  fun deleteSession() {

    val sessionHolder = SessionHolder(RND.str(10), RND.str(10))
    val identity = sessionService.get().createSession(sessionHolder)

    //
    //
    authRegister.get().deleteSession(identity.id)
    //
    //

    val sessionData = sessionService.get().getSessionData<Any>(identity.id)
    assertThat(sessionData).isNull()
  }
}
