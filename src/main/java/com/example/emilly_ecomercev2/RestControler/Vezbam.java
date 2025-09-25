package com.example.emilly_ecomercev2.RestControler;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("Admin/Vezbam")
@RestController
@PreAuthorize("hasRole('ADMIN')")
public class Vezbam {


    @GetMapping("/proveri")
    public void vezbam(){
        System.out.println("VLEGVIT VO ADMIN");
    }
}
