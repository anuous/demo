package com.example.demo;

import com.example.demo.daoImpl.UserMapper;
import com.example.demo.model.Dept;
import com.example.demo.model.User;
import com.example.demo.serviceImpl.service.IUserService;
import com.github.pagehelper.PageInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTests {

	@Autowired
	public UserMapper userMapper;

	@Autowired
	public IUserService userService;

	@Test
	public void testInsert(){
		//Assert.assertEquals(1L,userMapper.insert("anuous",21).longValue());
		User user=new User();
		user.setName("testName_2");
		user.setAge(18);
		Long id=userService.addUser(user);
		System.out.println(user.toString());
	}
	@Test
	public void testGetByName(){
		String name="testName_2";
		User user=userService.getByName(name);
		System.out.println(user.toString());
	}

	@Test
	public void testInsertDept(){
		Dept dept=new Dept();
		dept.setDeptName("Human Resource");
		dept.setDeptNo("001");
		Long res=userService.addDept(dept);
		System.out.println(res);
	}


	@Test
	public void testPage(){
		PageInfo<User> users=userService.listUser(2,1);
		System.out.println(users.getSize());
	}
}
