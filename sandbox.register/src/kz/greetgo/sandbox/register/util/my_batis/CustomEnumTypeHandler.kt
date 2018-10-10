///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.util.my_batis

import org.apache.ibatis.type.BaseTypeHandler
import org.apache.ibatis.type.JdbcType

import java.sql.CallableStatement
import java.sql.PreparedStatement
import java.sql.ResultSet

class CustomEnumTypeHandler<E : Enum<E>>(private val type: Class<E>?) : BaseTypeHandler<E>() {

  init {
    if (type == null) {
      throw IllegalArgumentException("Type argument cannot be null")
    }
  }

  override fun setNonNullParameter(ps: PreparedStatement, i: Int, parameter: E, jdbcType: JdbcType?) {
    if (jdbcType == null) {
      ps.setString(i, parameter.name)
    } else {
      ps.setObject(i, parameter.name, jdbcType.TYPE_CODE) // see r3589
    }
  }

  override fun getNullableResult(rs: ResultSet, columnName: String): E? {
    return valueOfOrNull(rs.getString(columnName))
  }

  override fun getNullableResult(rs: ResultSet, columnIndex: Int): E? {
    return valueOfOrNull(rs.getString(columnIndex))
  }

  override fun getNullableResult(cs: CallableStatement, columnIndex: Int): E? {
    return valueOfOrNull(cs.getString(columnIndex))
  }

  private fun valueOfOrNull(str: String?): E? {

    if (str == null) {
      return null
    }

    try {
      return java.lang.Enum.valueOf(type, str)
    } catch (e: IllegalArgumentException) {
      return null
    }

  }
}