package com.ons.securejwt.service;


import com.ons.securejwt.dto.LoginDto;
import com.ons.securejwt.dto.RegisterDto;
import com.ons.securejwt.models.Role;
import com.ons.securejwt.models.User;
import org.springframework.http.ResponseEntity;


public interface UserService {
   //ResponseEntity<?> register (RegisterDto registerDto);
   //ResponseEntity<BearerToken> authenticate(LoginDto loginDto);

   /**
    * 用户认证
    * @param loginDto LoginDto
    * @return String 认证成功时返回 token
    */
   String authenticate(LoginDto loginDto);

   /**
    * 用户注册
    * @param registerDto RegisterDto
    * @return 返回响应对象（包含token）
    */
   ResponseEntity<?> register (RegisterDto registerDto);

   /**
    * 保存角色
    * @param role Role
    * @return Role
    */
   Role saveRole(Role role);

   /**
    * 保存用户
    * @param user User
    * @return User
    */
   User saverUser (User user) ;
}
