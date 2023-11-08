package com.ons.securejwt.service;

import com.ons.securejwt.utilt.JwtUtilities;
import com.ons.securejwt.service.CustomerUserDetailsServiceImpl;
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

    /**
     * 在处理请求和响应时进行过滤操作
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param filterChain FilterChain
     * @throws ServletException 过滤服务异常
     * @throws IOException IO 异常
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        // 从http 请求中获取令牌
        String token = jwtUtilities.getToken(request);
        log.debug("获取请求头中的token：" + token);

        // 验证令牌
        if (token != null && jwtUtilities.validateToken(token)) {
            // 提取 mobile
            String mobile = jwtUtilities.extractUsername(token);
            log.debug("解析token中获得 mobile:" + mobile);
            // 根据 mobile 获取此用户的 UserDetail 对象
            UserDetails userDetails = customerUserDetailsServiceImpl.loadUserByUsername(mobile);

            if (userDetails != null) {
                // 如果用户对象不为空，则对用户的名称和权限进行认证
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                log.info("已对 {} 用户进行认证", mobile);

                // 将验证后的身份验证对象设置为当前线程的身份验证对象。
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else
            {
                log.debug("根据 mobile 获取到的 UserDetails 对象为空");
            }
        }

        //将请求(request)和响应(response)传递给下一个过滤器或者目标资源。
        filterChain.doFilter(request, response);
    }

}
