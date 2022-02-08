package com.rekkme.data.repository;

import java.util.List;
import java.util.UUID;

import com.rekkme.data.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, UUID> { 

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
    int existsFriendRequest(UUID toUserId, UUID fromUserId);

    @Query(value = "SELECT * FROM USERS u WHERE LOWER(u.USERNAME) SIMILAR TO :qStr OR LOWER(u.FIRST_NAME) SIMILAR TO :qStr OR LOWER(u.LAST_NAME) SIMILAR TO :qStr", nativeQuery = true)
    List<User> findUserLike(@Param("qStr") String qStr);

    @Query(value = "SELECT * FROM USERS ORDER BY REK_POINTS DESC LIMIT 50", nativeQuery = true)
    List<User> findTopUsers();

    @Query(value = "SELECT * FROM USER_FRIENDS LEFT JOIN USERS ON USERS.USER_ID = USER_FRIENDS.USER_ID WHERE USER_FRIENDS.FRIEND_ID = ?1", nativeQuery = true)
    List<User> findFollowers(UUID userId);

    @Query(value = "SELECT * FROM USER_FRIENDS INNER JOIN USERS ON USERS.USER_ID = USER_FRIENDS.FRIEND_ID WHERE USER_FRIENDS.USER_ID = ?1", nativeQuery = true)
    List<User> findFollowing(UUID userId);

    @Query(value = "SELECT * FROM USER_FRIENDS f INNER JOIN USER_FRIENDS u ON u.USER_ID = f.FRIEND_ID INNER JOIN USERS s ON s.USER_ID = f.FRIEND_ID WHERE f.USER_ID = ?1", nativeQuery = true)
    List<User> findFriends(UUID userId);

    @Query(value = "SELECT COUNT(*) FROM USER_FRIENDS WHERE FRIEND_ID = ?1", nativeQuery = true)
    int findNumFollowers(UUID userId);

    @Query(value = "SELECT COUNT(*) FROM USER_FRIENDS WHERE USER_ID = ?1", nativeQuery = true)
    int findNumFollowing(UUID userId);

    @Query(value = "SELECT COUNT(*) FROM USER_FRIENDS f INNER JOIN USER_FRIENDS u ON u.USER_ID = f.FRIEND_ID WHERE f.USER_ID = ?1 AND u.FRIEND_ID = ?1", nativeQuery = true)
    int findNumFriends(UUID userId);

    @Query(value = "SELECT COUNT(*) FROM USER_FRIENDS f WHERE USER_ID = ?1 AND FRIEND_ID = ?2", nativeQuery = true)
    int existsFriend(UUID userId, UUID friendId);
}