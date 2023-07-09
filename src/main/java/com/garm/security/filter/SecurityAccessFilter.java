package com.garm.security.filter;

import com.garm.security.domain.dto.CheckAccessDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityAccessFilter extends OncePerRequestFilter {


    @Override
    public void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException {
        try {
            if (!isIgnore(request.getRequestURI())) {
                CheckAccessDto checkAccessDto = new CheckAccessDto();
                checkAccessDto.setUri(request.getRequestURI());

//                String result = checkSecurity(checkAccessDto);
//                if (Objects.nonNull(result)) {
                chain.doFilter(request, response);
//                } else {
                response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                setCharset(response);
//                    response.getWriter().write(result);
//                }
            } else {
                chain.doFilter(request, response);
            }
        } catch (Exception ex) {
            setCharset(response);
            response.getWriter().write(Objects.isNull(ex.getMessage()) ? "Unexpected Error Occurred!!" : ex.getMessage());
        }
    }

    private String[] excludeUrls;

    private boolean isIgnore(String uri) {
        return Arrays.stream(Optional.ofNullable(excludeUrls)
                        .orElse(new String[]{}))
                .anyMatch(uri::contains);
    }

    public static void setCharset(HttpServletResponse response) {
        response.setHeader("Content-type", "text/html; charset=utf-8");
    }
}
