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
import java.util.EnumSet

@Bean
class CrossOriginFilterRegistration : WebAppContextRegistration, Filter {
  override fun priority(): Double {
    return -90.0
  }

  override fun registerTo(webAppContext: WebAppContext) {
    webAppContext.addFilter(FilterHolder(this), "/*", EnumSet.of(DispatcherType.REQUEST))
    printRegistration()
  }

  override fun init(filterConfig: FilterConfig) {}

  override fun destroy() {}

  override fun doFilter(servletRequest: ServletRequest, servletResponse: ServletResponse, filterChain: FilterChain) {
    if (servletRequest !is HttpServletRequest || servletResponse !is HttpServletResponse) {
      throw ServletException(javaClass.simpleName + " can work only with HTTP protocol")
    }

    //    printRequestInfo(request);

    servletResponse.addHeader("Access-Control-Allow-Credentials", "true")
    servletResponse.addHeader("Access-Control-Allow-Origin", servletRequest.getHeader("Origin"))

    servletResponse.addHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE")
    servletResponse.addHeader("Access-Control-Allow-Headers", "origin,x-requested-with,access-control-request-headers,"
      + "content-type,access-control-request-method,accept,token,set-cookie")
    servletResponse.addHeader("Access-Control-Max-Age", "1800")

    if ("OPTIONS" == servletRequest.method) {
      servletResponse.status = 200
      return
    }

    filterChain.doFilter(servletRequest, servletResponse)
  }

  @Suppress("unused")
  private fun printRequestInfo(request: HttpServletRequest) {
    println("request.getRequestURL() = " + request.requestURL)
    println("origin = " + extractOrigin(request.requestURL))
    println("request.getServerName() = " + request.serverName)
    println("request.getServerPort() = " + request.serverPort)
    println("request.getProtocol() = " + request.protocol)
    println("request.getRemoteAddr() = " + request.remoteAddr)
    println("request.getRemoteHost() = " + request.remoteHost)
    println("request.getRemotePort() = " + request.remotePort)
    println("request.getRemoteUser() = " + request.remoteUser)

    val headerNames = request.headerNames
    while (headerNames.hasMoreElements()) {
      val headName = headerNames.nextElement()
      val headValue = request.getHeader(headName)
      println("headName = $headName, headValue = $headValue")
    }
  }

  private fun extractOrigin(url: CharSequence): String {
    val s = url.toString()
    val i1 = s.indexOf("://")
    if (i1 < 0) throw IllegalArgumentException("It is not URL: $url")
    val i2 = s.indexOf('/', i1 + 3)
    return if (i2 < 0) s else s.substring(0, i2)
  }
}
