///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.controller.security

import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*

/**
 * Показывает, что указанный метод не нуждается в защите секурити (ему не нужна сессия)
 * Created by pompei on 05.06.17.
 */
@MustBeDocumented
@Target(CLASS, FILE, FUNCTION, PROPERTY_GETTER, PROPERTY_SETTER)
@Retention(RUNTIME)
annotation class PublicAccess
