package com.rekkme.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.rekkme.data.entity.Category;
import com.rekkme.data.entity.Comment;
import com.rekkme.data.entity.Rek;
import com.rekkme.data.entity.RekResult;
import com.rekkme.data.entity.Tag;
import com.rekkme.data.entity.User;
import com.rekkme.data.repository.CommentRepository;
import com.rekkme.data.repository.RekRepository;
import com.rekkme.data.repository.RekResultRepository;
import com.rekkme.data.repository.TagRepository;
import com.rekkme.data.repository.UserRepository;
import com.rekkme.dtos.entity.CommentDto;
import com.rekkme.dtos.forms.RekRequestDto;
import com.rekkme.dtos.forms.RekResultRequestDto;
import com.rekkme.dtos.responses.NotificationDto;
import com.rekkme.exception.NotEnoughPointsException;
import com.rekkme.exception.RecordNotFoundException;
import com.rekkme.exception.ResultAlreadyExistsException;
import com.rekkme.util.PointsUtil;

import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RekService {

    private final RekRepository rekRepository;
    private final RekResultRepository rekResultRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final QueueService queueService;
    private final NotifyService notifyService;

    public RekResult addRekResult(User user, UUID rekId, RekResultRequestDto rekResultReq) {
        Rek rek = this.rekRepository.findById(rekId)
            .orElseThrow(() -> new RecordNotFoundException("Reks", rekId));
        if (rek.getRekResult() != null) {
            throw new ResultAlreadyExistsException();
        }

        // create the result
        RekResult rekRes = new RekResult();
        rekRes.setKo(false);
        rekRes.setResult(rekResultReq.getResult());
        rekRes.setCreatedOn(LocalDateTime.now());
        rekRes.setRek(rek);
        RekResult rekResult = this.rekResultRepository.save(rekRes);
        rek.setRekResult(rekResult);

        // modify points
        User fromUser = rek.getFromUser();
        int newPoints = PointsUtil.calculatePoints(fromUser.getRekPoints(), rek.getWager(),
            rekRes.getResult(), rekRes.getKo());
        fromUser.setRekPoints(newPoints);
        this.userRepository.save(fromUser);

        // if in queue remove it
        this.queueService.remove(rekId, user);

        // send notification to fromUser
        this.notifyService.sendNotification(fromUser.getUserId(), 
            this.resultMessage(user, fromUser.getRekPoints()));
        
        return rekResult;
    }

    public List<Rek> addReks(RekRequestDto rekReq, User user, Category cat) {
        if (user.getRekPoints() < rekReq.getWager()) {
            throw new NotEnoughPointsException(user.getRekPoints());
        }
        List<Rek> newReks = new ArrayList<>(); 

        List<Tag> newTags = new ArrayList<>();
        List<Tag> oldTags = new ArrayList<>();
        for (String tagName : rekReq.getTags()) {
            Tag tag = this.tagRepository.findByNameIgnoreCase(tagName);
            if (tag == null) {
                Tag newTag = new Tag();
                newTag.setName(tagName.toLowerCase());
                newTags.add(newTag);
            } else {
                oldTags.add(tag);
            }
        }
        this.tagRepository.saveAll(newTags);

        List<UUID> toUsers = new ArrayList<>();

        for (String username : rekReq.getUsernames()) {
            if (username.equals(user.getUsername())) { // can't recommend to yourself
                System.out.println("tried to recommend to self");
                continue;
            }

            User toUser = this.userRepository.findByUsername(username);
            
            if (this.userRepository.existsFriend(user.getUserId(), toUser.getUserId()) == 0) { // can't send a rekk to someone who's not your friend
                System.out.println("tried to recommend to not a friend");
                continue;
            }
            Rek rek = new Rek();
            rek.setCreatedOn(LocalDateTime.now());
            rek.setArtist(rekReq.getArtist());
            rek.setLocation(rekReq.getLocation());
            rek.setCategory(cat);
            rek.setWager(rekReq.getWager());
            rek.setDescription(rekReq.getDescription());
            rek.setUrl(rekReq.getUrl());
            rek.setImageUrl(rekReq.getImageUrl());
            rek.setFromUser(user);
            rek.setToUser(toUser);
            rek.setTitle(rekReq.getTitle());
            newReks.add(rek);

            toUsers.add(toUser.getUserId());
        }
        List<Rek> newSavedReks = this.rekRepository.saveAll(newReks);
        
        for (Tag newTag : newTags) {
            for (Rek r : newSavedReks) {
                this.tagRepository.addRekTagLink(r.getRekId(), newTag.getTagId());
            }
        }
        for (Tag oldTag : oldTags) {
            for (Rek r : newSavedReks) {
                this.tagRepository.addRekTagLink(r.getRekId(), oldTag.getTagId());
            }
        }

        // send notification to fromUser
        for (UUID userId : toUsers) {
            this.notifyService.sendNotification(userId, 
                this.rekMessage(user, rekReq.getTitle()));
        }

        return newSavedReks;
    }

    public Comment addComment(CommentDto commentDto, User user, UUID rekId) {
        Rek rek = this.rekRepository.findById(rekId)
            .orElseThrow(() -> new RecordNotFoundException("Reks", rekId));
        Comment comment = new Comment();
        comment.setCreatedOn(LocalDateTime.now());
        comment.setMessage(commentDto.getMessage());
        comment.setUser(user);
        comment.setRek(rek);
        this.notifyService.sendNotification(rek.getFromUser().getUserId(), 
            this.rekComment(user, rek.getTitle()));
        return this.commentRepository.save(comment);
    }

    public void toggleLike(User user, UUID rekId) {
        Rek rek = this.rekRepository.findById(rekId)
            .orElseThrow(() -> new RecordNotFoundException("Reks", rekId));
        if (rek.getFromUser().getUserId().equals(user.getUserId())) { // can't like your own rek
            return;
        }
        int count = this.rekRepository.existsLike(user.getUserId(), rekId);
        System.out.println(count);
        if (count == 0) {
            System.out.println("adding like");
            this.rekRepository.addLike(user.getUserId(), rekId);
            this.notifyService.sendNotification(rek.getFromUser().getUserId(), 
                this.rekLike(user, rek.getTitle()));
        } else {
            System.out.println("deleting like");
            this.rekRepository.deleteLike(user.getUserId(), rekId);
        }
    }

    public List<Rek> findReksTo(UUID userId) {
        return this.rekRepository.findReksTo(userId);
    }

    private NotificationDto resultMessage(User user, int points) {
        NotificationDto notification = new NotificationDto();
        notification.setUsername(user.getUsername());
        notification.setMessage(user.getUsername() + " gave your rekk " + Integer.toString(points) + " points");
        notification.setAvatar(user.getImageUrl());
        return notification;
    }

    private NotificationDto rekMessage(User user, String title) {
        NotificationDto notification = new NotificationDto();
        notification.setUsername(user.getUsername());
        notification.setMessage(user.getUsername() + " sent you a Rekk: " + title);
        notification.setAvatar(user.getImageUrl());
        return notification;
    }

    private NotificationDto rekLike(User user, String title) {
        NotificationDto notification = new NotificationDto();
        notification.setUsername(user.getUsername());
        notification.setMessage(user.getUsername() + " liked your Rekk: " + title);
        notification.setAvatar(user.getImageUrl());
        return notification;
    }

    private NotificationDto rekComment(User user, String title) {
        NotificationDto notification = new NotificationDto();
        notification.setUsername(user.getUsername());
        notification.setMessage(user.getUsername() + " commented on your Rekk: " + title);
        notification.setAvatar(user.getImageUrl());
        return notification;
    }
}
