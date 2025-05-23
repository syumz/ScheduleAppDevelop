package org.example.scheduleappdevelop.comment.repository;

import org.example.scheduleappdevelop.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByScheduleId(Long scheduleId);

    default Comment findByIdOrElseThrow(Long commentId){
        return findById(commentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + commentId));
    }

    List<Comment> findBySchedule_IdIn(List<Long> scheduleIdList);
}
