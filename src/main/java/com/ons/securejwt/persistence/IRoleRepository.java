package com.ons.securejwt.persistence;

import com.ons.securejwt.models.Role;
import com.ons.securejwt.models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role,Integer> {

    Role findByRoleName(RoleName roleName);


}
