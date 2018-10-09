///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.controller.register

///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.model.PersonDisplay
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.model.SessionHolder
import kz.greetgo.security.session.SessionIdentity

interface AuthRegister {

  fun getSessionHolder(): SessionHolder?

  fun login(username: String, password: String?): SessionIdentity

  fun resetThreadLocalAndVerifySession(sessionId: String?, token: String?)

  fun displayPerson(personId: String): PersonDisplay

  fun deleteSession(sessionId: String)
}
