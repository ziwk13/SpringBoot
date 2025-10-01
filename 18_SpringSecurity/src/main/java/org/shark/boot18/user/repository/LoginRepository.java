package org.shark.boot18.user.repository;

import org.shark.boot18.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<User,Integer> {
    // username을 이용한 조회
    Optional<User> findByUsername(String username);
}
