package org.example.scheduleappdevelop.user.repository;

import org.example.scheduleappdevelop.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findMemberByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
