///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.debug.beans

import kz.greetgo.depinject.core.Bean
import kz.greetgo.depinject.core.BeanGetter
import kz.greetgo.depinject.core.HasAfterInject
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.util.Modules
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.debug.util.WebAppContextRegistration
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.webapp.WebAppContext
import java.util.*

@Bean
class DebugServer : HasAfterInject {
  companion object {
    private const val PORT = 1313/* Reptilians is out of there */
  }

  val server = Server(PORT)

  lateinit var webAppContextRegistrations: BeanGetter<List<WebAppContextRegistration>>

  @Throws(Exception::class)
  fun start(): DebugServer {
    server.start()
///MODIFY replace sandbox {PROJECT_NAME}
    val url = "Go to http://localhost:$PORT/sandbox/api/auth/probe"
    System.err.println("[[[                                ]]]")
    System.err.println("[[[ Stand server has been launched ]]] [[[ $url ]]]")
    System.err.println("[[[                                ]]]")
    return this
  }

  @Throws(InterruptedException::class)
  fun join() {
    server.join()
  }

  @Throws(Exception::class)
  override fun afterInject() {
    val webAppServlet = WebAppContext(
      Modules.clientDir().toPath().resolve(".").toString(),
///MODIFY replace sandbox {PROJECT_NAME}
      "/sandbox")

    webAppContextRegistrations.get().stream()
      .sorted(Comparator.comparingDouble { it.priority() })
      .forEachOrdered { it.registerTo(webAppServlet) }

    server.handler = webAppServlet
  }

}
