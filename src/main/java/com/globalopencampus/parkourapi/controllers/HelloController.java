package com.globalopencampus.parkourapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping
    Map<String, String> sayHello() {
        Map<String, String> hello = new HashMap<>();
        hello.put("welcome", "test");
        return hello;
    }

}