package com.ons.securejwt.service;


import com.ons.securejwt.dto.LoginDto;
import com.ons.securejwt.dto.RegisterDto;
import com.ons.securejwt.models.Role;
import com.ons.securejwt.models.User;
import org.springframework.http.ResponseEntity;


public interface UserService {
   //ResponseEntity<?> register (RegisterDto registerDto);
   //ResponseEntity<BearerToken> authenticate(LoginDto loginDto);

   String authenticate(LoginDto loginDto);
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
