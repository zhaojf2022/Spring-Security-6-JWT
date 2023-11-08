package com.ons.securejwt.service;

import com.ons.securejwt.models.IUserRepository;
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
     * @param mobile 用户手机号（用户名）
     * @return UserDetails
     * @throws UsernameNotFoundException 无法找到用户异常
     */
    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        // 根据手机号查找用户信息。如果找不到对应的用户，将抛出UsernameNotFoundException异常。如果找到了用户信息，将返回该用户信息。
        return iUserRepository.findByMobile(mobile).orElseThrow(()-> new UsernameNotFoundException("找不到用户："+ mobile));

    }


}
