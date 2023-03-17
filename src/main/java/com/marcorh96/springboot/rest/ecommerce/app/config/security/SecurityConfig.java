package com.marcorh96.springboot.rest.ecommerce.app.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.marcorh96.springboot.rest.ecommerce.app.config.jwt.JwtAccessDeniedError;
import com.marcorh96.springboot.rest.ecommerce.app.config.jwt.JwtAuthError;
import com.marcorh96.springboot.rest.ecommerce.app.config.jwt.JwtFilter;

@Configuration
/* @EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) */
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtAuthError jwtAuthError;

    @Autowired
    private JwtAccessDeniedError jwtAccessDeniedError;

    @Bean
    JwtFilter jwtFilter() {
        return new JwtFilter();
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeHttpRequests(authConfig -> {
            authConfig.requestMatchers("/api/users/login", "/api/**" ).permitAll();
            authConfig.requestMatchers("/api/users/signin").permitAll();
            authConfig.anyRequest().authenticated();
        }).exceptionHandling().authenticationEntryPoint(jwtAuthError)
                .accessDeniedHandler(jwtAccessDeniedError).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.userDetailsService(userDetailsService);
        http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
