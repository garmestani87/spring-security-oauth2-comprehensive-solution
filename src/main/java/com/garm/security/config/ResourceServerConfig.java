package com.garm.security.config;

import com.garm.security.filter.JsonToUrlEncodedAuthenticationFilter;
import com.garm.security.filter.LoggingFilter;
import com.garm.security.filter.RoutingFilter;
import com.garm.security.handler.CustomAuthenticationEntryPoint;
import com.garm.security.handler.CustomTokenExtractor;
import com.garm.security.provider.MongoTokenStore;
import com.garm.security.service.log.impl.RequestLogServiceImpl;
import com.garm.security.service.servicepath.impl.ServicePathUrlServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@Configuration
@EnableResourceServer
@RequiredArgsConstructor
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "resource-server-rest-api";
    private static final String SECURED_READ_SCOPE = "#oauth2.hasScope('read')";
    private static final String SECURED_WRITE_SCOPE = "#oauth2.hasScope('write')";
    private static final String SECURED_PATTERN = "/**";

    public final MongoTokenStore tokenStore;
    private final ServicePathUrlServiceImpl servicePathUrlService;
    private final RequestLogServiceImpl requestLogService;
    private final JsonToUrlEncodedAuthenticationFilter jsonFilter;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID)
                .tokenExtractor(new CustomTokenExtractor())
                .authenticationEntryPoint(authenticationEntryPoint);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .httpBasic().disable()
                .requestMatchers()
                .antMatchers(SECURED_PATTERN)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/resource/**").permitAll()
                .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                .antMatchers(HttpMethod.POST, SECURED_PATTERN)
                .access(SECURED_WRITE_SCOPE)
                .anyRequest()
                .access(SECURED_READ_SCOPE)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jsonFilter, ChannelProcessingFilter.class)
                .addFilterAfter(new LoggingFilter(requestLogService), AbstractPreAuthenticatedProcessingFilter.class)
                .addFilterAfter(new RoutingFilter(servicePathUrlService), AbstractPreAuthenticatedProcessingFilter.class);
    }

    @EventListener
    public void authSuccessEventListener(AuthenticationSuccessEvent authorizedEvent) {
        // write custom code here for login success audit
    }

    @EventListener
    public void authFailedEventListener(AbstractAuthenticationFailureEvent oAuth2AuthenticationFailureEvent) {
        // write custom code here login failed audit.
    }
}
