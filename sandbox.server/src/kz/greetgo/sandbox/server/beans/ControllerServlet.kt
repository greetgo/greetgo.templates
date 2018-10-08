///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.server.beans

import kz.greetgo.depinject.core.Bean
import kz.greetgo.depinject.core.BeanGetter
import kz.greetgo.mvc.builder.ExecDefinition
import kz.greetgo.mvc.interfaces.Views
import kz.greetgo.mvc.model.UploadInfo
import kz.greetgo.mvc.war.AppServlet
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.util.Controller

import java.util.ArrayList

import java.util.Collections.unmodifiableList

@Bean
class ControllerServlet : AppServlet() {
  lateinit var controllerList: BeanGetter<List<Controller>>

  lateinit var views: BeanGetter<Views>

  override fun getControllerList(): List<Any> {
    return unmodifiableList(ArrayList<Any>(controllerList.get()))
  }

  override fun getViews(): Views {
    return views.get()
  }

  override fun getUploadInfo(): UploadInfo {
    val ret = UploadInfo()
    ret.maxFileSize = 50000000
    ret.fileSizeThreshold = 1000
    return ret
  }

  override fun afterRegister() {

    System.err.println("[ControllerServlet] --------------------------------------")
    System.err.println("[ControllerServlet] -- USING CONTROLLERS:")
    for (execDefinition in execDefinitionList()) {
      System.err.println("[ControllerServlet] --   " + execDefinition.infoStr())
    }
    System.err.println("[ControllerServlet] --------------------------------------")

    super.afterRegister()
  }

  override fun getTargetSubContext(): String {
    return "/api"
  }
}
