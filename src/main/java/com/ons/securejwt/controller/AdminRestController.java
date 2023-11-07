package com.ons.securejwt.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * admin角色用户的接口功能
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminRestController {


    //RessourceEndPoint:http://localhost:13537/api/admin/hello
    @GetMapping("/hello")
    public String sayHello ()
    { return "Hello" ;}


}
