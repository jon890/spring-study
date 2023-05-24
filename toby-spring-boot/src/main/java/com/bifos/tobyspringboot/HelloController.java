package com.bifos.tobyspringboot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

@Controller
public class HelloController {

    private final HelloService service;


    public HelloController(HelloService service) {
        this.service = service;
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello(String name) {
        return service.sayHello(Objects.requireNonNull(name));
    }
}
