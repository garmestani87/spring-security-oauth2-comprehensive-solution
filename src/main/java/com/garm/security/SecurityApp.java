package com.garm.security;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.garm")
public class SecurityApp {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApp.class, args);
    }

}
