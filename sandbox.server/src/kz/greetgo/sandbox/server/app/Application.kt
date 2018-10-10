///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.server.app

import kz.greetgo.depinject.Depinject

import javax.servlet.ServletContainerInitializer
import javax.servlet.ServletContext
import javax.servlet.ServletException

class Application : ServletContainerInitializer {

  @Throws(ServletException::class)
  override fun onStartup(c: Set<Class<*>>?, servletContext: ServletContext) {

    val beanContainer = Depinject.newInstance(ApplicationBeanContainer::class.java)

    val appInitializer = beanContainer.appInitializer()

    appInitializer.initialize(servletContext)

  }
}
