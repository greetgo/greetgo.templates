///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.util

import kz.greetgo.db.AbstractJdbcWithDataSource
import kz.greetgo.db.TransactionManager

import javax.sql.DataSource

///MODIFY replace Sandbox {PROJECT_CC_NAME}
class JdbcSandbox(private val dataSource: DataSource,
                  private val transactionManager: TransactionManager) : AbstractJdbcWithDataSource() {

  override fun getDataSource(): DataSource {
    return dataSource
  }

  override fun getTransactionManager(): TransactionManager {
    return transactionManager
  }
}
