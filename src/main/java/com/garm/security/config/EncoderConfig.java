package com.garm.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.token.ClientKeyGenerator;
import org.springframework.security.oauth2.client.token.DefaultClientKeyGenerator;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;

@Configuration
public class EncoderConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    public ClientKeyGenerator clientKeyGenerator() {
        return new DefaultClientKeyGenerator();
    }

    @Bean
    public AuthenticationKeyGenerator authenticationKeyGenerator() {
        return new DefaultAuthenticationKeyGenerator();
    }

}
