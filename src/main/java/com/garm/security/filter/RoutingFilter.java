package com.garm.security.filter;

import com.garm.security.service.servicepath.impl.ServicePathUrlServiceImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.garm.security.helper.ErrorMessageHelper.setErrorMessage;


public class RoutingFilter extends OncePerRequestFilter {

    private final ServicePathUrlServiceImpl servicePathUrlService;

    public RoutingFilter(ServicePathUrlServiceImpl servicePathUrlService) {
        this.servicePathUrlService = servicePathUrlService;
    }

    @Override
    public void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException {
        try {
            servicePathUrlService.hasAccessUrlThisPath(SecurityContextHolder.getContext().getAuthentication().getName(), request.getRequestURI());
            chain.doFilter(request, response);
        } catch (Exception ex) {
            setErrorMessage(response, ex.getMessage());
        }
    }

}