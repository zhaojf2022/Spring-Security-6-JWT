package com.ons.securejwt.dto;


import lombok.Data;

/**
 * token 包装器，作为向请求方传送的响应对象
 */
@Data
public class BearerToken {

    private String accessToken ;
    private String tokenType ;

    public BearerToken(String accessToken , String tokenType) {
        this.tokenType = tokenType ;
        this.accessToken = accessToken;
    }


}
