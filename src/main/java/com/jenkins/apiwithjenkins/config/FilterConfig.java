package com.jenkins.apiwithjenkins.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public ResponseLoggingFilter responseLoggingFilter() {
        return new ResponseLoggingFilter();
    }
    @Bean
    public RequestLoggingFilter requestLoggingFilter() {
        return new RequestLoggingFilter();
    }


}