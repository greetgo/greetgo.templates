///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.beans.all

import org.testng.annotations.Test

import org.fest.assertions.api.Assertions.assertThat

class IdGeneratorTest {
  @Test
  fun newId() {
    val idGenerator = IdGenerator()

    val id1 = idGenerator.newId()
    val id2 = idGenerator.newId()

    assertThat(id1).isNotNull
    assertThat(id2).isNotNull
    assertThat(id1).isNotEqualTo(id2)

    println("id1 = $id1")
    println("id2 = $id2")
  }
}
