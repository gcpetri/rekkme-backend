package com.rekkme.middleware;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rekkme.data.entity.User;
import com.rekkme.data.repository.UserRepository;
import com.rekkme.exception.RecordNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthFilter extends OncePerRequestFilter {
    
    @Value("${app.api.basepath}")
    private String basepath;

    @Value("${app.api.cookieName}")
    private String cookieName;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
        throws ServletException {
        String path = request.getRequestURI();
        return path.contains("/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws IOException {

        // if there are not cookies
        String cookie = "";
        if (request.getCookies() == null) {
            response.sendRedirect(this.basepath + "/login");
            return;
        }

        // if the cookie is not present
        for (Cookie c : request.getCookies()) { // redirect to login
            if (c.getName().equals(this.cookieName)) {
                cookie = c.getValue();
            }
        }
        if (cookie == null) {
            response.sendRedirect(this.basepath + "/login");
            return;
        }

        // if the cookie is valid
        try {
            Long cookieLong = Long.parseLong(cookie);
            User user = this.userRepository.findById(cookieLong)
                .orElseThrow(() -> new RecordNotFoundException("User", cookieLong));
            request.setAttribute("user", user);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.sendRedirect(this.basepath + "/login");
            return;
        }
        
    }
}
