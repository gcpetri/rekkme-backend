package com.rekkme.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.rekkme.data.entity.Category;
import com.rekkme.data.entity.Comment;
import com.rekkme.data.entity.Rek;
import com.rekkme.data.entity.RekResult;
import com.rekkme.data.entity.User;
import com.rekkme.data.repository.CategoryRepository;
import com.rekkme.data.repository.RekRepository;
import com.rekkme.data.repository.RekResultRepository;
import com.rekkme.data.repository.UserRepository;
import com.rekkme.dtos.entity.CommentDto;
import com.rekkme.dtos.entity.RekDto;
import com.rekkme.dtos.entity.RekResultDto;
import com.rekkme.dtos.entity.UserDto;
import com.rekkme.dtos.forms.RekRequestDto;
import com.rekkme.dtos.forms.RekResultRequestDto;
import com.rekkme.dtos.responses.ResultNotificationDto;
import com.rekkme.exception.RecordNotFoundException;
import com.rekkme.service.RekService;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${app.api.basepath}/reks")
@RequiredArgsConstructor
public class RekController {
    
    private final RekRepository rekRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;
    private final RekService rekService;
    private final RekResultRepository rekResultRepository;
    private final UserRepository userRepository;

    // get reks to this user
    @GetMapping(value={"", "/", "/to"})
    public List<RekDto> getReks(@RequestAttribute("user") User user) {
        return this.rekRepository.getReksTo(user.getUserId())
            .stream()
            .map(r -> convertRekToDto(r, user))
            .collect(Collectors.toList());
    }

    // get reks from this user
    @GetMapping("/from")
    public List<RekDto> getReksFrom(@RequestAttribute("user") User user) {
        return this.rekRepository.getReksFrom(user.getUserId())
            .stream()
            .map(r -> convertRekToDto(r, user))
            .collect(Collectors.toList());
    }

    /*
    @GetMapping("/queue")
    public List<RekDto> getRekQueue(@RequestAttribute("user") User user) {
        return this.rekRepository.getQueue(user.getUserId())
            .stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    } */

    // get the reks associated with this user and their friends
    @GetMapping("/activity")
    public List<RekDto> getActivity(@RequestAttribute("user") User user) {
        List<UUID> userIds = new ArrayList<>();
        userIds.add(user.getUserId());
        for (User friend : user.getFriends()) {
            userIds.add(friend.getUserId());
        }
        return this.rekRepository.getActivity(userIds)
            .stream()
            .map(r -> convertRekToDto(r, user))
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RekDto getRekById(@RequestAttribute("user") User user, @PathVariable UUID id) {
        List<Rek> reks = this.rekRepository.getReksTo(user.getUserId());
        for (Rek rek : reks) {
            if (rek.getRekId().equals(id)) {
                return convertRekToDto(rek, user);
            }
        }
        throw new RecordNotFoundException("Rek", id);
    }

    @PostMapping("/save")
    public List<RekDto> addReks(@RequestAttribute("user") User user,
        @RequestBody RekRequestDto rekReq) {
        Category cat = this.categoryRepository.findByNameIgnoreCase(rekReq.getCategory());
        if (cat == null) {
            throw new RecordNotFoundException("Category", rekReq.getCategory());
        }
        
        List<Rek> newSavedReks = this.rekService.addReks(rekReq, user, cat);
        return newSavedReks.stream()
            .map(r -> convertRekToDto(r, user))
            .collect(Collectors.toList());
    }

    @PostMapping("/{rekId}/result")
    public RekResultDto addRekResult(@RequestAttribute("user") User user, @PathVariable UUID rekId,
        @RequestBody RekResultRequestDto rekResultReq) {
        RekResult rekResult = this.rekService.addRekResult(user, rekId, rekResultReq);
        return convertRekResultToDto(rekResult);
    }

    @GetMapping("/{rekId}/result/{resultId}")
    public RekResultDto getRekResult(@RequestAttribute("user") User user, @PathVariable UUID rekId,
        @PathVariable Long resultId) {
        Rek rek = this.rekRepository.getById(rekId);
        if (rek == null) {
            throw new RecordNotFoundException("rek", rekId);
        }
        return convertRekResultToDto(rek.getRekResult());
    }

    /*
    @PostMapping("/queue/add/{rekId}")
    public List<RekDto> addToQueue(@RequestAttribute("user") User user, @PathVariable UUID rekId) {
        return this.rekService.addQueue(user, rekId).stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    } */

    @PostMapping("/{rekId}/comment")
    public CommentDto addComment(@RequestAttribute("user") User user, @PathVariable UUID rekId,
        @RequestBody CommentDto commentDto) {
        Comment newComment = rekService.addComment(commentDto, user, rekId);
        return convertCommentToDto(newComment);
    }

    @PostMapping("/{rekId}/like")
    public ResponseEntity<Object> toggleLike(@RequestAttribute("user") User user, @PathVariable UUID rekId) {
        this.rekService.toggleLike(user, rekId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/results/new")
    public List<ResultNotificationDto> getNewRekResults(@RequestAttribute("user") User user) {

        List<ResultNotificationDto> resp = new ArrayList<>();
        LocalDateTime lastLogin = user.getLastLogin();
        List<RekResult> results = this.rekResultRepository.findAll();
        for (RekResult res : results) {
            if (res.getCreatedOn().isAfter(lastLogin)) {
                ResultNotificationDto note = new ResultNotificationDto();
                note.setCreatedOn(res.getCreatedOn());
                note.setResult(res.getResult());
                note.setTitle(res.getRek().getTitle());
                note.setWager(res.getRek().getWager());
                note.setDiff(res.getResult() - res.getRek().getWager());
                note.setToUser(convertToUserDto(res.getRek().getToUser()));
                resp.add(note);
            }
        }
        user.setLastLogin(LocalDateTime.now());
        this.userRepository.save(user);
        return resp;
    }

    // utils

    private RekDto convertRekToDto(Rek rek, User user) {
        RekDto rekDto = modelMapper.map(rek, RekDto.class);
        if (user == null) {
            return rekDto;
        }
        if (this.rekRepository.getLike(user.getUserId(), rek.getRekId()) > 0) {
            rekDto.setLiked(true);
        }
        return rekDto;
    }

    private RekResultDto convertRekResultToDto(RekResult rek) {
        RekResultDto rekDto = modelMapper.map(rek, RekResultDto.class);
        return rekDto;
    }

    private CommentDto convertCommentToDto(Comment comment) {
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
        return commentDto;
    }

    private UserDto convertToUserDto(User user) {
        UserDto friendDto = modelMapper.map(user, UserDto.class);
        return friendDto;
    }
}
