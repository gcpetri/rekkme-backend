package com.rekkme.service;

import org.springframework.transaction.annotation.Transactional;
import com.rekkme.data.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.stereotype.Service;

@Service
public class UserService {

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
}
