///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.controller.errors

/**
 *
 *
 * Генерируется при обработке запроса по REST.
 *
 *
 *
 * Created by pompei on 2017-06-02.
 *
 */
open class RestError
@JvmOverloads
constructor(val statusCode: Int = 500, message: String? = null) : RuntimeException(message) {

  constructor(message: String?) : this(500, message)

}
