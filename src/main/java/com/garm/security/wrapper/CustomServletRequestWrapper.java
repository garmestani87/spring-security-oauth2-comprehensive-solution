package com.garm.security.wrapper;

import org.springframework.security.web.savedrequest.Enumerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class CustomServletRequestWrapper extends HttpServletRequestWrapper {

    private final HashMap<String, String[]> params;

    public CustomServletRequestWrapper(HttpServletRequest request, HashMap<String, String[]> params) {
        super(request);
        this.params = params;
    }

    @Override
    public String getParameter(String name) {
        if (this.params.containsKey(name)) {
            return this.params.get(name)[0];
        }
        return "";
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return this.params;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return new Enumerator<>(params.keySet());
    }

    @Override
    public String[] getParameterValues(String name) {
        return params.get(name);
    }

    @Override
    public String getHeader(String name) {
        if (this.params.containsKey(name)) {
            return this.params.get(name)[0];
        }
        return super.getHeader(name);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        return super.getHeaders(name);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return super.getHeaderNames();
    }


}