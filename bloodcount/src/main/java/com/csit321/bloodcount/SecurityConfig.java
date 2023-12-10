package com.csit321.bloodcount;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .authorizeRequests()
                .antMatchers("/public/**", "/user/**", "/donor/**", "/hospital/**", "/bloodbank/**", "/bloodrequest/**", "/admin/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .httpBasic().disable();
    }
}
