package org.example.scheduleappdevelop.comment.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {

//    private final Long id;

    private final String username;

    private final String comment;

    public CommentRequestDto(Long id, String username, String comment) {
//        this.id = id;
        this.username = username;
        this.comment = comment;
    }
}
