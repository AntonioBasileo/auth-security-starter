package it.auth.security.starter.config;

import it.auth.security.core.utility.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityContext {

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }
}
