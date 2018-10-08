///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.beans.all

import kz.greetgo.depinject.core.Bean

import java.security.SecureRandom
import java.util.Random

@Bean
class IdGenerator {

  private val random = SecureRandom()

  fun newId(): String {

    val indexes = random
      .ints(ALL_LENGTH.toLong())
      .map { i -> if (i < 0) -i else i }
      .map { i -> i % ALL_LENGTH }
      .toArray()

    val id = CharArray(ID_LEN)
    for (i in 0 until ID_LEN) {
      id[i] = ALL[indexes[i]]
    }

    return String(id)
  }

  companion object {

    private val ENG = "abcdefghijklmnopqrstuvwxyz"
    private val DEG = "0123456789"
    private val ALL = (ENG.toLowerCase() + ENG.toUpperCase() + DEG).toCharArray()
    private val ALL_LENGTH = ALL.size

    private val ID_LEN = 17
  }
}
