///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.beans.all

import kz.greetgo.depinject.core.Bean
import kz.greetgo.security.password.PasswordEncoder

import kz.greetgo.security.SecurityBuilders.newPasswordEncoderBuilder

@Bean
class PasswordEncoderFactory {
  @Bean
  fun createPasswordEncoder(): PasswordEncoder {
///MODIFY replace h2h3j4j5hn6b6h5j43k3 {RND_SALT}
    return newPasswordEncoderBuilder().setSalt("h2h3j4j5hn6b6h5j43k3").build()
  }
}
