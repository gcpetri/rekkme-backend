package com.rekkme.controller;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import com.rekkme.data.dtos.CommentDto;
import com.rekkme.data.dtos.RekDto;
import com.rekkme.data.dtos.RekRequestDto;
import com.rekkme.data.dtos.RekResultDto;
import com.rekkme.data.dtos.RekResultRequestDto;
import com.rekkme.data.dtos.ResultNotificationDto;
import com.rekkme.data.dtos.UserDto;
import com.rekkme.data.entity.Category;
import com.rekkme.data.entity.Comment;
import com.rekkme.data.entity.Rek;
import com.rekkme.data.entity.RekResult;
import com.rekkme.data.entity.User;
import com.rekkme.data.repository.CategoryRepository;
import com.rekkme.data.repository.RekRepository;
import com.rekkme.data.repository.RekResultRepository;
import com.rekkme.exception.RecordNotFoundException;
import com.rekkme.service.RekService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.api.basepath}/reks")
public class RekController {
    
    @Autowired
    private RekRepository rekRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RekService rekService;

    @Autowired
    private RekResultRepository rekResultRepository;

    @CrossOrigin
    @GetMapping(value={"", "/", "/list"})
    public List<RekDto> getReks(@RequestAttribute("user") User user, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        return this.rekRepository.getReksTo(user.getUserId())
            .stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public RekDto getRekById(@RequestAttribute("user") User user, @PathVariable Long id, HttpServletResponse response) {
        List<Rek> reks = this.rekRepository.getReksTo(user.getUserId());
        response.setHeader("Access-Control-Allow-Origin", "*");
        for (Rek rek : reks) {
            if (rek.getRekId() == id) {
                return convertToDto(rek);
            }
        }
        throw new RecordNotFoundException("Rek", id);
    }

    @CrossOrigin
    @PostMapping("/save")
    public List<RekDto> addReks(@RequestAttribute("user") User user,
        @RequestBody RekRequestDto rekReq, HttpServletResponse response) {

        Category cat = this.categoryRepository.findByNameIgnoreCase(rekReq.getCategory());
        if (cat == null) {
            throw new RecordNotFoundException("category" + rekReq.getCategory(), 0L);
        }
        response.setHeader("Access-Control-Allow-Origin", "*");
        
        List<Rek> newSavedReks = this.rekService.addReks(rekReq, user, cat);
        return newSavedReks.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    @CrossOrigin
    @PostMapping("/{rekId}/result")
    public RekResultDto addRekResult(@RequestAttribute("user") User user, @PathVariable Long rekId,
        @RequestBody RekResultRequestDto rekResultReq, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        RekResult rekResult = this.rekService.addRekResult(user, rekId, rekResultReq);
        return convertRekResultToDto(rekResult);
    }

    @CrossOrigin
    @GetMapping("/{rekId}/result/{resultId}")
    public RekResultDto getRekResult(@RequestAttribute("user") User user, @PathVariable Long rekId,
        @PathVariable Long resultId, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Rek rek = this.rekRepository.getById(rekId);
        if (rek == null) {
            throw new RecordNotFoundException("rek", rekId);
        }
        return convertRekResultToDto(rek.getRekResult());
    }

    @CrossOrigin
    @PostMapping("/{rekId}/comment")
    public CommentDto addComment(@RequestAttribute("user") User user, @PathVariable Long rekId,
        @RequestBody CommentDto commentDto, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        Comment newComment = rekService.addComment(commentDto, user, rekId);
        return convertCommentToDto(newComment);
    }

    @CrossOrigin
    @PostMapping("/newresults")
    public List<ResultNotificationDto> getNewRekResults(@RequestAttribute("user") User user,
        HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        List<ResultNotificationDto> resp = new ArrayList<>();
        try {
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
                    note.setToUser(convertUserToDto(res.getRek().getToUser()));
                    resp.add(note);
                }
            }
            return resp;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RecordNotFoundException("rek", 0L);
        }
    }

    // utils

    private RekDto convertToDto(Rek rek) {
        RekDto rekDto = modelMapper.map(rek, RekDto.class);
        return rekDto;
    }

    private RekResultDto convertRekResultToDto(RekResult rek) {
        RekResultDto rekDto = modelMapper.map(rek, RekResultDto.class);
        return rekDto;
    }

    private Rek convertToEntity(RekDto challengeDto) throws ParseException {
        Rek rek = modelMapper.map(challengeDto, Rek.class);
        return rek;
    }

    private Comment convertCommentToEntity(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }

    private CommentDto convertCommentToDto(Comment comment) {
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
        return commentDto;
    }

    private UserDto convertUserToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }
}
