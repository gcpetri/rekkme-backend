package com.rekkme.data.repository;

import java.util.List;
import java.util.UUID;

import com.rekkme.data.entity.Rek;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RekRepository extends JpaRepository<Rek, UUID> {

    @Query(value = "SELECT * FROM REKS r WHERE r.REK_ID = ?1", nativeQuery = true)
    Rek getRekById(UUID rekId);

    @Query(value = "SELECT * FROM REKS r WHERE r.TO_USER_ID = ?1", nativeQuery = true)
    List<Rek> findReksTo(UUID userId);

    @Query(value = "SELECT * FROM REKS r WHERE r.FROM_USER_ID = ?1", nativeQuery = true)
    List<Rek> findReksFrom(UUID userId);

    @Query(value = "SELECT * FROM REKS r WHERE r.TO_USER_ID IN ?1 OR r.FROM_USER_ID IN ?1 ORDER BY r.CREATED_ON DESC", nativeQuery = true)
    List<Rek> findCommunityReks(List<UUID> userIds);

    @Query(value = "SELECT * FROM REKS WHERE CATEGORY_ID = ?1 ORDER BY CREATED_ON DESC LIMIT 50", nativeQuery = true)
    List<Rek> findByCategory(UUID categoryId);

    @Query(value = "SELECT * FROM REKS ORDER BY CREATED_ON DESC LIMIT 75", nativeQuery = true)
    List<Rek> findLatestReks();

    @Modifying
    @Query(value = "INSERT INTO LIKES (USER_ID, REK_ID) VALUES (?1, ?2)", nativeQuery = true)
    void addLike(UUID userId, UUID rekId);

    @Modifying
    @Query(value = "DELETE FROM LIKES WHERE USER_ID = ?1 AND REK_ID = ?2", nativeQuery = true)
    void deleteLike(UUID userId, UUID rekId);

    @Query(value = "SELECT COUNT(*) FROM LIKES l WHERE USER_ID = ?1 AND REK_ID = ?2", nativeQuery = true)
    int existsLike(UUID userId, UUID rekId);

    @Query(value = "SELECT COUNT(l) FROM LIKES l WHERE l.REK_ID = ?1", nativeQuery = true)
    int getNumLikes(UUID rekId);

    @Query(value = "SELECT r.* FROM REKS r INNER JOIN USER_REK_QUEUES q ON q.REK_ID = r.REK_ID WHERE q.USER_ID = ?1 ORDER BY q.QUEUE_ORDER ASC", nativeQuery = true)
    List<Rek> getQueueByUserId(UUID userId);

    @Query(value = "SELECT r.TOP FROM REKS r INNER JOIN USER_REK_QUEUES q ON q.REK_ID = r.REK_ID WHERE q.USER_ID = ?1 ORDER BY q.QUEUE_ORDER ASC", nativeQuery = true)
    Rek getQueueTop(UUID userId);
}
