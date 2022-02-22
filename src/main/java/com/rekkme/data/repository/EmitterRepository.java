package com.rekkme.data.repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public class EmitterRepository {
    
    private Map<UUID, SseEmitter> userEmitterMap = new ConcurrentHashMap<>();

    public void addOrReplaceEmitter(UUID userId, SseEmitter emitter) {
        userEmitterMap.put(userId, emitter);
    }

    public void remove(UUID userId) {
        if (userEmitterMap != null && userEmitterMap.containsKey(userId)) {
            userEmitterMap.remove(userId);
        }
    }

    public SseEmitter find(UUID userId) {
        return userEmitterMap.get(userId);
    }
}
