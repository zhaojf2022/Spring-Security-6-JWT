package com.ons.securejwt.controller;


import com.ons.securejwt.service.UserService;
import com.ons.securejwt.dto.LoginDto;
import com.ons.securejwt.dto.RegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * user 角色用户的接口功能
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    /**
     * 注册用户接口
     * 接收一个RegisterDto对象作为参数，使用 @RequestBody 注解将请求体映射为RegisterDto对象
     * RessourceEndPoint: <a href="http://localhost:13537/api/user/register">...</a>
     * @param registerDto RegisterDto
     * @return ResponseEntity<?>
     */
    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody RegisterDto registerDto)  {
        return  userService.register(registerDto);
    }

    /**
     * 用户认证接口，认证通过后返回一个JWT 令牌（token）
     * @param loginDto LoginDto
     * @return String
     */
    //RessourceEndPoint:http://localhost:13537/api/user/authenticate
    @PostMapping("/authenticate")
    public String authenticate(@RequestBody LoginDto loginDto)
    { return  userService.authenticate(loginDto);}



}
