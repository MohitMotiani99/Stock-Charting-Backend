package com.StockCharting.User.repository;

import com.StockCharting.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUserId(String userId);

    Optional<User> findByUsername(String username);

    void deleteByUserId(String userId);

    Optional<User> findByUsernameAndPassword(String username, String password);
}
