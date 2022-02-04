package com.rekkme.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.websocket.server.PathParam;

import com.rekkme.data.dtos.FriendDto;
import com.rekkme.data.entity.User;
import com.rekkme.data.repository.UserRepository;
import com.rekkme.exception.RecordNotFoundException;
import com.rekkme.exception.UserNotFoundException;
import com.rekkme.service.FriendService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.api.basepath}/friends")
public class FriendController {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendService friendService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value={"/", ""})
    public List<FriendDto> getUserFriends(@RequestAttribute("user") User user) {
        return user.getFriends()
            .stream()
            .map(this::convertToFriendDto)
            .collect(Collectors.toList());
    }

    @GetMapping("/requests")
    public List<FriendDto> getUserFriendRequestsTo(@RequestAttribute("user") User user) {
        return userRepository.getFriendRequestsTo(user.getUserId())
            .stream()
            .map(this::convertToFriendDto)
            .collect(Collectors.toList());
    }

    @GetMapping("/requests/from")
    public List<FriendDto> getUserFriendRequestsFrom(@RequestAttribute("user") User user) {
        return userRepository.getFriendRequestsFrom(user.getUserId())
            .stream()
            .map(this::convertToFriendDto)
            .collect(Collectors.toList());
    }

    @PostMapping("/requests/toggle")
    public ResponseEntity<Object> makeFriendRequest(@RequestAttribute("user") User user,
        @PathParam("username") String username) {
        User toUser = this.userRepository.findByUsername(username);
        if (toUser == null) {
            throw new UserNotFoundException("Users", username);
        }
        System.out.println("got toUser");
        int count = this.userRepository.getFriendRequest(toUser.getUserId(), user.getUserId());
        if (count == 0) {
            this.friendService.addFriendRequest(toUser.getUserId(), user.getUserId());
        } else {
            this.friendService.deleteFriendRequest(toUser.getUserId(), user.getUserId());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/requests/accept")
    public ResponseEntity<Object> acceptFriendRequest(@RequestAttribute("user") User user,
        @PathParam("username") String username) {
        User fromUser = this.userRepository.findByUsername(username);
        if (fromUser == null) { // username doesn't exist
            throw new UserNotFoundException("Users", username);
        }
        int count = this.userRepository.getFriendRequest(user.getUserId(), fromUser.getUserId());
        if (count == 0) { // no friend request to accept
            throw new RecordNotFoundException("Friend Requests", fromUser.getUserId());
        }
        this.friendService.deleteFriendRequest(user.getUserId(), fromUser.getUserId());
        this.friendService.addFriend(user.getUserId(), fromUser.getUserId());
        user.getFriends().add(fromUser);
        fromUser.getFriends().add(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/requests/reject")
    public ResponseEntity<Object> rejectFriendRequest(@RequestAttribute("user") User user,
        @PathParam("username") String username) {
        User fromUser = this.userRepository.findByUsername(username);
        if (fromUser == null) { // username doesn't exist
            throw new UserNotFoundException("Users", username);
        }
        int count = this.userRepository.getFriendRequest(user.getUserId(), fromUser.getUserId());
        if (count == 0) { // no friend request to accept
            throw new RecordNotFoundException("Friend Requests", fromUser.getUserId());
        }
        this.friendService.deleteFriendRequest(user.getUserId(), fromUser.getUserId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteFriend(@RequestAttribute("user") User user,
        @PathParam("username") String username) {
        User friend = this.userRepository.findByUsername(username);
        if (friend == null) {
            throw new UserNotFoundException("Users", username);
        }
        this.friendService.deleteFriend(user.getUserId(), friend.getUserId());
        user.getFriends().remove(friend);
        friend.getFriends().remove(user);
        return ResponseEntity.ok().build();
    }

    // utils

    private FriendDto convertToFriendDto(User user) {
        FriendDto friendDto = modelMapper.map(user, FriendDto.class);
        return friendDto;
    }
}
