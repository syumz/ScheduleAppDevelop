package org.example.scheduleappdevelop.comment.dto;

import lombok.Getter;

@Getter
public class CommentResponseDto {

    private final Long id;

    private final String username;

    private final String comment;

    public CommentResponseDto(Long id, String username, String comment) {
        this.id = id;
        this.username = username;
        this.comment = comment;
    }
}
