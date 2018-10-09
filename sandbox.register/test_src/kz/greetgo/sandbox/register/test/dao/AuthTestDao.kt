///MODIFY replace sandbox {PROJECT_NAME}
package kz.greetgo.sandbox.register.test.dao

import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Param
import org.apache.ibatis.annotations.Update

interface AuthTestDao {
  @Insert("insert into Person (id, username, encoded_password, blocked) "
    + "values (#{id}, #{username}, #{encodedPassword}, 0)")
  fun insertPerson(@Param("id") id: String,
                   @Param("username") username: String,
                   @Param("encodedPassword") encodedPassword: String
  )

  @Update("update Person set \${fieldName} = #{fieldValue} where id = #{id}")
  fun updatePersonField(@Param("id") id: String,
                        @Param("fieldName") fieldName: String,
                        @Param("fieldValue") fieldValue: Any)

  @Insert("insert into user_can (user_can, description) values (#{can}, 'description of '||#{can})"
    + " on conflict (user_can) do nothing")
  fun upsert(@Param("can") can: String)

  @Insert("insert into person_cans (person_id, user_can)" +
    " select p.id as person_id, #{can} as user_can" +
    " from person p, user_can where p.username = #{username} and p.blocked = 0" +
    " on conflict (person_id, user_can) do nothing")
  fun personCan(@Param("username") username: String, @Param("can") can: String)
}
