package com.ons.securejwt.models;

import com.ons.securejwt.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * IUserRepository 接口将使用 User 作为实体类的类型，使用Integer作为主键的类型
 * 接口中的方法将根据 JPA 的规则，自动生成 SQL 语句
 */
public interface IUserRepository extends JpaRepository<User,Integer> {

    /**
     * 判断指定手机号是否存在
     * @param mobile String
     * @return Boolean
     */
    Boolean existsByMobile(String mobile);

    /**
     * 根据手机号查找用户信息，并以Optional对象的形式返回
     * Optional类用于表示可能为空的值，可以有效避免空指针异常的问题
     * @param mobile String
     * @return  Optional<User>
     */
    Optional<User> findByMobile(String mobile);


}


