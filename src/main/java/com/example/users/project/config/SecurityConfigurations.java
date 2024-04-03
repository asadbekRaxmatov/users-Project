package com.example.users.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.SecurityConfigurer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface SecurityConfigurations extends SecurityConfigurer {
    @Bean
    PasswordEncoder passwordEncoder();

    @Override
    void init(SecurityBuilder builder) throws Exception;

    @Override
    void configure(SecurityBuilder builder) throws Exception;

    void configure(AuthenticationManagerBuilder auth) throws Exception;

    void configure(HttpSecurity http) throws Exception;
}
