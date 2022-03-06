package com.clark.blogxmanage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/hello-World", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class HelloWorld {
    @Autowired
    private com.clark.blogxfunction.controller.HelloWorld helloWorld;

    @GetMapping(value = "hello")
    public String hello() {
        String s = helloWorld.hello();
        return "hello!";
    }
}
