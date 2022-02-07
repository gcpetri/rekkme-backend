package com.rekkme.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.rekkme.data.entity.User;
import com.rekkme.data.repository.UserRepository;
import com.rekkme.dtos.entity.UserDto;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${app.api.basepath}/explore/users")
@RequiredArgsConstructor
public class UserQueryController {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    
    @GetMapping
    public List<UserDto> queryUsersByUsername(@RequestParam String q) {
        System.out.println("Query: " + q);
        String[] strArr = q.split("\\s+");
        String regexStr = "%(";
        for (int i = 0; i < strArr.length; i++) {
            regexStr += "(" + strArr[i] + ")";
            if (i != strArr.length - 1) regexStr += "|";
        }
        regexStr += ")%";
        if (regexStr.length() < 5) {
            return new ArrayList<>();
        }
        System.out.println("RegexStr" + regexStr);
        List<User> users = this.userRepository.findUserLike(regexStr);
        return users.stream()
            .map(this::convertToUserDto)
            .collect(Collectors.toList());
    }

    @GetMapping("/top")
    public List<UserDto> queryTopUsers() {
        List<User> users = this.userRepository.findTopUsers();
        return users.stream()
            .map(this::convertToUserDto)
            .collect(Collectors.toList());
    }


    // utils

    private UserDto convertToUserDto(User user) {
        UserDto friendDto = modelMapper.map(user, UserDto.class);
        return friendDto;
    }
}
