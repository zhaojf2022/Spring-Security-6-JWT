package com.ons.securejwt.models;

import com.ons.securejwt.models.Role;
import com.ons.securejwt.models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * IRoleRepository接口将使用Role作为实体类的类型，使用Integer作为主键的类型。
 */
public interface IRoleRepository extends JpaRepository<Role,Integer> {

    /**
     * 此方法可以自动生成 SQL 语句，根据 rolename 字段来查询
     * @param roleName Rolename
     * @return Role
     */
    Role findByRoleName(RoleName roleName);

}
