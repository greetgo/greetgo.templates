///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.impl

import kz.greetgo.depinject.core.Bean
import kz.greetgo.depinject.core.BeanGetter
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.model.PersonRecord
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.register.PersonRegister
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.dao.PersonDao

@Bean
class PersonRegisterImpl : PersonRegister {
  lateinit var personDao: BeanGetter<PersonDao>

  override fun list(): List<PersonRecord> {
    return personDao.get().list()
  }
}
