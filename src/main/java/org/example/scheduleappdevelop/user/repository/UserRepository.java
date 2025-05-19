package org.example.scheduleappdevelop.user.repository;

import org.example.scheduleappdevelop.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
