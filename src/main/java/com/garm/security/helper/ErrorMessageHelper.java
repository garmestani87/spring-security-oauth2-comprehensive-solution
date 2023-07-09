package com.garm.security.helper;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ErrorMessageHelper {


    public static void setErrorMessage(HttpServletResponse response, String message) throws IOException {
        // set your error message
    }

    public static void setException(HttpServletResponse response, String messageCode) {
        // set your exception
    }

    public static void setErrorMessage(HttpServletResponse response, String message, HttpStatus responseCode) throws IOException {
        // set your error message
    }

}
