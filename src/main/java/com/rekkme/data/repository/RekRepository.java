package com.rekkme.data.repository;

import java.util.List;

import com.rekkme.data.entity.Rek;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RekRepository extends JpaRepository<Rek, Long> {

    @Query(value = "SELECT * FROM REKS r WHERE r.REK_ID = ?1", nativeQuery = true)
    Rek getRekById(Long rekId);

    @Query(value = "SELECT * FROM REKS r WHERE r.TO_USER_ID = ?1", nativeQuery = true)
    List<Rek> getReksTo(Long userId);

    @Query(value = "SELECT * FROM REKS LEFT JOIN USER_REK_QUEUES ON REKS.REK_ID = USER_REK_QUEUES.REK_ID WHERE USER_REK_QUEUES.USER_ID = ?1 ORDER BY USER_REK_QUEUES.QUEUE_ORDER ASC", nativeQuery = true)
    List<Rek> getQueue(Long userId);

    @Query(value = "SELECT QUEUE_ORDER FROM USER_REK_QUEUES q WHERE q.USER_ID = ?1 ORDER BY q.QUEUE_ORDER ASC", nativeQuery = true)
    List<Long> getQueueOrders(Long userId);

    @Modifying
    @Query(value = "INSERT INTO USER_REK_QUEUES (USER_ID, REK_ID, QUEUE_ORDER) VALUES (?1, ?2, ?3)", nativeQuery = true)
    void addToQueue(Long userId, Long rekId, int queueOrder);
}
