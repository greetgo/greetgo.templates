///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.dao

///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.model.PersonDisplay
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.controller.model.UserCan
///MODIFY replace sandbox {PROJECT_NAME}
import kz.greetgo.sandbox.register.model.PersonLogin
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Select

interface AuthDao {
  @Select("select * from person where username = #{username} and blocked = 0")
  fun selectByUsername(@Param("username") username: String): PersonLogin

  @Select("select surname||' '||name||' '||patronymic as fio, username" + " from person where id = #{personId}")
  fun loadDisplayPerson(@Param("personId") personId: String): PersonDisplay

  @Select("select user_can from person_cans where person_id = #{personId}")
  fun loadCans(personId: String): List<UserCan>
}
