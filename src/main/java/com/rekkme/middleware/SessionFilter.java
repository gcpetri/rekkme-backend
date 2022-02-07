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
import com.rekkme.dtos.entity.UserDto;
import com.rekkme.dtos.responses.SessionDto;
import com.rekkme.security.JwtUtil;
import com.rekkme.service.UserService;

import org.apache.http.entity.ContentType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class SessionFilter extends OncePerRequestFilter {
    
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

    @Autowired
    private ModelMapper modelMapper;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return !path.endsWith(this.basepath + "/session");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {

        System.out.println("Session hit");

        SessionDto sessionDto = new SessionDto();
        
        // get the bearer token
        // final String authorizationHeader = request.getHeader("Authorization");
        final String cookieStr = this.getTokenFromCookie(request.getCookies());

        String jwt = null;
        String username = null;

        sessionDto.setSession(null);
        response.setContentType(ContentType.APPLICATION_JSON.toString());

        // there is no bearer token
        // if (authorizationHeader == null || !(authorizationHeader.startsWith("Bearer "))) {
        if (cookieStr == null) {
            String json = new ObjectMapper().writeValueAsString(sessionDto);
            response.getWriter().write(json);
            response.flushBuffer();
            return;
        }

        // jwt = authorizationHeader.substring(7);
        jwt = cookieStr;
        username = jwtUtil.extractUsername(jwt);

        // could not get username
        if (username == null) {
            String json = new ObjectMapper().writeValueAsString(sessionDto);
            response.getWriter().write(json);
            response.flushBuffer();
            return;
        }

        Map<String, String> usernameAndPassword = this.userService.getUsernameAndPassword(username);

        // there is no password on record
        if (usernameAndPassword == null) {
            String json = new ObjectMapper().writeValueAsString(sessionDto);
            response.getWriter().write(json);
            response.flushBuffer();
            return;
        }

        // token is invalid
        if (jwtUtil.validateToken(jwt, username)) {  
            User user = this.userRepository.findByUsername(username);
            if (user == null) {
                String json = new ObjectMapper().writeValueAsString(sessionDto);
                response.getWriter().write(json);
                response.flushBuffer();
                return;
            }
            sessionDto.setSession(this.convertUserToDto(user));
            String json = new ObjectMapper().writeValueAsString(sessionDto);
            response.getWriter().write(json);
            response.flushBuffer();
            return;
        }
        String json = new ObjectMapper().writeValueAsString(sessionDto);
        response.getWriter().write(json);
        response.flushBuffer();
        return;
    }

    // utils

    private UserDto convertUserToDto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }

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
