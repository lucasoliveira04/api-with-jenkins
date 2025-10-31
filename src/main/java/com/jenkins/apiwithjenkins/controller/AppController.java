package com.jenkins.apiwithjenkins.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@Profile("dev")
public class AppController {

    @GetMapping("/list-user")
    public ResponseEntity<List<String>> listUsers() {
        List<String> list = new ArrayList<>();
        list.add("Jenkins");
        list.add("Jenkins");
        list.add("Jenkins");
        return ResponseEntity.ok(list);
    }
}
