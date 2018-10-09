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
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.IOException
import java.util.EnumSet

@Bean
class PrintRequestParamsRegistration : WebAppContextRegistration, Filter {
  override fun registerTo(webAppContext: WebAppContext) {
    webAppContext.addFilter(FilterHolder(this), "/*", EnumSet.of(DispatcherType.REQUEST))
    printRegistration()
  }

  override fun priority(): Double {
    return -190.0
  }

  @Throws(ServletException::class)
  override fun init(filterConfig: FilterConfig) {
  }

  override fun destroy() {}


  @Throws(IOException::class, ServletException::class)
  override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {
    if (servletRequest !is HttpServletRequest || servletResponse !is HttpServletResponse) {
      throw ServletException(javaClass.simpleName + " can work only with HTTP protocol")
    }

    run {
      val out = StringBuilder(servletRequest.method)
      while (out.length < 10) out.append(' ')
      out.append(' ')
      out.append(servletRequest.requestURI)
      println(out)
    }

    filterChain.doFilter(servletRequest, servletResponse)
  }
}
