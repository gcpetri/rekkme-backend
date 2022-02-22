package com.rekkme.controller;

import com.rekkme.data.entity.User;
import com.rekkme.service.NotifyService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${app.api.basepath}/notify")
@RequiredArgsConstructor
public class NotifyController {

    private final NotifyService notifyService;

    @GetMapping("/handshake")
    public SseEmitter subscribe(@RequestAttribute("user") User user) {
        System.out.println("Subscribed to SSE events");
        return notifyService.createEmitter(user.getUserId());
    }
}
