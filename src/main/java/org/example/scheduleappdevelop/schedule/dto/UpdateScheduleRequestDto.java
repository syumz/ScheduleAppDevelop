package org.example.scheduleappdevelop.schedule.dto;

import lombok.Getter;

@Getter
public class UpdateScheduleRequestDto {

    private final String username;

    private final String password;

    private final String title;

    private final String contents;

    public UpdateScheduleRequestDto(String username, String password, String title, String contents) {
        this.username = username;
        this.password = password;
        this.title = title;
        this.contents = contents;
    }
}
