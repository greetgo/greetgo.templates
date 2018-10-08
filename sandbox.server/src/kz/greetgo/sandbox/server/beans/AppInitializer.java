///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.server.beans;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.util.App;
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.util.LiquibaseManager;

import javax.servlet.ServletContext;

@Bean
public class AppInitializer {

  public BeanGetter<LiquibaseManager> liquibaseManager;

  public BeanGetter<ControllerServlet> controllerServlet;

  public BeanGetter<Utf8AndTraceResetFilter> utf8AndTraceResetFilter;

  public void initialize(ServletContext ctx) throws Exception {
    if (!App.INSTANCE.do_not_run_liquibase_on_deploy_war().exists()) {
      liquibaseManager.get().apply();
    }

    utf8AndTraceResetFilter.get().register(ctx);

    controllerServlet.get().register(ctx);
  }
}
