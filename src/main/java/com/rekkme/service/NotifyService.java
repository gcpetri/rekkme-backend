package com.rekkme.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.UUID;

import com.rekkme.data.repository.EmitterRepository;
import com.rekkme.dtos.responses.NotificationDto;

import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class NotifyService {

    @Value("${app.api.basepath}")
    private String basePath;

    @Value("${app.api.notify.connection.timeout}")
    private Long notifyTimeout;

    private final EmitterRepository emitterRepository;

    public SseEmitter createEmitter(UUID userId) {
        SseEmitter emitter = new SseEmitter(notifyTimeout);
        emitter.onCompletion(() -> emitterRepository.remove(userId));
        emitter.onTimeout(() -> emitterRepository.remove(userId));
        emitter.onError(e -> {
            System.out.println("Create SseEmitter exception" + e.getMessage());
            emitterRepository.remove(userId);
        });
        emitterRepository.addOrReplaceEmitter(userId, emitter);
        return emitter;
    }

    public void sendNotification(UUID userId, NotificationDto notification) {
        SseEmitter sseEmitter = emitterRepository.find(userId);
        if (sseEmitter == null) {
            System.out.println("User emitter not found");
        }
        try {
            System.out.println("Sending message");
            sseEmitter.send(this.buildEvent(notification));

        } catch (IOException | IllegalStateException e) {
            System.out.println("Error sending notification");
            emitterRepository.remove(userId);
        }
    }

    private SseEmitter.SseEventBuilder buildEvent(NotificationDto notification) {
        return SseEmitter.event()
            .id(UUID.randomUUID().toString())
            .name("Rekkme")
            .data(notification);
    }
}
