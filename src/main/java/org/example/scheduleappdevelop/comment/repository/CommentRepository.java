package org.example.scheduleappdevelop.comment.repository;

import org.example.scheduleappdevelop.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByScheduleId(Long scheduleId);

    List<Comment> findBySchedule_IdIn(List<Long> scheduleIdList);
}
