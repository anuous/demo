package com.example.demo.controller;

import com.example.demo.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping(value="/users")
public class UserController {

     static Map<Long,User> users= Collections.synchronizedMap(new HashMap<Long, User>());
     static{
         User u=new User();
         u.setId(1L);
         u.setAge(10);
         u.setName("default");
         users.put(1L,u);
     }

     @RequestMapping(value = "/add",method = RequestMethod.PUT)
   public String add(@ModelAttribute User user){
         users.put(user.getId(),user);
         System.out.println(users.size());
         return "success";
   }

    @RequestMapping(value="/list",method = RequestMethod.GET)
    public List<User> listUsers(){
        List<User> res=new ArrayList<User>(users.values());
        return res;
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public User getUser(@PathVariable Long id){
        return users.get(id);
    }

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public User getUser2(@RequestParam Long id){
        return users.get(id);
    }

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String exceptionTest(Boolean flag){
         if(null==flag||!flag){
             throw new RuntimeException("exceptionTest run exception !");
         }
         return "this is a success test !";
    }
}
