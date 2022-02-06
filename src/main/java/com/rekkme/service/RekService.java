package com.rekkme.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import org.springframework.transaction.annotation.Propagation;

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
import com.rekkme.exception.RecordNotFoundException;
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

    public RekResult addRekResult(User user, UUID rekId, RekResultRequestDto rekResultReq) {
        Rek rek = this.rekRepository.getById(rekId);
        if (rek == null) {
            throw new RecordNotFoundException("rek", rekId);
        }
        System.out.println("found rek");
        RekResult rekRes = new RekResult();
        rekRes.setKo(false);
        rekRes.setResult(rekResultReq.getResult());
        rekRes.setCreatedOn(LocalDateTime.now());
        rekRes.setRek(rek);
        RekResult rekResult = this.rekResultRepository.save(rekRes);
        System.out.println("saved result");
        User fromUser = rek.getFromUser();
        rek.setRekResult(rekResult);
        fromUser.setRekPoints(fromUser.getRekPoints() + (rekRes.getResult() - rek.getWager()));
        this.userRepository.save(fromUser);
        System.out.println("added result to rek");
        return rekResult;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW,
        rollbackFor = Exception.class)
    public List<Rek> addReks(RekRequestDto rekReq, User user, Category cat) {
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

        for (String username : rekReq.getUsernames()) {
            User toUser = this.userRepository.findByUsername(username);
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
        return newSavedReks;
    }

    public Comment addComment(CommentDto commentDto, User user, UUID rekId) {
        Rek rek = this.rekRepository.getById(rekId);
        if (rek == null) {
            throw new RecordNotFoundException("reks", rekId);
        }
        Comment comment = new Comment();
        comment.setCreatedOn(LocalDateTime.now());
        comment.setMessage(commentDto.getMessage());
        comment.setUser(user);
        comment.setRek(rek);
        return this.commentRepository.save(comment);
    }

    public List<Rek> addQueue(User user, UUID rekId) {
        Rek rek = this.rekRepository.getById(rekId);
        if (rek == null) {
            throw new RecordNotFoundException("reks", rekId);
        }
        List<Long> queueOrder = this.rekRepository.getQueueOrders(user.getUserId());
        this.rekRepository.addToQueue(user.getUserId(), rekId, queueOrder.size());
        return this.rekRepository.getQueue(user.getUserId());
    }
}
