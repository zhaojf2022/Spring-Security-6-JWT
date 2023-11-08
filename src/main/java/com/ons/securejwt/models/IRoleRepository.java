package com.ons.securejwt.models;

import com.ons.securejwt.models.Role;
import com.ons.securejwt.models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * IRoleRepository接口将使用Role作为实体类的类型，使用Integer作为主键的类型。
 */
public interface IRoleRepository extends JpaRepository<Role,Integer> {

    /**
     * 此方法可根据方法名称自动生成 SQL 语句，根据参数的类型（字段）和值 roleName 来生成条件
     * @param roleName RoleName
     * @return Role
     */
    Role findByRoleName(RoleName roleName);

}
