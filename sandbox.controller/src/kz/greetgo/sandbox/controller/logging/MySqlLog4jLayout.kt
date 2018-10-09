///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.controller.logging

import org.apache.log4j.spi.LoggingEvent

import java.text.SimpleDateFormat
import java.util.Date

class MySqlLog4jLayout : org.apache.log4j.Layout() {

  private val buffer = StringBuilder(255)
  private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")

  override fun format(event: LoggingEvent): String {
    val buffer = this.buffer

    buffer.setLength(0)
    buffer.append(dateFormat.format(Date(event.getTimeStamp()))).append(' ')
    LogIdentity.appendLogIdentity(buffer)

    run {
      buffer.append(" [")
      val loggerName = event.loggerName
      val index1 = loggerName.lastIndexOf('.')
      if (0 > index1) {
        buffer.append(loggerName)
      } else {
        val index2 = loggerName.lastIndexOf('.', index1 - 1)
        if (index2 < 0) {
          buffer.append(loggerName)
        } else {
          buffer.append(loggerName, index2 + 1, loggerName.length)
        }
      }
      buffer.append(']')
    }

    buffer.append(" SQL ").append(event.message)

    return buffer.append('\n').toString()
  }

  override fun ignoresThrowable(): Boolean {
    return true
  }

  override fun activateOptions() {}
}
