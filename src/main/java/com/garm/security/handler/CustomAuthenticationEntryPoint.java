package com.garm.security.handler;

import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.garm.security.helper.ErrorMessageHelper.setErrorMessage;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;


@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private String message;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {

        if (authenticationException instanceof InsufficientAuthenticationException)
            setErrorMessage(response, message, UNAUTHORIZED);
        else
            setErrorMessage(response, message);
    }

}