package com.example.demo.daoImpl;


import com.example.demo.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component(value="userMapper")
public interface UserMapper {

    @Insert("insert into user(t_name,t_age) values(#{name},#{age})")
    @Options(useGeneratedKeys=true,keyColumn = "t_id")
    public Long insert(@Param("name") String name,@Param("age")Integer age);

    @Results({
            @Result(property = "name",column = "t_name"),
            @Result(property = "id",column = "t_id"),
            @Result(property = "age",column = "t_age"),
    })
    @Select("select * from user where t_name = #{name}")
    public User getByName(@Param("name") String name);

}
