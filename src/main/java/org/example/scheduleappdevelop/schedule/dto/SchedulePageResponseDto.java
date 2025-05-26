package org.example.scheduleappdevelop.schedule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.example.scheduleappdevelop.comment.entity.Comment;
import org.example.scheduleappdevelop.schedule.entity.Schedule;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class SchedulePageResponseDto {

    private final String username;

    private final String title;

    private final String contents;

    private final Long commentCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime modifiedAt;


    public SchedulePageResponseDto(String username, String title, String contents, Long commentCount, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.username = username;
        this.title = title;
        this.contents = contents;
        this.commentCount = commentCount;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public static SchedulePageResponseDto toDto(Schedule schedule, List<Comment> findAllComment) {

        // 각 게시물에 달린 댓글의 개수
        Long commentCount = findAllComment
                .stream()
                .filter(comment -> comment.getSchedule().getId().equals(schedule.getId())) // 댓글의 schedule id가 지금 schedule id와 같다면
                .count(); // 필터에 해당하는 댓글의 개수를 센다.

        return new SchedulePageResponseDto(
                schedule.getUser().getUsername(),
                schedule.getTitle(),
                schedule.getContents(),
                commentCount,
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );

    }
}
