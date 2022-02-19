package com.rekkme.data.repository;

import java.util.List;
import java.util.UUID;

import com.rekkme.data.entity.UserRekQueue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface QueueRepository extends JpaRepository<UserRekQueue, UUID> {
    
    @Query(value = "SELECT q.QUEUE_ORDER FROM USER_REK_QUEUES q WHERE q.USER_ID = ?1 ORDER BY q.QUEUE_ORDER ASC", nativeQuery = true)
    List<Float> findOrdersByUserId(UUID userId);

    @Query(value = "SELECT max(q.QUEUE_ORDER) FROM USER_REK_QUEUES q WHERE q.USER_ID = ?1", nativeQuery = true)
    float findHighestOrder(UUID userId);

    @Query(value = "SELECT * FROM USER_REK_QUEUES WHERE USER_ID = ?1 AND REK_ID = ?2", nativeQuery = true)
    UserRekQueue findByRekId(UUID userId, UUID rekId);

    @Modifying
    @Query(value = "UPDATE USER_REK_QUEUES SET QUEUE_ORDER = ?1 WHERE USER_REK_QUEUE_ID = ?2", nativeQuery = true)
    void updateOrder(float queueOrder, UUID userRekQueueId);

    @Query(value = "SELECT * FROM USER_REK_QUEUES WHERE USER_ID = ?1 AND REK_ID = ?2", nativeQuery = true)
    UserRekQueue findUserRekQueueByUserIdAndRekId(UUID userId, UUID rekId);

    @Modifying
    @Query(value = "DELETE FROM USER_REK_QUEUES WHERE USER_ID = ?1", nativeQuery = true)
    void deleteUserQueue(UUID userId);
}
