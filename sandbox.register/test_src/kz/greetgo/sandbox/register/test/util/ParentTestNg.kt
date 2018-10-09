///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.test.util

import kz.greetgo.depinject.testng.AbstractDepinjectTestNg
import kz.greetgo.depinject.testng.ContainerConfig

@ContainerConfig(BeanConfigTests::class)
abstract class ParentTestNg : AbstractDepinjectTestNg()
