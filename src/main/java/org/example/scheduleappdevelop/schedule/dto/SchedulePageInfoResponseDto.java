package org.example.scheduleappdevelop.schedule.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class SchedulePageInfoResponseDto<T> {

    private final List<T> contents; // 작성자, 제목, 내용, 작성일, 수정일, 댓글 개수를 담은 리스트

    private final int page;

    private final int size;


    public SchedulePageInfoResponseDto(List<T> contents, int page, int size) {
        this.contents = contents;
        this.page = page;
        this.size = size;

    }

    public static <T> SchedulePageInfoResponseDto<T> toDto(List<T> contents, int page, int size) {
        return new SchedulePageInfoResponseDto<>(contents, page, size);
    }
}
