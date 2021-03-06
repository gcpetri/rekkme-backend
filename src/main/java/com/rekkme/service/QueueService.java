package com.rekkme.service;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import com.rekkme.data.entity.Rek;
import com.rekkme.data.entity.User;
import com.rekkme.data.entity.UserRekQueue;
import com.rekkme.data.repository.QueueRepository;
import com.rekkme.data.repository.RekRepository;
import com.rekkme.data.repository.UserRepository;
import com.rekkme.dtos.responses.NotificationDto;
import com.rekkme.exception.RecordNotFoundException;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class QueueService {
    
    private final RekRepository rekRepository;
    private final QueueRepository queueRepository;
    private final UserRepository userRepository;
    private final NotifyService notifyService;

    public Rek push(UUID rekId, User user) {
        Rek r = this.rekRepository.findById(rekId)
            .orElseThrow(() -> new RecordNotFoundException("Rek", rekId));
        UserRekQueue oldQueue = this.queueRepository.findByRekId(user.getUserId(), rekId);
        if (oldQueue != null) {
            return oldQueue.getRek();
        }
        float order = this.queueRepository.findHighestOrder(user.getUserId());
        if (order > Float.MAX_VALUE - 100.0f) { // they are obviously a bot
            this.clearQueue(user);
            return r;
        }
        order += 100.0f;
        UserRekQueue q = new UserRekQueue();
        q.setRek(r);
        q.setUser(user);
        q.setQueueOrder(order);
        this.queueRepository.save(q);
        return r;
    }

    public void remove(UUID rekId, User user) {
        UserRekQueue q = this.queueRepository.findByRekId(user.getUserId(), rekId);
        if (q == null) {
            return;
        }
        this.queueRepository.delete(q);
    }

    public void move(UUID rekId, User user, int pos) {

        UserRekQueue q = this.queueRepository.findUserRekQueueByUserIdAndRekId(user.getUserId(), rekId);
        if (q == null) {
            System.out.println("Rek is not in the queue");
            return;
        }

        List<Float> queueOrders = this.queueRepository.findOrdersByUserId(user.getUserId());

        if (queueOrders.size() <= 1) {
            System.out.println("Queue length is only 0");
            return;
        }
    
        if (pos == 0) {
            float order = queueOrders.get(0);
            if (order == q.getQueueOrder()) {
                System.out.println("Already in that position");
                return;
            }
            if (order < Float.MIN_VALUE + 5.0f) { // they are obviously a bot
                this.clearQueue(user);
                return;
            }
            this.queueRepository.updateOrder(order - 5.0f, q.getUserRekQueueId());
            return;
        }

        if (pos < 1 || pos > queueOrders.size() - 1) {
            System.out.println("Position out of bounds, queue size: " + Integer.toString(queueOrders.size()));
            return;
        }

        float afterOrder = queueOrders.get(pos);
        if (afterOrder == q.getQueueOrder()) {
            System.out.println("Already in that position");
            return;
        }

        float beforeOrder = queueOrders.get(pos - 1);
        this.queueRepository.updateOrder(beforeOrder + ((afterOrder - beforeOrder) / 2), q.getUserRekQueueId());
        System.out.println("moved");
    }

    public void clearQueue(User user) {
        this.queueRepository.deleteUserQueue(user.getUserId());
    }

    // Notifications 

    // cron = "0 0 9 15 * ?"
    @Scheduled(fixedRate = 5000)
	public void reportCurrentTime() {
        for (User u : this.userRepository.findAll()) {
            Rek rek = this.rekRepository.getQueueTop(u.getUserId());
            if (rek != null) {
                this.notifyService.sendNotification(u.getUserId(), this.rekQueue(rek.getTitle()));
            }
        }
        System.out.println("Queue Notification Sent");
	}

    private NotificationDto rekQueue(String title) {
        NotificationDto notification = new NotificationDto();
        notification.setUsername("Rekkme");
        notification.setMessage("Remember to try your Rekk: " + title + " today!");
        notification.setAvatar(null);
        return notification;
    }
}
