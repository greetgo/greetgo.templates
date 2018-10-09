///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.controller.controller

import kz.greetgo.depinject.core.Bean
import kz.greetgo.depinject.core.BeanGetter
import kz.greetgo.mvc.annotations.AsIs
import kz.greetgo.mvc.annotations.Par
import kz.greetgo.mvc.annotations.ParSession
import kz.greetgo.mvc.annotations.ToJson
import kz.greetgo.mvc.annotations.on_methods.ControllerPrefix
import kz.greetgo.mvc.annotations.on_methods.OnGet
import kz.greetgo.mvc.annotations.on_methods.OnPost
import kz.greetgo.mvc.interfaces.TunnelCookies
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.model.PersonDisplay
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.register.AuthRegister
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.security.PublicAccess
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.util.Controller

/**
 * как составлять контроллеры написано
 * [здесь](https://github.com/greetgo/greetgo.mvc/blob/master/doc/controller_spec.md)
 */
@Bean
@ControllerPrefix("/auth")
class AuthController : Controller {

  lateinit var authRegister: BeanGetter<AuthRegister>

  @AsIs
  @PublicAccess
  @OnGet("/probe")
  fun probe(): String {
    return "System is working <b>OK</b>"
  }

  @AsIs
  @PublicAccess
  @OnPost("/login")
  fun login(@Par("username") username: String,
            @Par("password") password: String,
            cookies: TunnelCookies): String {

    val identity = authRegister.get().login(username, password)

    cookies.forName("g-session")
      .path("/")
      .httpOnly(true)
      .maxAge(-1)
      .saveValue(identity.id)

    return identity.token
  }

  @ToJson
  @OnGet("/displayPerson")
  fun displayPerson(@ParSession("personId") personId: String): PersonDisplay {
    return authRegister.get().displayPerson(personId)
  }

  @AsIs
  @PublicAccess
  @OnGet("/exit")
  fun exit(@ParSession("sessionId") sessionId: String, cookies: TunnelCookies) {
    authRegister.get().deleteSession(sessionId)

    cookies.forName("g-session")
      .path("/")
      .remove()
  }
}
