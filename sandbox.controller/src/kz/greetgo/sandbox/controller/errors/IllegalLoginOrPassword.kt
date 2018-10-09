///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.controller.errors

class IllegalLoginOrPassword : RestError(401, "Не верен пользователь и/или пароль")
