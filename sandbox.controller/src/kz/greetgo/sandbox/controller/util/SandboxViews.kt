///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.controller.util

import com.fasterxml.jackson.databind.ObjectMapper
import kz.greetgo.depinject.core.BeanGetter
import kz.greetgo.mvc.annotations.ParSession
import kz.greetgo.mvc.annotations.ToJson
import kz.greetgo.mvc.annotations.ToXml
import kz.greetgo.mvc.interfaces.MethodInvokedResult
import kz.greetgo.mvc.interfaces.MethodInvoker
import kz.greetgo.mvc.interfaces.RequestTunnel
import kz.greetgo.mvc.interfaces.SessionParameterGetter
import kz.greetgo.mvc.interfaces.Views
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.errors.JsonRestError
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.errors.RestError
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.register.AuthRegister
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.security.PublicAccess
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.security.SecurityError
import org.apache.log4j.Logger

import java.lang.reflect.Method

/**
 * В этом классе реализована обработка методов контроллеров
 */
///MODIFY replace Sandbox {PROJECT_CC_NAME}
abstract class SandboxViews : Views {
  private val objectMapper = ObjectMapper()

  lateinit var authRegister: BeanGetter<AuthRegister>

  private val logger = Logger.getLogger(SandboxViews::class.qualifiedName)

  /**
   * Этот метод вызывается после вызова метода контроллера помеченного аннотацией [ToJson].
   *
   * @param object сюда подаётся объект, который вернул метод контроллера
   * @param tunnel это тунель запроса (через него можно управлять процессом запроса)
   * @param method ссылка на метод контроллера, который был только-что вызван
   * @return эта строка будет отправлена в качестве тела ответа на запрос, зашифрованной в кодировке UTF-8.
   * @throws Exception нужно чтобы не ставить надоедливые try/catch-блоки
   */
  @Throws(Exception::class)
  override fun toJson(`object`: Any?, tunnel: RequestTunnel?, method: Method?): String? {
    return convertToJson(`object`)
  }

  @Throws(Exception::class)
  private fun convertToJson(`object`: Any?): String? {
    return if (`object` == null) null else objectMapper.writer().writeValueAsString(`object`)
  }

  /**
   * Этот метод вызывается после вызова метода контроллера помеченного аннотацией [ToXml].
   *
   * @param object сюда подаётся объект, который вернул метод контроллера
   * @param tunnel это тунель запроса (через него можно управлять процессом запроса)
   * @param method ссылка на метод контроллера, который был только-что вызван
   * @return эта строка будет отправлена в качестве тела ответа на запрос, зашифрованной в кодировке UTF-8.
   * @throws Exception нужно чтобы не ставить надоедливые try/catch-блоки
   */
  @Throws(Exception::class)
  override fun toXml(`object`: Any?, tunnel: RequestTunnel?, method: Method?): String {
    //Здесь нужно object преобразовать в XML и вернуть
    //Здесь аннотация ToXml не работает
    throw UnsupportedOperationException()
  }


  /**
   * Этот метод вызывается, каждый раз при обработке запроса. Метод контроллера ещё не вызван, и его нужно вызвать
   * из этого метода. А можно и не вызвать, например потому-что нет прав или ещё по какой причине.
   *
   * @param methodInvoker исполнитель метода контроллера - вспомогательный объект, в котором подготовлено всё
   * необходимое для вызова метода контроллера,
   * и для изучения вызываемого метода контроллера. Например можно посмотреть какие аннотации есть
   * у метода и провести дополнительные манипуляции.
   * @throws Exception нужно чтобы не ставить надоедливые try/catch-блоки
   */
  @Throws(Exception::class)
  override fun performRequest(methodInvoker: MethodInvoker) {

    //вызываем этот метод, чтобы в дальнейшем можно было получить момент непосредственно перед вызовом метода контроллера
    beforeRequest()

    //подготавливаем сессию. Здесь может произойти ошибка, например повреждён токен. И тогда метод вызва не будет
    prepareSession(methodInvoker)

    //вызываем этот метод, чтобы в дальнейшем можно было получить момент непосредственно перед вызовом метода контроллера
    beforeRequestWithSession()

    //вызываем метод контроллера и получаем результат вызова метода
    val invokedResult = methodInvoker.invoke()

    //пытаемся зарендерить результат поведением по-умолчанию. Таким поведением являются аннотации: ToJson, ToXml, AsIs
    if (invokedResult.tryDefaultRender()) {
      //поведение по-умолчанию получилось применить. Это значит что запрос полностью обработан и
      //больше ничего делать не нужно - выходим
      return
    }

    //здесь нужно обработать специфичный результат работы метода контроллера, например прорендерить JSP или оттобразить
    //ошибку, или ещё что-то

    //смотрим была ли ошибка в метода
    if (invokedResult.error() != null) {
      //обрабатываем ошибку
      performError(methodInvoker, invokedResult)
    } else {
      //обрабатываем нормальное поведение
      performRender(methodInvoker, invokedResult)
    }
  }

  /**
   * Этот метод вызывается всегда перед вызовом метода контроллера, но уже после проверки безопасности.
   * Если проверка безопасности не прошла, то этот метод не вызывается
   *
   * @throws Exception нужно чтобы не ставить надоедливые try/catch-блоки
   */
  @Throws(Exception::class)
  private fun beforeRequestWithSession() {
  }

  /**
   * Этот метод вызывается всегда перед вызовом метода контроллера
   *
   * @throws Exception нужно чтобы не ставить надоедливые try/catch-блоки
   */
  @Throws(Exception::class)
  protected open fun beforeRequest() {
  }

  /**
   * Осуществляет подготовку сессии и сохранения её в LocalThread-переменной.
   *
   * @param methodInvoker исполнитель метода контроллера
   */
  private fun prepareSession(methodInvoker: MethodInvoker) {
    //Достаём токен из заголовка запроса. Если токена нет, то получим null
    val token = methodInvoker.tunnel().requestHeaders().value("token")

    //Берём идентификатор сессии из кукисов. Если сессии нет, то получаем null
    val sessionId = methodInvoker.tunnel().cookies().name("g-session").value()

    //Проверяем параметры сессии на достоверность, и если всё ок, сохраняем в ThreadLocal-переменной сессию
    //Иначе очищаем ThreadLocal-переменную
    authRegister.get().resetThreadLocalAndVerifySession(sessionId, token)

    if (

      methodInvoker.getMethodAnnotation<PublicAccess>(PublicAccess::class.java) == null

      && authRegister.get().session == null) {

      throw SecurityError()

    }
  }

  /**
   * Этот метод вызывается, когда необходимо заполнить параметр метода контроллера
   * помеченный аннотацией [ParSession]
   *
   * @param context информация о параметре: что за параметр, его тип и пр.
   * @param tunnel  тунель запроса - дан для того, чтобы можно было получить какие-нибудь данные для параметра
   * @return значение этого параметра: оно будет подставлено в этот параметр
   */
  override fun getSessionParameter(context: SessionParameterGetter.ParameterContext?, tunnel: RequestTunnel?): Any? {
    if ("personId" == context!!.parameterName()) {
      if (context.expectedReturnType() !== String::class.java) throw SecurityError("personId must be a string")

      val sessionHolder = authRegister.get().session
      return sessionHolder?.personId
    }

    if ("mode" == context.parameterName()) {
      if (context.expectedReturnType() !== String::class.java) throw SecurityError("personId must be a string")

      val sessionHolder = authRegister.get().session
      return sessionHolder?.mode
    }

    if ("sessionId" == context.parameterName()) {
      if (context.expectedReturnType() !== String::class.java) throw SecurityError("personId must be a string")
      return tunnel!!.cookies().name("g-session").value()
    }

    throw SecurityError("Unknown session parameter " + context.parameterName())
  }

  /**
   * Здесь происходиь рендеринг вьюшки запроса
   *
   * @param methodInvoker исполнитель метода контроллера
   * @param invokedResult результаты вызова метода контроллера
   */
  private fun performRender(methodInvoker: MethodInvoker, invokedResult: MethodInvokedResult) {
    assert(invokedResult.error() == null)
    //возвращённое методом контроллера значение
    val returnedValue = invokedResult.returnedValue() ?: return

    //обрабатываем только строки. Не понятно как обрабатывать другие типы.
    if (returnedValue !is String) {
      throw IllegalArgumentException("Cannot view " + returnedValue.javaClass
        + " with value " + returnedValue)
    }

    //предполагаем, что возвращённое значение - это локальный путь к jsp-файлу, например: jsp/hello.jsp

    val tunnel = methodInvoker.tunnel()

    //заполняем данные для вьюшки, которые будут доступны через $ например $hello - в методе их добавляли в MvcModel
    for ((key, value) in methodInvoker.model().data) {
      tunnel.requestAttributes().set(key, value)
    }

    //форвардим на рендеринг jsp-файла
    tunnel.forward(returnedValue, true)
  }

  /**
   * Обрабатывается ошибка запроса
   *
   * @param methodInvoker информация о методе контроллера для обработки запроса
   * @param invokedResult результаты вызова метода контроллера
   * @throws Exception нужно чтобы не ставить надоедливые try/catch-блоки
   */
  @Throws(Exception::class)
  private fun performError(methodInvoker: MethodInvoker, invokedResult: MethodInvokedResult) {
    val error = invokedResult.error()!!

    logger.error(error.message, error)

    val tunnel = methodInvoker.tunnel()
    tunnel.requestAttributes().set("ERROR_TYPE", error.javaClass.simpleName)

    if (error is JsonRestError) {
      tunnel.setResponseStatus(error.statusCode)
      val json = convertToJson(error.sendingAsJsonObject)
      if (json != null)
        tunnel.responseWriter.use { writer -> writer.print(json) }
      return
    }

    if (error is RestError) {
      tunnel.setResponseStatus(error.statusCode)

      if (error.message != null) {
        tunnel.responseWriter.use { writer -> writer.print(error.message) }
      }

      return
    }

    run {
      tunnel.setResponseStatus(500)
      tunnel.responseWriter.use { writer ->
        writer.println("Internal server error: " + error.message)
        writer.println()
        error.printStackTrace(writer)
      }

      error.printStackTrace()
    }

    return
  }
}
