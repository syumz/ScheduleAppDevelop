package org.example.scheduleappdevelop.user.repository;

import org.example.scheduleappdevelop.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findMemberByUsername(String username);

    default User findMemberByUsernameOrElseThrow(String username) {
            return findMemberByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exists username: " + username));

    }


    default User findByIdOrElseThrow(Long id) {
        return findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exists id: " + id));
    }

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
