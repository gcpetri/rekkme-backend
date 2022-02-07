package com.rekkme.middleware;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rekkme.data.entity.User;
import com.rekkme.data.repository.UserRepository;
import com.rekkme.dtos.responses.RedirectResponseDto;
import com.rekkme.security.JwtUtil;
import com.rekkme.service.UserService;

import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthFilter extends OncePerRequestFilter {
    
    @Value("${app.api.basepath}")
    private String basepath;

    @Value("${app.api.cookieName}")
    private String cookieName;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.endsWith(this.basepath + "/ping") || 
            path.endsWith(this.basepath + "/session") || 
            path.contains(this.basepath + "/login") ||
            path.contains(this.basepath + "/explore");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {

        System.out.println("AuthFilter hit");

        RedirectResponseDto redirectResponseDto = new RedirectResponseDto();

        response.setContentType(ContentType.APPLICATION_JSON.toString());
        
        // get the bearer token
        // final String authorizationHeader = request.getHeader("Authorization");
        final String cookieStr = this.getTokenFromCookie(request.getCookies());

        String jwt = null;
        String username = null;

        // there is no bearer token
        // if (authorizationHeader == null || !(authorizationHeader.startsWith("Bearer "))) {
        if (cookieStr == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            redirectResponseDto.setAction("LOGIN");
            String json = new ObjectMapper().writeValueAsString(redirectResponseDto);
            response.getWriter().write(json);
            response.flushBuffer();
            return;
        }

        // jwt = authorizationHeader.substring(7);
        jwt = cookieStr;
        username = jwtUtil.extractUsername(jwt);

        // could not get username
        if (username == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            redirectResponseDto.setAction("LOGIN");
            String json = new ObjectMapper().writeValueAsString(redirectResponseDto);
            response.getWriter().write(json);
            response.flushBuffer();
            return;
        }

        Map<String, String> usernameAndPassword = null;
        try {
            usernameAndPassword = this.userService.getUsernameAndPassword(username);
        } catch (Exception e) {
            redirectResponseDto.setAction("LOGIN");
            String json = new ObjectMapper().writeValueAsString(redirectResponseDto);
            response.getWriter().write(json);
            response.flushBuffer();
            return;
        }

        // there is no password on record
        if (usernameAndPassword == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            redirectResponseDto.setAction("RESET-PASSWORD");
            String json = new ObjectMapper().writeValueAsString(redirectResponseDto);
            response.getWriter().write(json);
            response.flushBuffer();
            return;
        }

        // token is invalid
        if (jwtUtil.validateToken(jwt, username)) { 
            User user = this.userRepository.findByUsername(username);
            if (user == null) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                redirectResponseDto.setAction("LOGIN");
                String json = new ObjectMapper().writeValueAsString(redirectResponseDto);
                response.getWriter().write(json);
                response.flushBuffer();
                return;
            }
            request.setAttribute("user", user);
            filterChain.doFilter(request, response);
            return;
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        redirectResponseDto.setAction("LOGIN");
        String json = new ObjectMapper().writeValueAsString(redirectResponseDto);
        response.getWriter().write(json);
        response.flushBuffer();
        return;
    }

    // utils

    private String getTokenFromCookie(Cookie[] cookies) {
        if (cookies == null || cookies.length == 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(this.cookieName)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
