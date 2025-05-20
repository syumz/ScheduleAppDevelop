package org.example.scheduleappdevelop.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduleappdevelop.comment.dto.CommentResponseDto;
import org.example.scheduleappdevelop.comment.entity.Comment;
import org.example.scheduleappdevelop.comment.repository.CommentRepository;
import org.example.scheduleappdevelop.schedule.entity.Schedule;
import org.example.scheduleappdevelop.schedule.repository.ScheduleRepository;
import org.example.scheduleappdevelop.user.entity.User;
import org.example.scheduleappdevelop.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentResponseDto saveComment(Long id, String username, String comment) {

        // 스케줄 id 가 존재하는지 확인
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        // 유저가 존재하는지 확인
        User findUser = userRepository.findMemberByUsernameOrElseThrow(username);

        Comment comment1 = new Comment(comment);
        comment1.setSchedule(findSchedule);
        comment1.setUser(findUser);


        Comment savedComment = commentRepository.save(comment1);

        return new CommentResponseDto(savedComment.getId(), username, savedComment.getComment());
    }

    public List<CommentResponseDto> findCommentByScheduleId(Long id) {

        // 스케줄 id가 존재하는지 확인
        scheduleRepository.findByIdOrElseThrow(id);

        return commentRepository.findByScheduleId(id)
                .stream()
                .map(CommentResponseDto::toDto)
                .toList();
    }
}
