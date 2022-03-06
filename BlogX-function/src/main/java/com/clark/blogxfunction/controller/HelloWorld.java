package com.clark.blogxfunction.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@FeignClient(value = "BlogFunction", //
        url = "localhost:1001")
@RestController
@RequestMapping(value = "/helloWorld", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class HelloWorld {

    @GetMapping(value = "hello")
    public String hello() {
        return "hello!";
    }
}
