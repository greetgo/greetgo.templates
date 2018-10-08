///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.util

import kz.greetgo.db.TransactionManager
import org.apache.ibatis.session.TransactionIsolationLevel
import org.apache.ibatis.transaction.Transaction
import org.apache.ibatis.transaction.TransactionFactory
import java.sql.Connection
import java.sql.SQLException
import java.util.*
import javax.sql.DataSource

class LocalTransactionFactory(private val transactionManager: TransactionManager) : TransactionFactory {

  override fun setProperties(props: Properties) {}

  override fun newTransaction(conn: Connection): Transaction {
    throw UnsupportedOperationException()
  }

  override fun newTransaction(dataSource: DataSource,
                              level: TransactionIsolationLevel,
                              autoCommit: Boolean): Transaction {

    return object : Transaction {
      @Suppress("RedundantVisibilityModifier")
      internal var connection: Connection? = null

      @Throws(SQLException::class)
      override fun getConnection(): Connection? {
        if (connection == null) {
          connection = transactionManager.getConnection(dataSource)
        }
        return connection
      }

      @Throws(SQLException::class)
      override fun commit() {
        throw UnsupportedOperationException()
      }

      @Throws(SQLException::class)
      override fun rollback() {
        throw UnsupportedOperationException()
      }

      @Throws(SQLException::class)
      override fun close() {
        transactionManager.closeConnection(dataSource, connection)
      }

      @Throws(SQLException::class)
      override fun getTimeout(): Int? {
        return null
      }
    }
  }
}
