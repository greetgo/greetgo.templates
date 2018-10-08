///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.server.beans

import kz.greetgo.depinject.core.Bean
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.logging.LogIdentity

import javax.servlet.DispatcherType
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.FilterConfig
import javax.servlet.ServletContext
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import java.io.IOException
import java.util.EnumSet

@Bean
class Utf8AndTraceResetFilter : Filter {
  fun register(ctx: ServletContext) {
    val dynamic = ctx.addFilter(javaClass.name, this)
    dynamic.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*")
  }

  @Throws(IOException::class, ServletException::class)
  override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {

    LogIdentity.resetThread()

    request.characterEncoding = "UTF-8"
    response.characterEncoding = "UTF-8"
    chain.doFilter(request, response)

  }

  @Throws(ServletException::class)
  override fun init(filterConfig: FilterConfig) {
  }

  override fun destroy() {}
}
