///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.controller.errors

/**
 * Ошибка запроса по REST которая ещё отправляет объект через JSON.
 */
class JsonRestError : RestError {
  val sendingAsJsonObject: Any?

  constructor(sendingAsJsonObject: Any?)
    : super(sendingAsJsonObject?.toString()) {
    this.sendingAsJsonObject = sendingAsJsonObject
  }

  constructor(statusCode: Int, sendingAsJsonObject: Any?)
    : super(statusCode, sendingAsJsonObject?.toString()) {
    this.sendingAsJsonObject = sendingAsJsonObject
  }
}
