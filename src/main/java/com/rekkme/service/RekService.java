package com.rekkme.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.rekkme.data.dtos.CommentDto;
import com.rekkme.data.dtos.RekRequestDto;
import com.rekkme.data.dtos.RekResultRequestDto;
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
import com.rekkme.exception.RecordNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RekService {

    @Autowired
    private RekRepository rekRepository;

    @Autowired
    private RekResultRepository rekResultRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;
    
    @Transactional(propagation = Propagation.REQUIRES_NEW,
        rollbackFor = Exception.class)
    public RekResult addRekResult(User user, Long rekId, RekResultRequestDto rekResultReq) {
        Rek rek = this.rekRepository.getById(rekId);
        if (rek == null) {
            throw new RecordNotFoundException("rek", rekId);
        }
        System.out.println("found rek");
        RekResult rekRes = new RekResult();
        rekRes.setKo(false);
        rekRes.setResult(rekResultReq.getResult());
        rekRes.setCreatedOn(LocalDateTime.now());
        RekResult rekResult = this.rekResultRepository.save(rekRes);
        System.out.println("saved result");
        rek.setRekResult(rekResult);
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

    @Transactional(propagation = Propagation.REQUIRES_NEW,
        rollbackFor = Exception.class)
    public Comment addComment(CommentDto commentDto, User user, Long rekId) {
        Rek rek = this.rekRepository.getById(rekId);
        if (rek == null) {
            throw new RecordNotFoundException("rek", rekId);
        }
        Comment comment = new Comment();
        comment.setCreatedOn(LocalDateTime.now());
        comment.setMessage(comment.getMessage());
        comment.setUser(user);
        comment.setRek(rek);
        return this.commentRepository.save(comment);
    }
}
