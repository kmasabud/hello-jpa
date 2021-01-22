package com.earth.hellojpa.repository;

import com.earth.hellojpa.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Integer> {
    List<UserInfoEntity> findByTell(String tell); //-> SELECT * FROM user_info WHERE tell = ?

    UserInfoEntity findById(int id);
}


//autowire -> @Component, @Service and @Repository