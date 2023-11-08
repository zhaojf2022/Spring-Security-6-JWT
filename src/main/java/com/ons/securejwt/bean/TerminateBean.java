package com.ons.securejwt.bean;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

/**
 * 停止服务时要运行的bean
 * @author huangqingshi
 * @Date 2019-08-17
 */
@Slf4j
public class TerminateBean {

    /**
     * 使用@PreDestroy注解指定了一个在销毁bean之前要执行的方法
     * 当bean被销毁时，该方法将被调用，可以执行一些清理操作
     */
    @PreDestroy
    public void preDestroy() {
        // 这个方法是停止服务前运行的
        log.info("TerminalBean is destroyed");
    }
}