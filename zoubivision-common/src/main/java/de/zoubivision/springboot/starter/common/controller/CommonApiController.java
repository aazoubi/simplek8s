package de.zoubivision.springboot.starter.common.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CommonApiController {

    @GetMapping
    public String getCommonApi(){
        return "Hallo Spring Common boot";
    }
}
