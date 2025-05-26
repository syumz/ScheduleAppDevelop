package org.example.scheduleappdevelop.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.scheduleappdevelop.common.Const;
import org.example.scheduleappdevelop.config.PasswordEncoder;
import org.example.scheduleappdevelop.user.dto.LoginResponseDto;
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
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignUpResponseDto signUp(String username, String email, String password) {

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        String encodePassword = passwordEncoder.encode(password);

        User user = new User(username, email, encodePassword);

        User savedUser = userRepository.save(user);

        return new SignUpResponseDto(savedUser.getId(), savedUser.getUsername(), savedUser.getEmail());
    }

    public UserResponseDto findById(Long id) {

        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "아이디가 존재하지 않습니다.");
        }

        User findUser = optionalUser.get();
        return new UserResponseDto(findUser.getUsername(), findUser.getEmail());
    }

    @Transactional
    public void updatePassword(Long id, String oldPassword, String newPassword) {

        User findUser = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(oldPassword, findUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");

        }

        String updateNewPassword = passwordEncoder.encode(newPassword);

        findUser.updatePassword(updateNewPassword);
    }

    @Transactional
    public void delete(Long id) {
        User findUser = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다."));

        userRepository.delete(findUser);
    }


    public LoginResponseDto login(String email, String password, HttpServletRequest request) {

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일에 맞는 회원정보가 없습니다.");
        }

        User login = optionalUser.get();

        if (!passwordEncoder.matches(password, login.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호에 맞는 회원정보가 없습니다.");
        }

        HttpSession session = request.getSession();
        System.out.println("로그인되었습니다.: " + session.getId());
        session.setAttribute(Const.LOGIN_USER, login);

        return new LoginResponseDto(login.getId());
    }


    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        // 세션이 null 이 아니면 (로그인인 경우)
        if (session != null) {
            System.out.println("로그아웃되었습니다.: " + session.getId());
            session.invalidate(); // 해당 세션(데이터)을 삭제한다.
        }
    }
}
