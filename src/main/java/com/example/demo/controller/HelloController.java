package com.example.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping(value="/hello")
public class HelloController {

    @RequestMapping(value="/get",produces = MediaType.APPLICATION_JSON_VALUE,method= RequestMethod.GET)
    @ResponseBody()
    public String getStr(){
        return "hello world !";
    }

    @RequestMapping(value="/" ,method= RequestMethod.GET)
    public String login(ModelMap map){
        map.addAttribute("title","Login Page");
        return "index";
    }


}
