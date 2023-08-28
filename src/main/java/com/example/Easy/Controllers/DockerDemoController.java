package com.example.Easy.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/docker")
public class DockerDemoController {
    @GetMapping("testAPI")
    public String testMethod() {
        return "Its working";
    }
}
