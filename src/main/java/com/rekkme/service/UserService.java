package com.rekkme.service;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import com.rekkme.data.dtos.UserCreateDto;
import com.rekkme.data.entity.User;
import com.rekkme.data.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final String defaultAvatar = "https://raw.githubusercontent.com/tatiaris/rekkme/e3bd5b661546a556cd5b842a6d53c39deccf921c/public/avatars/guy-1.png";

    @Autowired
    private UserRepository userRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW,
        rollbackFor = Exception.class)
    public void addFriend(Long userId, Long friendId) {
        this.userRepository.addFriend(userId, friendId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
        rollbackFor = Exception.class)
    public void deleteFriend(Long userId, Long friendId) {
        this.userRepository.deleteFriend(userId, friendId);
    }

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
        return this.userRepository.save(user);
    }
}
