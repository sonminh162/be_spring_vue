package com.example.demo.controller;

import org.dom4j.util.AttributeHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController {
    @GetMapping("/test")
    public String ping() {
        return "pong";
    }
}
