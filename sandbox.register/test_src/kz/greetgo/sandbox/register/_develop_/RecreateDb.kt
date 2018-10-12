///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register._develop_

///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.test.util.TestsBeanContainerCreator

/**
 * see --> Инициация приложения на рабочем месте разработчика
 *
 * Этот скрипт запускается для иницииации приложения: здесь автоматически настраиваются конфиги и инициируется БД
 */
object RecreateDb {
  @JvmStatic
  fun main(args: Array<String>) {
    val bc = TestsBeanContainerCreator.create()

    bc.dbWorker().recreateAll()
  }
}
