///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.controller.controller;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.mvc.annotations.AsIs;
import kz.greetgo.mvc.annotations.Par;
import kz.greetgo.mvc.annotations.ParSession;
import kz.greetgo.mvc.annotations.ToJson;
import kz.greetgo.mvc.annotations.on_methods.ControllerPrefix;
import kz.greetgo.mvc.annotations.on_methods.OnGet;
import kz.greetgo.mvc.annotations.on_methods.OnPost;
import kz.greetgo.mvc.interfaces.TunnelCookies;
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.model.PersonDisplay;
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.register.AuthRegister;
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.security.PublicAccess;
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.util.Controller;
import kz.greetgo.security.session.SessionIdentity;

/**
 * как составлять контроллеры написано
 * <a href="https://github.com/greetgo/greetgo.mvc/blob/master/doc/controller_spec.md">здесь</a>
 */
@Bean
@ControllerPrefix("/auth")
public class AuthController implements Controller {

  public BeanGetter<AuthRegister> authRegister;

  @AsIs
  @PublicAccess
  @OnGet("/probe")
  public String probe() {
    return "System is working <b>OK</b>";
  }

  @AsIs
  @PublicAccess
  @OnPost("/login")
  public String login(@Par("username") String username,
                      @Par("password") String password,
                      TunnelCookies cookies) {

    SessionIdentity identity = authRegister.get().login(username, password);

    cookies.forName("g-session")
      .path("/")
      .httpOnly(true)
      .maxAge(-1)
      .saveValue(identity.id);

    return identity.token;
  }

  @ToJson
  @OnGet("/displayPerson")
  public PersonDisplay displayPerson(@ParSession("personId") String personId) {
    return authRegister.get().displayPerson(personId);
  }

  @AsIs
  @PublicAccess
  @OnGet("/exit")
  public void exit(@ParSession("sessionId") String sessionId, TunnelCookies cookies) {
    authRegister.get().deleteSession(sessionId);

    cookies.forName("g-session")
      .path("/")
      .remove();
  }
}
