package com.garm.security.handler;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.garm.security.helper.ErrorMessageHelper.setErrorMessage;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        String messageCode = e.getMessage();
        if (e instanceof BadCredentialsException) {
            messageCode = "security.bad.credencial";
        }
        setErrorMessage(response, messageCode);
    }
}
