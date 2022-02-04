package com.rekkme.service;

import java.util.UUID;

import com.rekkme.data.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FriendService {

    @Autowired
    private UserRepository userRepository;
    
    @Transactional(propagation = Propagation.REQUIRES_NEW,
        rollbackFor = Exception.class)
    public void addFriendRequest(UUID toUserId, UUID fromUserId) {
        this.userRepository.addFriendRequest(toUserId, fromUserId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
        rollbackFor = Exception.class)
    public void deleteFriendRequest(UUID toUserId, UUID fromUserId) {
        this.userRepository.deleteFriendRequest(toUserId, fromUserId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
        rollbackFor = Exception.class)
    public void addFriend(UUID userId, UUID friendId) {
        this.userRepository.addFriend(userId, friendId);
        this.userRepository.addFriend(friendId, userId);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,
        rollbackFor = Exception.class)
    public void deleteFriend(UUID userId, UUID friendId) {
        this.userRepository.deleteFriend(userId, friendId);
        this.userRepository.deleteFriend(friendId, userId);
    }
}
