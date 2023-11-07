package com.ons.securejwt.config;

import com.ons.securejwt.bean.TerminateBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huangqingshi
 * @Date 2019-08-17
 */
@Configuration
public class ShutdownConfig {
    @Bean
    public TerminateBean getTerminateBean() {
        return new TerminateBean();
    }
}