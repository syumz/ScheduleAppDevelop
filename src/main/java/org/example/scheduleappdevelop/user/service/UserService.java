package org.example.scheduleappdevelop.user.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduleappdevelop.user.dto.SignUpResponseDto;
import org.example.scheduleappdevelop.user.dto.UserResponseDto;
import org.example.scheduleappdevelop.user.entity.User;
import org.example.scheduleappdevelop.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public SignUpResponseDto signUp(String username, String email, String password) {

        User user = new User(username, email, password);

        User savedUser = userRepository.save(user);

        return new SignUpResponseDto(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
    }

    public UserResponseDto findById(Long id) {

        Optional<User> optionalUser = userRepository.findById(id);

        if(optionalUser.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "아이디가 존재하지 않습니다. :" +id);
        }

        User findUser = optionalUser.get();
        return new UserResponseDto(findUser.getUsername(), findUser.getEmail());
    }

    @Transactional
    public void updatePassword(Long id, String oldPassword, String newPassword) {

        User findUser = userRepository.findByIdOrElseThrow(id);

        if(!findUser.getPassword().equals(oldPassword)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");

        }

        findUser.updatePassword(newPassword);
    }

    public void delete(Long id) {
        User findUser = userRepository.findByIdOrElseThrow(id);

        userRepository.delete(findUser);
    }
}
