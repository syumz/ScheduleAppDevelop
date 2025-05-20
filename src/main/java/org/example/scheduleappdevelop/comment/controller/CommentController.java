package org.example.scheduleappdevelop.comment.controller;

import lombok.RequiredArgsConstructor;
import org.example.scheduleappdevelop.comment.dto.CommentRequestDto;
import org.example.scheduleappdevelop.comment.dto.CommentResponseDto;
import org.example.scheduleappdevelop.comment.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{id}") // 댓글 생성
    public ResponseEntity<CommentResponseDto> saveComment(
            @PathVariable Long id,
            @RequestBody CommentRequestDto requestDto){

        return new ResponseEntity<>(commentService.saveComment(id, requestDto.getUsername(), requestDto.getComment()), HttpStatus.CREATED);
    }

    @GetMapping("/{id}") // 특정 게시물에 달린 댓글 조회
    public ResponseEntity<List<CommentResponseDto>> findCommentBySchedule(@PathVariable Long id){

        return new ResponseEntity<>(commentService.findCommentByScheduleId(id), HttpStatus.OK);
    }
}
