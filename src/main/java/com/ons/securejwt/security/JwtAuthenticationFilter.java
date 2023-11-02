package com.ons.securejwt.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/**
 * 令牌验证过滤器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtilities jwtUtilities;
    private final CustomerUserDetailsServiceImpl customerUserDetailsServiceImpl;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        // 从http 请求中获取令牌
        String token = jwtUtilities.getToken(request);

        // 验证令牌
        if (token != null && jwtUtilities.validateToken(token)) {
            // 提取 email
            String email = jwtUtilities.extractUsername(token);
            // 根据 email 获取此用户的 UserDetail 对象
            UserDetails userDetails = customerUserDetailsServiceImpl.loadUserByUsername(email);

            if (userDetails != null) {
                // 如果用户对象不为空，则对用户的名称和权限进行认证
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                log.info("authenticated user with email :{}", email);

                // 将验证后的身份验证对象设置为当前线程的身份验证对象。
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        //将请求(request)和响应(response)传递给下一个过滤器或者目标资源。
        filterChain.doFilter(request, response);
    }

}
