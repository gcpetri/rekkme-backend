package com.rekkme.middleware;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rekkme.data.entity.User;
import com.rekkme.data.repository.UserRepository;
import com.rekkme.security.JwtUtil;
import com.rekkme.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class ExploreFilter extends OncePerRequestFilter {
    
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
        return !path.contains(this.basepath + "/explore");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {

        System.out.println("ExploreFilter hit");
        
        // get the token
        // final String authorizationHeader = request.getHeader("Authorization");
        final String cookieStr = this.getTokenFromCookie(request.getCookies());

        String jwt = null;
        String username = null;

        // there is no bearer token
        // if (authorizationHeader == null || !(authorizationHeader.startsWith("Bearer "))) {
        if (cookieStr == null) {
            filterChain.doFilter(request, response);
            return;
        }

        //jwt = authorizationHeader.substring(7);
        jwt = cookieStr;
        username = jwtUtil.extractUsername(jwt);

        // could not get username
        if (username == null) {
            filterChain.doFilter(request, response);
            return;
        }

        Map<String, String> usernameAndPassword = null;
        try {
            usernameAndPassword = this.userService.getUsernameAndPassword(username);
        } catch (Exception e) {
            // do nothing
        }

        // there is no password on record
        if (usernameAndPassword == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // token is invalid
        if (jwtUtil.validateToken(jwt, username)) {
            User user = this.userRepository.findByUsername(username);
            if (user == null) {
                filterChain.doFilter(request, response);
                return;
            }
            request.setAttribute("user", this.userRepository.findByUsername(username));
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);
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
