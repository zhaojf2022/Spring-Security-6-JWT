package com.ons.securejwt.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

/**
 * 角色表
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id ;

    @Enumerated(EnumType.STRING)
    RoleName roleName ;

    public Role (RoleName roleName) {this.roleName = roleName;}
    public String getRoleName() {
        return roleName.toString();
    }
}
