package com.bifos.tobyspringboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final HelloService service;


    public HelloController(HelloService service) {
        this.service = service;
    }

    @GetMapping("/hello")
    public String hello(String name) {
        if (name == null || name.trim().length() == 0) throw new IllegalArgumentException();

        return service.sayHello(name);
    }
}
