package com.ons.securejwt.security;

import com.ons.securejwt.persistence.IUserRepository;
import com.ons.securejwt.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CustomerUserDetailsServiceImpl implements UserDetailsService {

    private final IUserRepository iUserRepository ;

    /**
     * 根据用户名查找用户信息
     * @param email 用户邮箱（用户名）
     * @return UserDetails
     * @throws UsernameNotFoundException 无法找到用户异常
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 根据邮箱查找用户信息。如果找不到对应的用户，将抛出UsernameNotFoundException异常。如果找到了用户信息，将返回该用户信息。
        return iUserRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found !"));

    }


}
