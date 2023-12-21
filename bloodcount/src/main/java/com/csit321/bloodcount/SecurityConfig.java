package com.csit321.bloodcount;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.servlet.http.HttpServletResponse;

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
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable()
                .exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
                    System.out.println("Authentication error: " + authException.getMessage());
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication error");
                });
    }
}
