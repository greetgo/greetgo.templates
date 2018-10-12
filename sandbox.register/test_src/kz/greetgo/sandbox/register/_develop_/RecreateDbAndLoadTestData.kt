///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register._develop_

///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.test.util.TestsBeanContainerCreator

/**
 * see --> Инициация приложения на рабочем месте разработчика c загрйзкой в БД тестовых данных из стэнда
 *
 * Этот скрипт запускается для иницииации приложения: здесь автоматически настраиваются конфиги и инициируется БД
 *
 * Также этот скрипт загружает в БД тестовые данные из стэнда
 */
object RecreateDbAndLoadTestData {
  @JvmStatic
  fun main(args: Array<String>) {
    val bc = TestsBeanContainerCreator.create()

    bc.dbWorker().recreateAll()
    bc.dbLoader().loadTestData()
  }
}
