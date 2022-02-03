package com.rekkme.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import com.rekkme.data.dtos.LoginDto;
import com.rekkme.data.dtos.LoginResponseDto;
import com.rekkme.data.dtos.SessionDto;
import com.rekkme.data.dtos.UserCreateDto;
import com.rekkme.data.dtos.UserDto;
import com.rekkme.data.entity.Auth;
import com.rekkme.data.entity.User;
import com.rekkme.data.repository.AuthRepository;
import com.rekkme.data.repository.UserRepository;
import com.rekkme.exception.RecordNotFoundException;
import com.rekkme.service.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.api.basepath}")
public class UserController {

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

    @GetMapping("/session")
    public SessionDto getUserFromSession(@RequestAttribute("user") User user) {
        SessionDto resp = new SessionDto();
        resp.setSession(convertToDto(user));
        return resp;
    }

    @GetMapping("/friends")
    public List<UserDto> getUserFriends(@RequestAttribute("user") User user) {
        return user.getFriends()
            .stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    @PostMapping("/friends/save")
    public List<UserDto> addUserFriend(@RequestAttribute("user") User user, @PathParam("username") String username) {
        User friend = this.userRepository.findByUsername(username);
        if (friend == null) {
            return user.getFriends().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        }
        if (!user.getFriends().contains(friend)) {
            this.userService.addFriend(user.getUserId(), friend.getUserId());
        }
        user.getFriends().add(friend);
        return user.getFriends()
            .stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    @DeleteMapping("/friends/delete")
    public List<UserDto> removeUserFriend(@RequestAttribute("user") User user, @PathParam("username") String username) {
        User friend = this.userRepository.findByUsername(username);
        if (friend == null) {
            System.out.println("friend not found");
            return user.getFriends()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        }
        if (user.getFriends().contains(friend)) {
            System.out.println("Found the friend");
            this.userService.deleteFriend(user.getUserId(), friend.getUserId());
            user.getFriends().remove(friend);
        }
        return user.getFriends()
            .stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> userLogin(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        User user = this.userRepository.findByUsername(loginDto.getUsername());
        Auth auth = this.authRepository.findByUserId(user.getUserId());
        LoginResponseDto respDto = new LoginResponseDto();
        if (!loginDto.getPassword().equals(auth.getPassword())) {
            respDto.setSuccess(false);
            return ResponseEntity.status(HttpStatus.OK).body(respDto);
        }
        Cookie cookie = new Cookie("userid", user.getUserId().toString());
        cookie.setSecure(true);
        response.addCookie(cookie);
        respDto.setSuccess(true);
        return ResponseEntity.status(HttpStatus.OK).body(respDto);
    }

    @GetMapping("/login")
    public ResponseEntity<Object> getLogin() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> userLogout(@RequestAttribute("user") User user, HttpServletResponse response) throws IOException {
        Cookie cookie = new Cookie("userid", null);
        cookie.setSecure(true);
        response.addCookie(cookie);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @CrossOrigin
    @PostMapping("/login/create")
    public ResponseEntity<Object> userCreate(@RequestBody UserCreateDto userCreateDto, HttpServletResponse response) throws IOException {
        LoginResponseDto respDto = new LoginResponseDto();
        User user = this.userService.createUser(userCreateDto);
        if (user == null) {
            respDto.setSuccess(false);
            return ResponseEntity.status(HttpStatus.OK).body(respDto);
        }
        Cookie cookie = new Cookie("userid", user.getUserId().toString());
        cookie.setSecure(true);
        response.addCookie(cookie);
        respDto.setSuccess(true);
        return ResponseEntity.status(HttpStatus.OK).body(respDto);
    }

    // utils

    private UserDto convertToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    private User convertToEntity(UserDto userDto) throws ParseException {
        User user = modelMapper.map(userDto, User.class);
        return user;
    }
}
