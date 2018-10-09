///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.util

import kz.greetgo.depinject.core.BeanFactory
import org.apache.ibatis.binding.MapperMethod
import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder
import org.apache.ibatis.reflection.ExceptionUtil
import org.apache.ibatis.session.Configuration
import org.apache.ibatis.session.SqlSession

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.concurrent.ConcurrentHashMap

abstract class AbstractMybatisDaoImplFactory : BeanFactory {

  protected abstract fun getSqlSession(): SqlSession

  protected abstract fun getConfiguration(): Configuration

  private val cache = ConcurrentHashMap<Class<*>, Any>()

  private val methodCache = ConcurrentHashMap<Method, MapperMethod>()

  override fun createBean(beanClass: Class<*>): Any {
    run {
      val ret = cache[beanClass]
      if (ret != null) return ret
    }
    synchronized(this) {
      run {
        val ret = cache[beanClass]
        if (ret != null) return ret
      }
      run {
        val ret = createBean0(beanClass)
        cache[beanClass] = ret
        return ret
      }
    }
  }

  private fun createBean0(beanClass: Class<*>): Any {
    val configuration = getConfiguration()
    configuration.addMapper(beanClass)

    run {
      val parser = MapperAnnotationBuilder(configuration, beanClass)
      parser.parse()
    }

    return Proxy.newProxyInstance(
      javaClass.classLoader,
      arrayOf(beanClass),
      ImplInvocationHandler(beanClass)
    )
  }


  private inner class ImplInvocationHandler(internal val mapperInterface: Class<*>) : InvocationHandler {

    @Throws(Throwable::class)
    override fun invoke(proxy: Any?, method: Method, args: Array<Any>): Any? {

      if (Any::class.java == method.declaringClass) {
        try {
          return method.invoke(this, *args)
        } catch (t: Throwable) {
          throw ExceptionUtil.unwrapThrowable(t)
        }

      }

      val mapperMethod = cachedMapperMethod(method)

      try {

        getSqlSession().use {
          return mapperMethod.execute(it, args)
        }

      } catch (t: Throwable) {
        t.printStackTrace()
        throw ExceptionUtil.unwrapThrowable(t)
      }

    }

    private fun cachedMapperMethod(method: Method): MapperMethod {
      run {
        val mapperMethod = methodCache[method]
        if (mapperMethod != null) return mapperMethod
      }
      synchronized(this) {
        run {
          val mapperMethod = methodCache[method]
          if (mapperMethod != null) return mapperMethod
        }
        run {
          val mapperMethod = MapperMethod(mapperInterface, method, getSqlSession().configuration)
          methodCache[method] = mapperMethod
          return mapperMethod
        }
      }
    }
  }
}
