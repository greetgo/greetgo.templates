///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.debug.beans

import kz.greetgo.depinject.core.Bean
import kz.greetgo.depinject.core.BeanGetter
import kz.greetgo.mvc.JettyWarServlet
import kz.greetgo.mvc.interfaces.Views
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.util.Controller
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.debug.util.WebAppContextRegistration

import java.util.ArrayList

@Bean
class JettyControllersRegistration : JettyWarServlet(), WebAppContextRegistration {

  lateinit var controllerList: BeanGetter<List<Controller>>

  lateinit var views: BeanGetter<Views>

  override fun getControllerList(): List<Any> {
    return ArrayList<Any>(controllerList.get())
  }

  override fun getViews(): Views {
    return views.get()
  }

  override fun afterRegistered() {
    System.err.println("[WebAppContext] --------------------------------------")
    System.err.println("[WebAppContext] -- USING CONTROLLERS:")
    for (execDefinition in execDefinitionList()) {
      System.err.println("[WebAppContext] --   " + execDefinition.infoStr())
    }
    System.err.println("[WebAppContext] --------------------------------------")
    printRegistration()
  }

  override fun mappingBase(): String {
    return "/api/*"
  }

  override fun getTargetSubContext(): String {
    return "/api"
  }

  override fun priority(): Double {
    return 0.0
  }
}
