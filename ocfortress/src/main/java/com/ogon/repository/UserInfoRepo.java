package com.ogon.repository;

import com.ogon.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserInfoRepo extends JpaRepository<UserInfoEntity, Integer> {

    UserInfoEntity findByUserName(String userName);

    List<UserInfoEntity> findByRole(String role);

    @Modifying
    @Transactional
    @Query("UPDATE UserInfoEntity user " +
            "SET user.password = :password, user.updatedDate = :updatedDate " +
            "WHERE user.userName = :userName")
    int updateUserPassword(@Param("userName") String userName,
                           @Param("password") String password,
                           @Param("updatedDate") String updatedDate);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserInfoEntity user " +
            "WHERE user.userName = :userName")
    int deleteUser(@Param("userName") String userName);

    @Query("SELECT user FROM UserInfoEntity user " +
            "WHERE user.userName LIKE CONCAT('%',:userName,'%')")
    List<UserInfoEntity> searchUserContainsText(@Param("userName") String userName);

    @Query("SELECT user FROM UserInfoEntity user " +
            "WHERE user.userName LIKE CONCAT(:userName,'%')")
    List<UserInfoEntity> searchUserStartsWithText(@Param("userName") String userName);
}