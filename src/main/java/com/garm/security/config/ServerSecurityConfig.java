package com.garm.security.config;

import com.garm.security.cache.CaptchaCacheService;
import com.garm.security.cache.TokenCacheService;
import com.garm.security.cache.UserCacheService;
import com.garm.security.filter.CustomAuthenticationFilter;
import com.garm.security.handler.CustomAuthenticationFailureHandler;
import com.garm.security.handler.CustomAuthenticationSuccessHandler;
import com.garm.security.provider.MongoTokenStore;
import com.garm.security.service.userdetail.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

import static com.garm.security.util.SecurityConstant.LOGIN_URL;

@Configuration
@EnableWebSecurity
@Import(EncoderConfig.class)
@RequiredArgsConstructor
public class ServerSecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenCacheService tokenCache;
    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder userPasswordEncoder;
    private final MongoTokenStore tokenStore;
    private final UserCacheService userCache;
    private final CaptchaCacheService captchaCache;
    private final Environment env;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider())
                .authenticationEventPublisher(authenticationEventPublisher())
                .userDetailsService(userDetailsService)
                .passwordEncoder(userPasswordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring().antMatchers("/resource/**", "/actuator/**");
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(userPasswordEncoder);
        return authProvider;
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authProvider())
//                .authenticationEventPublisher(authenticationEventPublisher());
//    }

    @Bean
    public DefaultAuthenticationEventPublisher authenticationEventPublisher() {
        return new DefaultAuthenticationEventPublisher();
    }

    @Bean
    public CustomAuthenticationFilter authenticationFilter() throws Exception {
        CustomAuthenticationFilter authFilter = new CustomAuthenticationFilter(tokenCache, tokenStore, userCache, captchaCache, env);
        authFilter.setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandler(tokenStore));
        authFilter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler());
        authFilter.setAuthenticationManager(authenticationManager());
        authFilter.setRequiresAuthenticationRequestMatcher(new OrRequestMatcher(new AntPathRequestMatcher(LOGIN_URL)));
        return authFilter;
    }
}