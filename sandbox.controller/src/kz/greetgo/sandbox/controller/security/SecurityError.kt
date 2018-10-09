///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.controller.security

///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.errors.RestError

class SecurityError @JvmOverloads constructor(message: String = "Security error") : RestError(401, message)
