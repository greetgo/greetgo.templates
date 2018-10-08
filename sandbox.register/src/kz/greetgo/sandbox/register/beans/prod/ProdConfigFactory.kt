///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.beans.prod

import kz.greetgo.depinject.core.Bean
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.util.LocalConfigFactory

@Bean
class ProdConfigFactory : LocalConfigFactory()
