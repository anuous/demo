package com.example.demo.controller;

import org.apache.log4j.Logger;
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

    private Logger logger = Logger.getLogger(getClass());

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

    @RequestMapping(value="/changeLog",produces = MediaType.APPLICATION_JSON_VALUE,method= RequestMethod.GET)
    @ResponseBody()
    public void testLog(){
        logger.info("this is a info log ---1");
        logger.error("this is a info log ---2");
        logger.warn("this is a info log ---3");
        logger.debug("this is a info log ---4");


    }
}
