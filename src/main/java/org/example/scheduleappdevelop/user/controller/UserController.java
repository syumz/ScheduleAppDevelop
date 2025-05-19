package org.example.scheduleappdevelop.user.controller;

import lombok.RequiredArgsConstructor;
import org.example.scheduleappdevelop.user.dto.SignUpRequestDto;
import org.example.scheduleappdevelop.user.dto.SignUpResponseDto;
import org.example.scheduleappdevelop.user.dto.UpdatePasswordRequestDto;
import org.example.scheduleappdevelop.user.dto.UserResponseDto;
import org.example.scheduleappdevelop.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup") // 유저 회원가입
    public ResponseEntity<SignUpResponseDto> signup(
            @RequestBody SignUpRequestDto requestDto){

        SignUpResponseDto signUpResponseDto =
                userService.signUp(
                        requestDto.getUsername(),
                        requestDto.getEmail(),
                        requestDto.getPassword()

                );
        return new ResponseEntity<>(signUpResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}") // 유저 아이디 조회
    public ResponseEntity<UserResponseDto> findById(
            @PathVariable Long id){

        UserResponseDto userResponseDto = userService.findById(id);

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}") // 유저 비밀번호 수정
    public ResponseEntity<Void> updatePassword(
            @PathVariable Long id,
            @RequestBody UpdatePasswordRequestDto requestDto){
        userService.updatePassword(id, requestDto.getOldPassword(), requestDto.getNewPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}") // 유저 삭제
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
