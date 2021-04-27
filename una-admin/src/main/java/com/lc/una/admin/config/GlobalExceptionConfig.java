package com.lc.una.admin.config;

import com.lc.una.base.handler.MyHandlerExceptionResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 全局异常处理配置
 *
 * @author LC
 * @date 2021年4月27日17:03:51
 */
@Configuration
public class GlobalExceptionConfig {

    @Bean
    public MyHandlerExceptionResolver getHandlerExceptionResolver() {
        return new MyHandlerExceptionResolver();
    }
}
