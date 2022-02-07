package com.rekkme.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;

import com.rekkme.data.entity.Category;
import com.rekkme.data.entity.Comment;
import com.rekkme.data.entity.Rek;
import com.rekkme.data.entity.RekResult;
import com.rekkme.data.entity.User;
import com.rekkme.data.repository.CategoryRepository;
import com.rekkme.data.repository.RekRepository;
import com.rekkme.dtos.entity.CommentDto;
import com.rekkme.dtos.entity.RekDto;
import com.rekkme.dtos.entity.RekResultDto;
import com.rekkme.dtos.forms.RekRequestDto;
import com.rekkme.dtos.forms.RekResultRequestDto;
import com.rekkme.exception.RecordNotFoundException;
import com.rekkme.service.RekService;
import com.rekkme.util.ConverterUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${app.api.basepath}/reks")
@RequiredArgsConstructor
public class RekController {

    private final RekRepository rekRepository;
    private final ConverterUtil converterUtil;
    private final CategoryRepository categoryRepository;
    private final RekService rekService;

    // get reks to this user
    @GetMapping(value={"", "/", "/to"})
    public List<RekDto> getReks(@RequestAttribute("user") User user) {
        return this.rekRepository.findReksTo(user.getUserId())
            .stream()
            .map(r -> this.converterUtil.convertRekToDto(r, user))
            .collect(Collectors.toList());
    }

    // get reks from this user
    @GetMapping("/from")
    public List<RekDto> getReksFrom(@RequestAttribute("user") User user) {
        return this.rekRepository.findReksFrom(user.getUserId())
            .stream()
            .map(r -> this.converterUtil.convertRekToDto(r, user))
            .collect(Collectors.toList());
    }

    // get the reks associated with this user and their friends
    @GetMapping("/community")
    public List<RekDto> getCommunity(@RequestAttribute("user") User user) {
        List<UUID> userIds = new ArrayList<>();
        for (User friend : user.getFriends()) {
            userIds.add(friend.getUserId());
        }
        return this.rekRepository.findCommunityReks(userIds)
            .stream()
            .map(r -> this.converterUtil.convertRekToDto(r, user))
            .collect(Collectors.toList());
    }

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> addReks(@RequestAttribute("user") User user,
        @RequestBody RekRequestDto rekReq) {
        Category cat = this.categoryRepository.findByNameIgnoreCase(rekReq.getCategory());
        if (cat == null) {
            throw new RecordNotFoundException("Category", rekReq.getCategory());
        }
        
        List<Rek> newSavedReks = this.rekService.addReks(rekReq, user, cat);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSavedReks.stream()
            .map(r -> this.converterUtil.convertRekToDto(r, user))
            .collect(Collectors.toList()));
    }

    @PostMapping("/{rekId}/result")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> addRekResult(@RequestAttribute("user") User user, @PathVariable UUID rekId,
        @RequestBody RekResultRequestDto rekResultReq) {
        RekResult rekResult = this.rekService.addRekResult(user, rekId, rekResultReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.converterUtil.convertRekResultToDto(rekResult));
    }

    @GetMapping("/{rekId}/result")
    public RekResultDto getRekResult(@RequestAttribute("user") User user, @PathVariable UUID rekId) {
        Rek rek = this.rekRepository.getById(rekId);
        if (rek == null) {
            throw new RecordNotFoundException("rek", rekId);
        }
        return this.converterUtil.convertRekResultToDto(rek.getRekResult());
    }

    @PostMapping("/{rekId}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> addComment(@RequestAttribute("user") User user, @PathVariable UUID rekId,
        @RequestBody CommentDto commentDto) {
        Comment newComment = rekService.addComment(commentDto, user, rekId);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(this.converterUtil.convertCommentToDto(newComment));
    }

    @PostMapping("/{rekId}/like")
    public ResponseEntity<Object> toggleLike(@RequestAttribute("user") User user, @PathVariable UUID rekId) {
        this.rekService.toggleLike(user, rekId);
        return ResponseEntity.ok().build();
    }
}
