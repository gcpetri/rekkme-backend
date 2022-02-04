package com.rekkme.service;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import com.rekkme.data.dtos.UserCreateDto;
import com.rekkme.data.entity.Auth;
import com.rekkme.data.entity.User;
import com.rekkme.data.repository.AuthRepository;
import com.rekkme.data.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final String defaultAvatar = "https://raw.githubusercontent.com/tatiaris/rekkme/e3bd5b661546a556cd5b842a6d53c39deccf921c/public/avatars/guy-1.png";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthRepository authRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW,
        rollbackFor = Exception.class)
    public User createUser(UserCreateDto userCreateDto) {
        User user = new User();
        user.setEmail(userCreateDto.getEmail());
        user.setFirstName(userCreateDto.getFirstname());
        user.setLastName(userCreateDto.getLastname());
        user.setUsername(userCreateDto.getUsername());
        user.setImageUrl(defaultAvatar);
        user.setKos(0);
        user.setRekPoints(0);
        user.setLastLogin(LocalDateTime.now());
        Auth auth = new Auth();
        User resUser = this.userRepository.save(user);
        auth.setPassword(userCreateDto.getPassword());
        auth.setUser(resUser);
        this.authRepository.save(auth);
        return resUser;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
        rollbackFor = Exception.class)
    public void deleteUser(User user) {
        this.userRepository.delete(user);
    }
}
