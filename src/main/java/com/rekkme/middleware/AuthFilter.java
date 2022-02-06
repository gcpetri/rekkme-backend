package com.rekkme.middleware;

import java.io.IOException;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rekkme.data.repository.UserRepository;
import com.rekkme.exception.RedirectToCreateNewPasswordException;
import com.rekkme.exception.RedirectToLoginException;
import com.rekkme.security.JwtUtil;
import com.rekkme.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthFilter extends OncePerRequestFilter {
    
    @Value("${app.api.basepath}")
    private String basepath;

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
            path.endsWith(this.basepath + "/session") || path.contains(this.basepath + "/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {

        System.out.println("AuthFilter hit");
        
        // get the bearer token
        final String authorizationHeader = request.getHeader("Authorization");

        String jwt = null;
        String username = null;

        // there is no bearer token
        if (authorizationHeader == null || !(authorizationHeader.startsWith("Bearer "))) {
            throw new RedirectToLoginException();
        }

        jwt = authorizationHeader.substring(7);
        username = jwtUtil.extractUsername(jwt);

        // could not get username
        if (username == null) {
            throw new RedirectToLoginException();
        }

        Map<String, String> usernameAndPassword = this.userService.getUsernameAndPassword(username);

        // there is no password on record
        if (usernameAndPassword == null) {
            throw new RedirectToCreateNewPasswordException();
        }

        // token is invalid
        if (jwtUtil.validateToken(jwt, username)) {  
            request.setAttribute("user", this.userRepository.findByUsername(username));
            filterChain.doFilter(request, response);
            return;
        }
        throw new RedirectToLoginException();
    }
}
