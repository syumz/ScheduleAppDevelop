package org.example.scheduleappdevelop.schedule.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchedulePageRequestDto {

    private int page = 1;

    private int size = 10;
}
