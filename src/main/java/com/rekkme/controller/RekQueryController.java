package com.rekkme.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.rekkme.data.entity.Category;
import com.rekkme.data.entity.Rek;
import com.rekkme.data.entity.User;
import com.rekkme.data.repository.CategoryRepository;
import com.rekkme.data.repository.RekRepository;
import com.rekkme.dtos.entity.RekDto;
import com.rekkme.exception.RecordNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${app.api.basepath}/explore/reks")
@RequiredArgsConstructor
public class RekQueryController {

    private final RekRepository rekRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    
    @GetMapping("/latest")
    public List<RekDto> getLatestReks(@RequestAttribute(value="user", required = false) User user,
        @RequestParam(required = false) String category) {
        if (category != null) {
            Category cat = this.categoryRepository.findByNameIgnoreCase(category);
            if (cat == null) {
                throw new RecordNotFoundException("Category", category);
            }
            return this.rekRepository.findByCategory(cat.getCategoryId())
                .stream()
                .map(r -> convertRekToDto(r, user))
                .collect(Collectors.toList());
        }
        return this.rekRepository.findLatestReks()
                .stream()
                .map(r -> convertRekToDto(r, user))
                .collect(Collectors.toList());
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
}
