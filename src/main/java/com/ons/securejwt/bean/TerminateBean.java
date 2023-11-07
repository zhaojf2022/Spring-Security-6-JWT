package com.ons.securejwt.bean;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

/**
 * @author huangqingshi
 * @Date 2019-08-17
 */
@Slf4j
public class TerminateBean {
    @PreDestroy
    public void preDestroy() {
        log.info("TerminalBean is destroyed");
    }
}