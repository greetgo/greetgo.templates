///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.server.beans

import kz.greetgo.depinject.core.Bean
import kz.greetgo.depinject.core.BeanGetter
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.util.App
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.util.LiquibaseManager

import javax.servlet.ServletContext

@Bean
class AppInitializer {

  lateinit var liquibaseManager: BeanGetter<LiquibaseManager>

  lateinit var controllerServlet: BeanGetter<ControllerServlet>

  lateinit var utf8AndTraceResetFilter: BeanGetter<Utf8AndTraceResetFilter>

  fun initialize(ctx: ServletContext) {
    if (!App.do_not_run_liquibase_on_deploy_war().exists()) {
      liquibaseManager.get().apply()
    }

    utf8AndTraceResetFilter.get().register(ctx)

    controllerServlet.get().register(ctx)
  }
}
