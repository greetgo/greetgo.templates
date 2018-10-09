///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.debug.beans

import kz.greetgo.depinject.core.Bean
///MODIFY replace sandbox {PROJECT_NAME}
///MODIFY replace Sandbox {PROJECT_CC_NAME}
import kz.greetgo.sandbox.controller.util.SandboxViews
import kz.greetgo.util.ServerUtil.streamToStr0
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.PrintWriter
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.stream.Collectors.toList

@Bean
///MODIFY replace Sandbox {PROJECT_CC_NAME}
class DebugViews : SandboxViews() {

  //see --> В этом файле можно настраивать скорость работы сервисов (он работает на горячюю)
  private val config = File("build/sleep_in_request.txt")

  private val nextIndex = AtomicInteger(0)

  override fun beforeRequest() {
    sleep()
  }

  private fun sleep() {
    if (config.exists()) {
      readAndSleep()
      return
    }

    run {
      config.parentFile.mkdirs()

      try {
        PrintWriter(config).use { pr ->
          pr.println("0")
          pr.println("#1500")
          pr.println("#0")
          pr.println("#3000")
        }
      } catch (e: FileNotFoundException) {
        throw RuntimeException(e)
      }
    }

    readAndSleep()
  }

  private fun readAndSleep() {

    val times = Arrays.stream(streamToStr0(FileInputStream(config)).split("\n".toRegex())
      .dropLastWhile { it.isEmpty() }
      .toTypedArray())
      .map { s -> s.trim { it <= ' ' } }
      .filter { s -> !s.startsWith("#") }
      .filter { s -> s.isNotEmpty() }
      .map { it.toLong() }
      .collect(toList())

    if (times.size == 0) {
      return
    }

    if (times.size == 1) {
      sleepLong(times[0])
    } else {
      sleepLong(times[nextIndex.getAndIncrement() % times.size])
    }

  }

  private fun sleepLong(timeToSleep: Long) {

    if (timeToSleep <= 0) {
      return
    }

    Thread.sleep(timeToSleep)

  }
}
