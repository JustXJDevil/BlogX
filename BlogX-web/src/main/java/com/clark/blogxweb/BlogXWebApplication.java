package com.clark.blogxweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.clark.*"
})
public class BlogXWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogXWebApplication.class, args);
    }

}
