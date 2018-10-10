///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.util.my_batis

import org.apache.ibatis.type.BaseTypeHandler
import org.apache.ibatis.type.JdbcType

import java.sql.CallableStatement
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class CustomBooleanTypeHandler : BaseTypeHandler<Boolean>() {

  override fun setNonNullParameter(ps: PreparedStatement, i: Int, parameter: Boolean?, jdbcType: JdbcType) {
    if (parameter == null) {
      ps.setNull(i, jdbcType.TYPE_CODE)
    } else {
      ps.setInt(i, if (parameter) 1 else 0)
    }
  }

  override fun getNullableResult(rs: ResultSet, columnName: String): Boolean? {
    val strVal = rs.getString(columnName)
    return if (strVal == null) {
      null
    } else {
      strVal != "0"
    }
  }

  override fun getNullableResult(rs: ResultSet, columnIndex: Int): Boolean? {
    val strVal = rs.getString(columnIndex)
    return if (strVal == null) null else "0" != strVal
  }

  override fun getNullableResult(cs: CallableStatement, columnIndex: Int): Boolean? {
    val strVal = cs.getString(columnIndex)
    return if (strVal == null) {
      null
    } else {
      strVal != "0"
    }
  }
}
