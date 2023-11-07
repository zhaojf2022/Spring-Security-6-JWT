package com.ons.securejwt.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SuperAdmin角色用户的接口功能
 */
@RestController
@RequestMapping("/superadmin")
@RequiredArgsConstructor
public class SuperadminRestController {


    //RessourceEndPoint:http://localhost:13537/api/superadmin/hi
    @GetMapping("/hi")
    public String sayHi ()
    { return "Hi" ;}


}
