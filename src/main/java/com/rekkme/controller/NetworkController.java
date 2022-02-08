package com.rekkme.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.rekkme.data.entity.User;
import com.rekkme.data.repository.UserRepository;
import com.rekkme.dtos.entity.UserDto;
import com.rekkme.exception.FriendException;
import com.rekkme.exception.RecordNotFoundException;
import com.rekkme.exception.UserNotFoundException;
import com.rekkme.service.FriendService;
import com.rekkme.util.ConverterUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${app.api.basepath}/network")
@RequiredArgsConstructor
public class NetworkController {

    private final UserRepository userRepository;
    private final FriendService friendService;
    private final ConverterUtil converterUtil;

    @GetMapping(value={"/friends", "/friends/", "/friends/list"})
    public List<UserDto> getFriends(@RequestAttribute("user") User user) {
        return this.userRepository.findFriends(user.getUserId())
            .stream()
            .map(u -> this.converterUtil.convertToUserDto(u))
            .collect(Collectors.toList());
    }

    @GetMapping(value={"/following", "/following/", "/following/list"})
    public List<UserDto> getFollowing(@RequestAttribute("user") User user) {
        return this.userRepository.findFollowing(user.getUserId())
            .stream()
            .map(u -> this.converterUtil.convertToUserDto(u))
            .collect(Collectors.toList());
    }

    @GetMapping(value={"/followers", "/followers/", "/followers/list"})
    public List<UserDto> getFollowers(@RequestAttribute("user") User user) {
        return this.userRepository.findFollowers(user.getUserId())
            .stream()
            .map(u -> this.converterUtil.convertToUserDto(u))
            .collect(Collectors.toList());
    }

    @GetMapping(value={"/requests", "/requests/to"})
    public List<UserDto> getUserFriendRequestsTo(@RequestAttribute("user") User user) {
        return userRepository.getFriendRequestsTo(user.getUserId())
            .stream()
            .map(u -> this.converterUtil.convertToUserDto(u))
            .collect(Collectors.toList());
    }

    @GetMapping("/requests/from")
    public List<UserDto> getUserFriendRequestsFrom(@RequestAttribute("user") User user) {
        return userRepository.getFriendRequestsFrom(user.getUserId())
            .stream()
            .map(u -> this.converterUtil.convertToUserDto(u))
            .collect(Collectors.toList());
    }

    @PostMapping("/requests/toggle")
    public ResponseEntity<Object> makeFriendRequest(@RequestAttribute("user") User user,
        @RequestParam String username) {
        User toUser = this.userRepository.findByUsername(username);
        if (toUser == null) {
            throw new UserNotFoundException(username);
        }
        // toggle the friend request
        int count = this.userRepository.existsFriendRequest(toUser.getUserId(), user.getUserId());
        if (count == 0) {
            if (this.userRepository.existsFriend(user.getUserId(), toUser.getUserId()) != 0) { // you are already following
                throw new FriendException("you are already following " + toUser.getUsername());
            }
            if (toUser.getIsPublic()) {
                this.friendService.addFriend(user.getUserId(), toUser.getUserId());
                return ResponseEntity.ok().build();
            }
            this.friendService.addFriendRequest(toUser.getUserId(), user.getUserId());
        } else {
            this.friendService.deleteFriendRequest(toUser.getUserId(), user.getUserId());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/requests/accept")
    public ResponseEntity<Object> acceptFriendRequest(@RequestAttribute("user") User user,
        @RequestParam String username) {
        User fromUser = this.userRepository.findByUsername(username);
        if (fromUser == null) { // username doesn't exist
            throw new UserNotFoundException(username);
        }
        int count = this.userRepository.existsFriendRequest(user.getUserId(), fromUser.getUserId());
        if (count == 0) { // no friend request to accept
            throw new RecordNotFoundException("Friend Requests", fromUser.getUserId());
        }
        this.friendService.deleteFriendRequest(user.getUserId(), fromUser.getUserId());
        this.friendService.addFriend(fromUser.getUserId(), user.getUserId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/requests/reject")
    public ResponseEntity<Object> rejectFriendRequest(@RequestAttribute("user") User user,
        @RequestParam String username) {
        User fromUser = this.userRepository.findByUsername(username);
        if (fromUser == null) { // username doesn't exist
            throw new UserNotFoundException(username);
        }
        int count = this.userRepository.existsFriendRequest(user.getUserId(), fromUser.getUserId());
        if (count == 0) { // no friend request to accept
            throw new RecordNotFoundException("Friend Requests", fromUser.getUserId());
        }
        this.friendService.deleteFriendRequest(user.getUserId(), fromUser.getUserId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteFriend(@RequestAttribute("user") User user,
        @RequestParam String username) {
        User friend = this.userRepository.findByUsername(username);
        if (friend == null) {
            throw new UserNotFoundException(username);
        }
        this.friendService.deleteFriend(user.getUserId(), friend.getUserId());
        return ResponseEntity.ok().build();
    }
}
