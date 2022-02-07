package com.rekkme.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.rekkme.data.entity.User;
import com.rekkme.dtos.entity.UserDto;
import com.rekkme.dtos.forms.LoginDto;
import com.rekkme.dtos.forms.UserCreateDto;
import com.rekkme.dtos.responses.LoginResponseDto;
import com.rekkme.exception.CreateUserException;
import com.rekkme.security.JwtUtil;
import com.rekkme.service.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${app.api.basepath}")
@RequiredArgsConstructor
public class UserController {

    @Value("${app.api.cookieAge:604800}")
    private int COOKIE_AGE;

    @Value("${app.api.basepath}")
    private String basepath;

    @Value("${app.api.cookieName}")
    private String cookieName;

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;

    @GetMapping(value={"/", ""})
    public UserDto getUser(@RequestAttribute("user") User user) {
        return convertToDto(user);
    }

    @GetMapping("/ping")
    public ResponseEntity<Object> getLogin() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> userLogin(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        LoginResponseDto respDto = new LoginResponseDto();

        Map<String, String> usernameAndPassword = this.userService.getUsernameAndPassword(loginDto.getUsername());

        // no password found
        if (usernameAndPassword == null) {
            respDto.setSuccess(false);
            return ResponseEntity.status(HttpStatus.OK).body(respDto);
        }

        // passwords don't match
        if (!usernameAndPassword.get(loginDto.getUsername()).equals(loginDto.getPassword())) {
            respDto.setSuccess(false);
            return ResponseEntity.status(HttpStatus.OK).body(respDto);
        }
    
        final String jwt = this.jwtUtil.generateToken(loginDto.getUsername());
        respDto.setSuccess(true);
        respDto.setJwt(jwt);

        Cookie cookie = new Cookie(this.cookieName, jwt);
        cookie.setSecure(true);
        cookie.setMaxAge(this.COOKIE_AGE);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.status(HttpStatus.OK).body(respDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> userLogout(@RequestAttribute("user") User user, HttpServletResponse response) throws IOException {
        Cookie cookie = new Cookie(this.cookieName, null);
        cookie.setSecure(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> userDelete(@RequestAttribute("user") User user, HttpServletResponse response) throws IOException {
        this.userService.deleteUser(user);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/login/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> userCreate(@RequestBody UserCreateDto userCreateDto, HttpServletResponse response) throws IOException {
        try {
            LoginResponseDto respDto = new LoginResponseDto();
            User user = this.userService.createUser(userCreateDto);
            if (user == null) {
                respDto.setSuccess(false);
                return ResponseEntity.status(HttpStatus.OK).body(respDto);
            }
            final String jwt = this.jwtUtil.generateToken(user.getUsername());
            respDto.setSuccess(true);
            respDto.setJwt(jwt);

            Cookie cookie = new Cookie(this.cookieName, jwt);
            cookie.setSecure(true);
            cookie.setMaxAge(this.COOKIE_AGE);
            cookie.setPath("/");
            response.addCookie(cookie);

            return ResponseEntity.status(HttpStatus.OK).body(respDto);
        } catch (Exception e) {
            throw new CreateUserException(e.getMessage());
        }
    }

    // utils

    private UserDto convertToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }
}
