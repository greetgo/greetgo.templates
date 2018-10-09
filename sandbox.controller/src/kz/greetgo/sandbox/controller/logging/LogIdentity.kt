///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.controller.logging

import java.util.Random
import java.util.concurrent.atomic.AtomicInteger

object LogIdentity {
  val RUN: String

  var machine: String? = null

  private val nextThreadId = AtomicInteger(0)

  private val threadId = object : ThreadLocal<Int>() {
    override fun initialValue(): Int {
      return nextThreadId.incrementAndGet()
    }
  }

  init {
    val letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    val rnd = Random()
    val arr = CharArray(10)
    for (i in arr.indices) {
      arr[i] = letters[rnd.nextInt(letters.length)]
    }
    RUN = String(arr)
  }

  fun resetThread() {
    threadId.set(nextThreadId.incrementAndGet())
  }

  fun appendLogIdentity(buf: StringBuilder) {
    if (machine != null) {
      buf.append("M:").append(machine).append(',')
    }
    buf.append("RUN:").append(RUN)
    run {
      val id = threadId.get()
      buf.append(",THREAD:000000")
      if (id < 10) {
        buf.append(id)
      } else if (id < 100) {
        buf.setLength(buf.length - 1)
        buf.append(id)
      } else if (id < 1000) {
        buf.setLength(buf.length - 2)
        buf.append(id)
      } else if (id < 10000) {
        buf.setLength(buf.length - 3)
        buf.append(id)
      } else if (id < 100000) {
        buf.setLength(buf.length - 4)
        buf.append(id)
      } else if (id < 1000000) {
        buf.setLength(buf.length - 5)
        buf.append(id)
      } else {
        buf.setLength(buf.length - 6)
        buf.append(id)
      }
    }
  }

  fun logIdentity(): String {
    val sb = StringBuilder()
    appendLogIdentity(sb)
    return sb.toString()
  }
}
