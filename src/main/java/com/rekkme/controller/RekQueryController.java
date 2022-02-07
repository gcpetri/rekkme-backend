package com.rekkme.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.rekkme.data.entity.Category;
import com.rekkme.data.entity.Rek;
import com.rekkme.data.entity.User;
import com.rekkme.data.repository.CategoryRepository;
import com.rekkme.data.repository.RekRepository;
import com.rekkme.dtos.entity.RekDto;
import com.rekkme.exception.RecordNotFoundException;
import com.rekkme.util.ConverterUtil;

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
    private final ConverterUtil converterUtil;
    
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
                .map(r -> this.converterUtil.convertRekToDto(r, user))
                .collect(Collectors.toList());
        }
        return this.rekRepository.findLatestReks()
                .stream()
                .map(r -> this.converterUtil.convertRekToDto(r, user))
                .collect(Collectors.toList());
    }

    @GetMapping(value="", params="id")
    public RekDto getRekById(@RequestAttribute("user") User user, @RequestParam UUID id) {
        Rek rek = this.rekRepository.findById(id)
            .orElseThrow(() -> new RecordNotFoundException("Reks", id));
        return this.converterUtil.convertRekToDto(rek, user);
    }
}
