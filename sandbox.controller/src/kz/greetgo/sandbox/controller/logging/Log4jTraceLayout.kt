///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.controller.logging

import org.apache.log4j.Layout
import org.apache.log4j.spi.LoggingEvent

import java.text.SimpleDateFormat
import java.util.Date

class Log4jTraceLayout : Layout() {

  private val buf = StringBuilder(255)

  override fun format(loggingEvent: LoggingEvent): String {
    val buf = this.buf
    buf.setLength(0)

    val dat = timeStampLayout(loggingEvent.timeStamp)
    buf.append(dat).append(' ')
    LogIdentity.appendLogIdentity(buf)

    buf.append(' ')

    run {
      var loggerName: String? = loggingEvent.loggerName
      if (loggerName != null) {
        val idx = loggerName.lastIndexOf('.')
        if (idx < 0 || idx == loggerName.length - 1) {
          loggerName = null
        } else {
          loggerName = loggerName.substring(idx + 1)
        }
        if (loggerName != null) {
          buf.append('[').append(loggerName).append("] ")
        }
      }
    }

    buf.append(loggingEvent.message)

    return buf.append('\n').toString()
  }

  fun timeStampLayout(timeStamp: Long): String {
    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(Date(timeStamp))
  }

  override fun ignoresThrowable(): Boolean {
    return true
  }

  override fun activateOptions() {}
}
