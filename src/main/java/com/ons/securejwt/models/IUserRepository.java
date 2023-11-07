package com.ons.securejwt.models;

import com.ons.securejwt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * IUserRepository 接口将使用 User 作为实体类的类型，使用Integer作为主键的类型
 * 接口中的方法将根据 JPA 的规则，自动生成 SQL 语句
 */
public interface IUserRepository extends JpaRepository<User,Integer> {

    Boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);


}


