package com.ons.securejwt.config;

import com.ons.securejwt.service.CustomerUserDetailsServiceImpl;
import com.ons.securejwt.service.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * Srping Security 访问控制的配置
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter ;
    private final CustomerUserDetailsServiceImpl customerUserDetailsServiceImpl;

    /**
     * 配置安全过滤器链
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception 异常
     */
    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception    {

        http
            // 禁用跨站请求伪造保护
            .csrf().disable()
            // 配置会话管理策略为无状态，即不使用会话
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            // 配置请求的授权规则
            .authorizeHttpRequests()
                // 允许 actuator 访问
                .requestMatchers("/actuator/**").permitAll()
                // 允许所有用户访问"/user"开头的请求路径
                .requestMatchers("/user/**").permitAll()
                // 允许 ADMIN 权限的用户访问"/admin"的开头的请求路径
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                // 允许 SUPERADMIN 权限的用户访问"/superadmin"开头的请求路径
                .requestMatchers("/superadmin/**").hasAuthority("SUPERADMIN") ;

        // 添加jwtAuthenticationFilter的过滤器，在UsernamePasswordAuthenticationFilter之前执行
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // 构建并返回配置好的HttpSecurity对象
        return  http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception
    { return authenticationConfiguration.getAuthenticationManager();}

    @Bean
    public PasswordEncoder passwordEncoder()
    { return new BCryptPasswordEncoder(); }

}
