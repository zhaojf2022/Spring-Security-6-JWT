package com.ons.securejwt.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 登录功能要传送的数据对象
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginDto {

    //it's a Data Trasfer Object for Login
    String email ;
    String password ;
}
