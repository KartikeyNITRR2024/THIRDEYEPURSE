package com.thirdeye.purse.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.thirdeye.purse.entity.UserInfo;

@Repository
public interface UserInfoRepo extends JpaRepository<UserInfo, Long> {
	boolean existsByEmailAndPassword(String email, String password);
    Optional<UserInfo> findByEmailAndPassword(String email, String password);
}
