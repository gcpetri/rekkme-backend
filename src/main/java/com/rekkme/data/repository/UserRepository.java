package com.rekkme.data.repository;

import java.util.List;

import com.rekkme.data.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> { 

    @Query(value = "SELECT FRIEND_ID FROM USER_FRIENDS WHERE USER_ID = ?1", nativeQuery = true)
    List<Long> getFriendIds(Long userId);

    @Query(value = "SELECT * FROM USERS WHERE USER_ID IN ?1", nativeQuery = true)
    List<User> findByIdArray(List<Long> userIds);

    @Modifying
    @Query(value = "INSERT INTO USER_FRIENDS (USER_ID, FRIEND_ID) VALUES (?1, ?2)", nativeQuery = true)
    void addFriend(Long userId, Long friendId);

    @Modifying
    @Query(value = "DELETE FROM USER_FRIENDS WHERE USER_ID = ?1 AND FRIEND_ID = ?2", nativeQuery = true)
    void deleteFriend(Long userId, Long friendId);

    User findByUsername(String username);
}