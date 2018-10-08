///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.util

import javax.sql.DataSource

interface DataSourceGetter {
  val dataSource: DataSource
}
