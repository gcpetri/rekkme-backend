package com.rekkme.middleware;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rekkme.data.dtos.SessionDto;
import com.rekkme.data.dtos.UserDto;
import com.rekkme.data.entity.User;
import com.rekkme.data.repository.UserRepository;

import org.apache.http.entity.ContentType;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Component
@Order(1)
public class SessionFilter extends OncePerRequestFilter {
    
    @Value("${app.api.basepath}")
    private String basepath;

    @Value("${app.api.cookieName}")
    private String cookieName;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
        throws ServletException {
        String path = request.getRequestURI();
        return !path.endsWith("/session");
    }

    // !!!!! NOTHING SHOULD GET THROUGH !!!!! //
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws IOException {

        // if there are not cookies
        String cookie = "";
        SessionDto sessionDto = new SessionDto();
        if (request.getCookies() == null) {
            sessionDto.setSession(null);
            String json = new ObjectMapper().writeValueAsString(sessionDto);
            response.setContentType(ContentType.APPLICATION_JSON.toString());
            response.getWriter().write(json);
            response.flushBuffer();
            return;
        }

        // if the cookie is not present
        for (Cookie c : request.getCookies()) { // redirect to login
            if (c.getName().equals(this.cookieName)) {
                cookie = c.getValue();
            }
        }
        if (cookie == null) {
            sessionDto.setSession(null);
            String json = new ObjectMapper().writeValueAsString(sessionDto);
            response.setContentType(ContentType.APPLICATION_JSON.toString());
            response.getWriter().write(json);
            response.flushBuffer();
            return;
        }

        // if the cookie is valid
        try {
            Long cookieLong = Long.parseLong(cookie);
            User user = this.userRepository.getById(cookieLong);
            if (user == null) {
                sessionDto.setSession(null);
            } else {
                sessionDto.setSession(convertToDto(user));
            }
            String json = new ObjectMapper().writeValueAsString(sessionDto);
            response.setContentType(ContentType.APPLICATION_JSON.toString());
            response.getWriter().write(json);
            response.flushBuffer();
            return;
        } catch (Exception e) {
            String json = new ObjectMapper().writeValueAsString(sessionDto);
            response.setContentType(ContentType.APPLICATION_JSON.toString());
            response.getWriter().write(json);
            response.flushBuffer();
            return;
        }
    }

    private UserDto convertToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }
}
