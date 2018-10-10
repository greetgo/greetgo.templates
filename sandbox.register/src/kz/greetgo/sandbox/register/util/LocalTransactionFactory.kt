///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.util

import kz.greetgo.db.TransactionManager
import org.apache.ibatis.session.TransactionIsolationLevel
import org.apache.ibatis.transaction.Transaction
import org.apache.ibatis.transaction.TransactionFactory
import java.sql.Connection
import java.util.*
import javax.sql.DataSource

class LocalTransactionFactory(private val transactionManager: TransactionManager) : TransactionFactory {

  override fun setProperties(props: Properties) {}

  override fun newTransaction(conn: Connection): Transaction {
    throw UnsupportedOperationException()
  }

  override fun newTransaction(dataSource: DataSource,
                              level: TransactionIsolationLevel?,
                              autoCommit: Boolean): Transaction {

    return object : Transaction {

      var theConnection: Connection? = null

      override fun getConnection(): Connection {
        if (theConnection == null) {
          theConnection = transactionManager.getConnection(dataSource)
        }
        return theConnection ?: throw NullPointerException("__connection__ == null")
      }

      override fun commit() {
        throw UnsupportedOperationException()
      }

      override fun rollback() {
        throw UnsupportedOperationException()
      }

      override fun close() {
        transactionManager.closeConnection(dataSource, connection)
      }

      override fun getTimeout(): Int? {
        return null
      }
    }
  }
}
