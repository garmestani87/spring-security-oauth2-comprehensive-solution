package com.garm.security.wrapper;

import org.springframework.core.env.Environment;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class CustomServletResponseWrapper extends HttpServletResponseWrapper {

    private ServletOutputStream outputStream;
    private PrintWriter writer;
    private CustomServletOutputStream customOutputStream;
    private Environment env;

    public CustomServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    public CustomServletResponseWrapper(HttpServletResponse response, Environment env) {
        this(response);
        this.env = env;
    }

    @Override
    public void addCookie(Cookie cookie) {
        if (Arrays.stream(env.getActiveProfiles()).anyMatch(profile -> profile.contains("prod"))) {
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
        }
        cookie.setPath("/");
        super.addCookie(cookie);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (writer != null) {
            throw new IllegalStateException("getWriter() has already been called on this response.");
        }

        if (outputStream == null) {
            outputStream = getResponse().getOutputStream();
            customOutputStream = new CustomServletOutputStream(outputStream);
        }

        return customOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (outputStream != null) {
            throw new IllegalStateException("getOutputStream() has already been called on this response.");
        }

        if (writer == null) {
            customOutputStream = new CustomServletOutputStream(getResponse().getOutputStream());
            writer = new PrintWriter(new OutputStreamWriter(customOutputStream, getResponse().getCharacterEncoding()), true);
        }

        return writer;
    }

    @Override
    public void flushBuffer() throws IOException {
        if (writer != null) {
            writer.flush();
        } else if (outputStream != null) {
            customOutputStream.flush();
        }
    }

    @Override
    public boolean isCommitted() {
        if (customOutputStream == null) return false;
        return customOutputStream.isCommitted();
    }

    public void flushFinal() throws IOException {
        if (customOutputStream != null) {
            customOutputStream.reallyFlush();
        }
    }
}