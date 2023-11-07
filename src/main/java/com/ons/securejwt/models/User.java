package com.ons.securejwt.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;


/**
 * 用户表
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements Serializable , UserDetails {


    //GenerationType.IDENTITY是一种主键生成策略，它会使用数据库自增的方式来生成主键值。
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id ;

    String firstName ;
    String lastName ;
    String email;
    String password ;

    // 一个用户可以有多个角色（数组）
    // 使用 fetch = FetchType.EAGER 设置加载策略为立即加载，即在加载该实体时同时加载与之相关联的实体。
    // 使用 cascade = CascadeType.PERSIST 设置级联操作为持久化级联，即在持久化该实体时同时持久化与之相关联的实体。
    @ManyToMany(fetch = FetchType.EAGER  , cascade = CascadeType.PERSIST)
    private List <Role> roles ;

    public User (String email , String password , List<Role> roles) {
      this.email= email ;
      this.password=password ;
      this.roles=roles ;
    }

    /**
     * 根据用户的角色获取用户的权限列表
     * @return Collection<? extends GrantedAuthority>
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 对于每个角色，构造一个SimpleGrantedAuthority对象，然后将该对象添加到 authorities 列表中
        this.roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName())));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
