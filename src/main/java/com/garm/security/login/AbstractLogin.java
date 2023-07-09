package com.garm.security.login;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.garm.security.domain.dto.AuthenticationDto;
import com.garm.security.wrapper.CustomServletRequestWrapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.entity.ContentType;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import static com.garm.security.util.SecurityConstant.*;

public abstract class AbstractLogin implements LoginStrategy {

    private final String loginUrl;

    protected AbstractLogin(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    @Override
    public final void login(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (Objects.equals(request.getContentType(), ContentType.APPLICATION_JSON.getMimeType())
                && Objects.equals(((HttpServletRequest) request).getRequestURI(), loginUrl)) {

            HashMap<String, String[]> params = new HashMap<>();

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
                final AuthenticationDto authDto = objectMapper.readValue(request.getInputStream(), AuthenticationDto.class);
                params.put(USERNAME, new String[]{authDto.getUsername()});
                params.put(PASSWORD, new String[]{authDto.getPassword()});
                params.put(GRANT_TYPE, new String[]{authDto.getGrant_type()});
                params.put(AUTHORIZATION, new String[]{"Basic " +
                        new String(Base64.encodeBase64((authDto.getClient_id() + ":" + authDto.getClient_secret()).getBytes()))});
                setParams(params);
                validate(request, response, authDto, params);
            } catch (Exception ex) {
                setError((HttpServletResponse) response, "security.invalid.data", params);
            }

            if (!Boolean.parseBoolean(params.getOrDefault(HAS_ERROR, new String[]{"false"})[0]))
                chain.doFilter(new CustomServletRequestWrapper(((HttpServletRequest) request), params), response);

        } else {
            chain.doFilter(request, response);
        }

    }

}
