///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.test.dao.postgres;

import kz.greetgo.depinject.core.BeanConfig;
import kz.greetgo.depinject.core.BeanScanner;
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.beans.all.DaoImplFactory;

@BeanScanner
@BeanConfig(factory = DaoImplFactory.class)
public class BeanConfigTestDao {}
