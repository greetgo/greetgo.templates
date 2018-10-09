///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.debug.util

import org.eclipse.jetty.webapp.WebAppContext

interface WebAppContextRegistration {
  fun registerTo(webAppContext: WebAppContext)

  fun priority(): Double

  fun printRegistration() {
    System.err.println("[WebAppContext] " + javaClass.simpleName + " activated. Priority " + priority())
  }
}
