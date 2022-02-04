package com.rekkme.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.rekkme.data.dtos.LoginDto;
import com.rekkme.data.dtos.LoginResponseDto;
import com.rekkme.data.dtos.UserCreateDto;
import com.rekkme.data.dtos.UserDto;
import com.rekkme.data.entity.Auth;
import com.rekkme.data.entity.User;
import com.rekkme.data.repository.AuthRepository;
import com.rekkme.data.repository.UserRepository;
import com.rekkme.service.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.api.basepath}")
public class UserController {

    private static final int COOKIE_AGE = 7 * 24 * 60 * 60; // expires in 7 days

    @Value("${app.api.basepath}")
    private String basepath;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

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
        User user = this.userRepository.findByUsername(loginDto.getUsername());
        if (user == null) {
            respDto.setSuccess(false);
            return ResponseEntity.status(HttpStatus.OK).body(respDto);
        }
        Auth auth = this.authRepository.findByUserId(user.getUserId());
        if (!loginDto.getPassword().equals(auth.getPassword())) {
            respDto.setSuccess(false);
            return ResponseEntity.status(HttpStatus.OK).body(respDto);
        }
        Cookie cookie = new Cookie("userid", user.getUserId().toString());
        cookie.setSecure(true);
        cookie.setMaxAge(COOKIE_AGE);
        cookie.setPath("/");
        response.addCookie(cookie);
        respDto.setSuccess(true);
        return ResponseEntity.status(HttpStatus.OK).body(respDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> userLogout(@RequestAttribute("user") User user, HttpServletResponse response) throws IOException {
        Cookie cookie = new Cookie("userid", null);
        cookie.setSecure(true);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> userDelete(@RequestAttribute("user") User user, HttpServletResponse response) throws IOException {
        this.userService.deleteUser(user);
        Cookie cookie = new Cookie("userid", null);
        cookie.setMaxAge(0);
        cookie.setSecure(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/login/create")
    public ResponseEntity<Object> userCreate(@RequestBody UserCreateDto userCreateDto, HttpServletResponse response) throws IOException {
        LoginResponseDto respDto = new LoginResponseDto();
        User user = this.userService.createUser(userCreateDto);
        if (user == null) {
            respDto.setSuccess(false);
            return ResponseEntity.status(HttpStatus.OK).body(respDto);
        }
        System.out.println(user.getUserId());
        Cookie cookie = new Cookie("userid", user.getUserId().toString());
        cookie.setMaxAge(COOKIE_AGE);
        cookie.setSecure(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        respDto.setSuccess(true);
        return ResponseEntity.status(HttpStatus.OK).body(respDto);
    }

    // utils

    private UserDto convertToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }
}
