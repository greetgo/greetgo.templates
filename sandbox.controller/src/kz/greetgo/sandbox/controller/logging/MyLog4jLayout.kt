///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.controller.logging

import org.apache.log4j.spi.LoggingEvent

import java.text.SimpleDateFormat
import java.util.Date

class MyLog4jLayout : org.apache.log4j.Layout() {

  private val buffer = StringBuilder(255)
  private val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")

  private var loggerNameCut = 2

  fun setLoggerNameCut(loggerNameCut: Int) {
    this.loggerNameCut = loggerNameCut
  }

  override fun format(event: LoggingEvent): String {
    val buffer = this.buffer
    buffer.setLength(0)

    buffer.append(dateFormat.format(Date(event.getTimeStamp()))).append(' ')
    LogIdentity.appendLogIdentity(buffer)
    run {
      val level = event.getLevel().toString()
      val levelLength = level.length
      when (levelLength) {
        5 -> buffer.append(' ').append(level)
        4 -> buffer.append("  ").append(level)
        else -> buffer.append(' ').append(level)
      }

    }
    appendLoggerName(buffer, event.loggerName, loggerNameCut)
    buffer.append(' ').append(event.message)
    return buffer.append('\n').toString()
  }

  override fun ignoresThrowable(): Boolean {
    return true
  }

  override fun activateOptions() {}

  companion object {

    fun appendLoggerName(buffer: StringBuilder, loggerName: String, loggerNameCut: Int) {
      buffer.append(" [")
      if (loggerNameCut <= 0) {
        buffer.append(loggerName)
      } else if (loggerNameCut == 1) {
        val index1 = loggerName.lastIndexOf('.')
        if (index1 < 0) {
          buffer.append(loggerName)
        } else {
          buffer.append(loggerName, index1 + 1, loggerName.length)
        }
      } else {
        val index1 = loggerName.lastIndexOf('.')
        if (index1 < 0) {
          buffer.append(loggerName)
        } else {
          val index2 = loggerName.lastIndexOf('.', index1 - 1)
          if (index2 < 0) {
            buffer.append(loggerName)
          } else {
            buffer.append(loggerName, index2 + 1, loggerName.length)
          }
        }
      }
      buffer.append(']')
    }
  }
}
