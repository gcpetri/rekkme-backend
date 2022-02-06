package com.rekkme.service;

import java.util.UUID;

import com.rekkme.data.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FriendService {

    private final UserRepository userRepository;
    
    public void addFriendRequest(UUID toUserId, UUID fromUserId) {
        this.userRepository.addFriendRequest(toUserId, fromUserId);
    }

    public void deleteFriendRequest(UUID toUserId, UUID fromUserId) {
        this.userRepository.deleteFriendRequest(toUserId, fromUserId);
    }

    public void addFriend(UUID userId, UUID friendId) {
        this.userRepository.addFriend(userId, friendId);
        this.userRepository.addFriend(friendId, userId);
    }

    public void deleteFriend(UUID userId, UUID friendId) {
        this.userRepository.deleteFriend(userId, friendId);
        this.userRepository.deleteFriend(friendId, userId);
    }
}
