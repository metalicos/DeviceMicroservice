package com.cyberdone.DeviceMicroservice.security.filter;

import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_MAX_AGE;

public class CrossOriginFilter extends OncePerRequestFilter {

    private static final String ALLOWED_PATTERN = "*";
    private static final String XSRF_TOKEN = "xsrf-token";
    private static final String ALLOWED_HEADERS = "authorization, content-type, xsrf-token";
    private static final String ALLOWED_AGE = "3600";
    private static final String ALLOWED_METHODES = "GET, POST, PUT, PATCH, DELETE, OPTIONS";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, ALLOWED_PATTERN);
        response.setHeader(ACCESS_CONTROL_ALLOW_METHODS, ALLOWED_METHODES);
        response.setHeader(ACCESS_CONTROL_MAX_AGE, ALLOWED_AGE);
        response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, ALLOWED_HEADERS);
        response.addHeader(ACCESS_CONTROL_EXPOSE_HEADERS, XSRF_TOKEN);

        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
