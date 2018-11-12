///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.impl

import kz.greetgo.depinject.core.Bean
import kz.greetgo.depinject.core.BeanGetter
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.errors.IllegalLoginOrPassword
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.model.PersonDisplay
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.model.SessionHolder
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.register.AuthRegister
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.dao.AuthDao
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.model.PersonLogin
import kz.greetgo.security.password.PasswordEncoder
import kz.greetgo.security.session.SessionIdentity
import kz.greetgo.security.session.SessionService


@Bean
class AuthRegisterImpl : AuthRegister {

  lateinit var authDao: BeanGetter<AuthDao>

  lateinit var passwordEncoder: BeanGetter<PasswordEncoder>

  lateinit var sessionService: BeanGetter<SessionService>

  private val sessionDot = ThreadLocal<SessionHolder>()

  override fun login(username: String, password: String?): SessionIdentity {

    val personLogin: PersonLogin = authDao.get().selectByUsername(username) ?: throw IllegalLoginOrPassword()

    if (!passwordEncoder.get().verify(password, personLogin.encodedPassword)) {
      throw IllegalLoginOrPassword()
    }

    val sessionHolder = SessionHolder(personLogin.id, null)

    return sessionService.get().createSession(sessionHolder)
  }

  override fun resetThreadLocalAndVerifySession(sessionId: String?, token: String?) {
    sessionDot.set(null)

    if (!sessionService.get().verifyId(sessionId)) {
      return
    }

    if (!sessionService.get().verifyToken(sessionId, token)) {
      return
    }

    sessionDot.set(sessionService.get().getSessionData(sessionId))
  }


  override fun getSessionHolder(): SessionHolder? {
    return sessionDot.get()
  }

  override fun displayPerson(personId: String): PersonDisplay {
    val ret = authDao.get().loadDisplayPerson(personId) ?: throw NullPointerException("No person with id = $personId")

    ret.cans = authDao.get().loadCans(personId).filter { it != null }

    return ret
  }

  override fun deleteSession(sessionId: String) {
    sessionService.get().removeSession(sessionId)
  }
}
