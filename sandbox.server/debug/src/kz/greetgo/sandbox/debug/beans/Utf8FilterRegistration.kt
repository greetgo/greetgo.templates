///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.debug.beans

import kz.greetgo.depinject.core.Bean
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.debug.util.WebAppContextRegistration
import org.eclipse.jetty.servlet.FilterHolder
import org.eclipse.jetty.webapp.WebAppContext

import javax.servlet.DispatcherType
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import java.io.IOException
import java.util.EnumSet

@Bean
class Utf8FilterRegistration : WebAppContextRegistration, Filter {
  override fun registerTo(webAppContext: WebAppContext) {
    webAppContext.addFilter(FilterHolder(this), "/*", EnumSet.of(DispatcherType.REQUEST))
    printRegistration()
  }

  override fun priority(): Double {
    return -200.0
  }

  @Throws(ServletException::class)
  override fun init(filterConfig: FilterConfig) {
  }

  @Throws(IOException::class, ServletException::class)
  override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {

    request.characterEncoding = "UTF-8"
    response.characterEncoding = "UTF-8"
    chain.doFilter(request, response)

  }

  override fun destroy() {}
}
