package com.ons.securejwt.utilt;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Component
public class JwtUtilities{

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;


    /**
     * token解析：获取指定 JWT 全部的声明信息
     * @param token String
     * @return Claims
     */
    public Claims extractAllClaims(String token) {
        // 使用 Jwts.parser() 创建一个 JWT 解析器对象
        // 使用 setSigningKey(secret) 方法设置 JWT 的签名密钥
        // 使用 parseClaimsJws(token) 方法解析传入的 JWT token
        // 使用 getBody() 方法获取解析后的 JWT 的载荷部分，作为 Claims 对象返回。
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * token 解析：使用指定的方法提取 token 中声明（Claims）部分的内部信息
     * @param token String
     * @param claimsResolver Function<Claims, T>
     * @return T
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        //使用claimsResolver的apply方法将Claims对象作为参数传递进去，以获取特定的声明
        return claimsResolver.apply(claims);
    }

    /**
     * token 解析：提取 token 中包含的过期时间
     * @param token String
     * @return Date
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * token 解析：提取 token 中包含的用户名
     * @param token String
     * @return String
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * 验证token中的用户名是否正确，以及是否在有效期内
     * @param token String
     * @param userDetails UserDetails
     * @return Boolean
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String mobile = extractUsername(token);
        return (mobile.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * 判断 token 是否过期（token包含的有效期是否在当前时间之前）
     * @param token String
     * @return Boolean
     */
    public Boolean isTokenExpired(String token) {

        return extractExpiration(token).before(new Date());
    }

    /**
     * 使用 mobile 和角色列表，生成新的 token
     * @param mobile String
     * @param roles List<String>
     * @return String
     */
    public String generateToken(String mobile , List<String> roles) {

        return Jwts.builder()
                .setSubject(mobile)
                .claim("role",roles)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(Instant.now().plus(jwtExpiration, ChronoUnit.MILLIS)))
                // 用密钥签名
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    /**
     * 验证 token 是否有效
     * @param token String
     * @return boolean
     */
    public boolean validateToken(String token) {
        try {
            // 使用解析令牌的方式来验证，如果没有异常，则验证通过
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.info("JWT 签名错误.");
            log.trace("Invalid JWT signature trace: {%s}", e);
        } catch (MalformedJwtException e) {
            log.info("无效的JWT token.");
            log.trace("Invalid JWT token trace: {%s}", e);
        } catch (ExpiredJwtException e) {
            log.info("JWT token 已过期.");
            log.trace("Expired JWT token trace: {%s}", e);
        } catch (UnsupportedJwtException e) {
            log.info("不支持的JWT token.");
            log.trace("Unsupported JWT token trace: {%s}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token 压缩处理器异常.");
            log.trace("JWT token compact of handler are invalid trace: {%s}", e);
        }
        return false;
    }

    /**
     * 从 http 请求头中提取令牌
     * @param httpServletRequest HttpServletRequest
     * @return String
     */

    public String getToken (HttpServletRequest httpServletRequest) {

         log.info("收到请求：" + httpServletRequest.toString());

         final String bearerToken = httpServletRequest.getHeader("Authorization");
         log.info("提取认证字符串：" + bearerToken);

         if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
             String tokenStr = bearerToken.substring(7);
             log.info("解析 token: "+ tokenStr);
             // 返回 "Bearer " 之后的部分
             return tokenStr;
         }else {
             log.info("token字符串为空或没有以'Bearer '开头");
         }

         return null;
    }

}
