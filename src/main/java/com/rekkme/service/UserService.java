package com.rekkme.service;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.rekkme.data.entity.Auth;
import com.rekkme.data.entity.User;
import com.rekkme.data.repository.AuthRepository;
import com.rekkme.data.repository.UserRepository;
import com.rekkme.dtos.forms.UserCreateDto;
import com.rekkme.exception.UserNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern
        .compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static final Pattern VALID_USERNAME_REGEX = Pattern
        .compile("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$");

    @Value("${app.api.avatarUrl}")
    private String avatarUrl;

    @Value("${app.api.lowPoints:100}")
    private Integer lowPoints;

    private String defaultAvatar = "guy-1.png";
    private final UserRepository userRepository;
    private final AuthRepository authRepository;

    public User createUser(UserCreateDto userCreateDto) throws Exception {

        // create the user
        User user = new User();
        if (!validateEmail(userCreateDto.getEmail())) {
            throw new Exception("invalid email format");
        }
        if (!validateUsername(userCreateDto.getUsername())) {
            throw new Exception("invalid username format");
        }
        if (userCreateDto.getAvatar() == null) {
            user.setImageUrl(this.avatarUrl + this.defaultAvatar);
        } else {
            user.setImageUrl(this.avatarUrl + userCreateDto.getAvatar());
        }
        user.setEmail(userCreateDto.getEmail());
        user.setFirstName(userCreateDto.getFirstname());
        user.setLastName(userCreateDto.getLastname());
        user.setUsername(userCreateDto.getUsername());
        user.setKos(0);
        user.setRekPoints(this.lowPoints);
        user.setLastLogin(LocalDateTime.now());

        // create the auth
        Auth auth = new Auth();
        User resUser = this.userRepository.save(user);
        auth.setPassword(userCreateDto.getPassword());
        auth.setUser(resUser);
        this.authRepository.save(auth);
        return resUser;
    }

    public void deleteUser(User user) {
        this.userRepository.delete(user);
    }

    public Map<String, String> getUsernameAndPassword(String username) {
        User user = this.userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }
        Auth auth = this.authRepository.findByUserId(user.getUserId());
        if (auth == null || auth.getPassword() == null) {
            // reset password
            return null;
        }
        Map<String, String> map = new HashMap<>();
        map.put(username, auth.getPassword());
        return map;
    }

    public User editUser(UserCreateDto userCreateDto, User existingUser) throws Exception {

        // create the user
        if (userCreateDto.getUsername() != null) {
            if (!validateUsername(userCreateDto.getUsername())) {
                throw new Exception("invalid username format");
            }
            existingUser.setUsername(userCreateDto.getUsername());
        }
        if (userCreateDto.getEmail() != null) {
            if (!validateEmail(userCreateDto.getEmail())) {
                throw new Exception("invalid email format");
            }
            existingUser.setEmail(userCreateDto.getEmail());
        }
        if (userCreateDto.getAvatar() != null) {
            if (userCreateDto.getAvatar() == null) {
                existingUser.setImageUrl(this.avatarUrl + this.defaultAvatar);
            } else {
                existingUser.setImageUrl(this.avatarUrl + userCreateDto.getAvatar());
            }
        }
        if (userCreateDto.getFirstname() != null) {
            existingUser.setFirstName(userCreateDto.getFirstname());
        }
        if (userCreateDto.getLastname() != null) {
            existingUser.setLastName(userCreateDto.getLastname());
        }
        if (userCreateDto.getIsPublic() != null) {
            existingUser.setIsPublic(userCreateDto.getIsPublic());
        }
        
        existingUser.setLastLogin(LocalDateTime.now());

        // create the auth
        User resUser = this.userRepository.save(existingUser);
        return resUser;
    }

    // utils

    private static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    private static boolean validateUsername(String usernameStr) {
        Matcher matcher = VALID_USERNAME_REGEX.matcher(usernameStr);
        return matcher.find();
    }
}
