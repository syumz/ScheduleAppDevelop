package org.example.scheduleappdevelop.comment.repository;

import org.example.scheduleappdevelop.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
