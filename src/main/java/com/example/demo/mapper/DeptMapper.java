package com.example.demo.mapper;

import com.example.demo.model.Dept;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.springframework.stereotype.Component;

@Mapper
@Component("deptMapper")
public interface DeptMapper {

    @Insert("insert into t_dept(deptno,deptname)values(#{deptNo},#{deptName})")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    public Long insertDept(Dept dept);
}
