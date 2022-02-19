package com.rekkme.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.rekkme.data.entity.User;
import com.rekkme.data.repository.RekRepository;
import com.rekkme.dtos.entity.RekDto;
import com.rekkme.service.QueueService;
import com.rekkme.util.ConverterUtil;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${app.api.basepath}/queue")
@RequiredArgsConstructor
public class QueueController {

    private final ConverterUtil converterUtil;
    private final RekRepository rekRepository;
    private final QueueService queueService;
    
    // get reks to this user's queue
    @GetMapping(value={"", "/", "/to"})
    public List<RekDto> getQueue(@RequestAttribute("user") User user) {
        return this.rekRepository.getQueueByUserId(user.getUserId())
            .stream()
            .map(r -> this.converterUtil.convertRekToDto(r, user))
            .collect(Collectors.toList());
    }

    @PostMapping(value={"/add/{rekId}", "/push/{rekId}", "/new/{rekId}", "/save/{rekId}"})
    public RekDto addToQueue(@RequestAttribute("user") User user,
        @PathVariable UUID rekId) {
        return this.converterUtil.convertRekToDto(this.queueService.push(rekId, user), user);
    }

    @DeleteMapping(value={"/remove/{rekId}", "/pop/{rekId}", "/delete/{rekId}"})
    public void removeFromQueue(@RequestAttribute("user") User user,
        @PathVariable UUID rekId) {
        this.queueService.remove(rekId, user);
    }

    @PutMapping(value={"/move/{rekId}/{pos}"})
    public void moveInQueue(@RequestAttribute("user") User user,
        @PathVariable UUID rekId, @PathVariable Integer pos) {
        this.queueService.move(rekId, user, pos);
    }

    @DeleteMapping(value={"/clear"})
    public void clearQueue(@RequestAttribute("user") User user) {
        this.queueService.clearQueue(user);
    }
}
