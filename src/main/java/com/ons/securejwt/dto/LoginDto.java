package com.ons.securejwt.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * 登录请求时需要传输的数据对象
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginDto {

    String mobile ;
    String password ;
}
