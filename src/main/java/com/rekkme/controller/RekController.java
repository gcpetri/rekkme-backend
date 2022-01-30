package com.rekkme.controller;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

import com.rekkme.data.dtos.RekDto;
import com.rekkme.data.entity.Rek;
import com.rekkme.data.entity.User;
import com.rekkme.data.repository.RekRepository;
import com.rekkme.exception.RecordNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.api.basepath}/reks")
public class RekController {
    
    @Autowired
    private RekRepository rekRepository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value={"", "/", "/list"})
    public List<RekDto> getReks(@RequestAttribute("user") User user) {
        return this.rekRepository.getReksTo(user.getUserId())
            .stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RekDto getRekById(@RequestAttribute("user") User user, @PathVariable Long id) {
        List<Rek> reks = this.rekRepository.getReksTo(user.getUserId());
        for (Rek rek : reks) {
            if (rek.getRekId() == id) {
                return convertToDto(rek);
            }
        }
        throw new RecordNotFoundException("Rek", id);
    }

    // utils

    private RekDto convertToDto(Rek rek) {
        RekDto rekDto = modelMapper.map(rek, RekDto.class);
        return rekDto;
    }

    private Rek convertToEntity(RekDto challengeDto) throws ParseException {
        Rek rek = modelMapper.map(challengeDto, Rek.class);
        return rek;
    }
}
