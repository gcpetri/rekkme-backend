package com.rekkme.data.repository;

import java.util.List;
import java.util.UUID;

import com.rekkme.data.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, UUID> { 

    @Query(value = "SELECT FRIEND_ID FROM USER_FRIENDS WHERE USER_ID = ?1", nativeQuery = true)
    List<UUID> getFriendIds(UUID userId);

    @Query(value = "SELECT * FROM USERS WHERE USER_ID IN ?1", nativeQuery = true)
    List<User> findByIdArray(List<UUID> userIds);

    @Modifying
    @Query(value = "INSERT INTO USER_FRIENDS (USER_ID, FRIEND_ID) VALUES (?1, ?2)", nativeQuery = true)
    void addFriend(UUID userId, UUID friendId);

    @Modifying
    @Query(value = "DELETE FROM USER_FRIENDS WHERE USER_ID = ?1 AND FRIEND_ID = ?2", nativeQuery = true)
    void deleteFriend(UUID userId, UUID friendId);

    User findByUsername(String username);

    @Query(value = "SELECT * FROM FRIEND_REQUESTS LEFT JOIN USERS ON USERS.USER_ID = FRIEND_REQUESTS.FROM_USER_ID WHERE FRIEND_REQUESTS.TO_USER_ID = ?1", nativeQuery = true)
    List<User> getFriendRequestsTo(UUID toUserId);

    @Query(value = "SELECT * FROM FRIEND_REQUESTS LEFT JOIN USERS ON USERS.USER_ID = FRIEND_REQUESTS.TO_USER_ID WHERE FRIEND_REQUESTS.FROM_USER_ID = ?1", nativeQuery = true)
    List<User> getFriendRequestsFrom(UUID fromUserId);

    @Modifying
    @Query(value = "INSERT INTO FRIEND_REQUESTS (TO_USER_ID, FROM_USER_ID) VALUES (?1, ?2)", nativeQuery = true)
    void addFriendRequest(UUID toUserId, UUID fromUserId);

    @Modifying
    @Query(value = "DELETE FROM FRIEND_REQUESTS WHERE TO_USER_ID = ?1 AND FROM_USER_ID = ?2", nativeQuery = true)
    void deleteFriendRequest(UUID toUserId, UUID fromUserId);

    @Query(value = "SELECT COUNT(*) FROM FRIEND_REQUESTS WHERE TO_USER_ID = ?1 AND FROM_USER_ID = ?2", nativeQuery = true)
    int getFriendRequest(UUID toUserId, UUID fromUserId);

    @Query(value = "SELECT * FROM USERS u WHERE LOWER(u.USERNAME) SIMILAR TO :qStr OR LOWER(u.FIRST_NAME) SIMILAR TO :qStr OR LOWER(u.LAST_NAME) SIMILAR TO :qStr", nativeQuery = true)
    List<User> findUserLike(@Param("qStr") String qStr);

    @Query(value = "SELECT * FROM USERS ORDER BY REK_POINTS DESC LIMIT 50", nativeQuery = true)
    List<User> findTopUsers();

    @Query(value = "SELECT * FROM USER_FRIENDS LEFT JOIN USERS ON USERS.USER_ID = USER_FRIENDS.USER_ID WHERE USER_FRIENDS.FRIEND_ID = ?1", nativeQuery = true)
    List<User> findCrowd(UUID userId);

    @Query(value = "SELECT COUNT(*) FROM USER_FRIENDS WHERE FRIEND_ID = ?1", nativeQuery = true)
    int findNumCrowd(UUID userId);

    @Query(value = "SELECT COUNT(*) FROM USER_FRIENDS WHERE USER_ID = ?1 AND FRIEND_ID = ?2", nativeQuery = true)
    int getFriend(UUID userId, UUID friendId);
}