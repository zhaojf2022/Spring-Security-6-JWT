package com.ons.securejwt.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * 注册功能要传送的数据对象
 * 使用@FieldDefaults(level = AccessLevel.PRIVATE)注解后，成员变量无需添加修饰符，默认为private
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterDto implements Serializable {

    String firstName ;
    String lastName ;
    String email;
    String password ;
}
