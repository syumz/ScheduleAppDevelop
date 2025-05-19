package org.example.scheduleappdevelop.schedule.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduleappdevelop.schedule.dto.ScheduleResponseDto;
import org.example.scheduleappdevelop.schedule.entity.Schedule;
import org.example.scheduleappdevelop.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService{

    private final ScheduleRepository scheduleRepository;

    public ScheduleResponseDto saveSchedule(String username, String title, String contents) {

        Schedule schedule = new Schedule(title, contents);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(savedSchedule.getId(), savedSchedule.getTitle(), savedSchedule.getContents());
    }

    public List<ScheduleResponseDto> findAll() {

        return scheduleRepository.findAll()
                .stream()
                .map(ScheduleResponseDto::toDto)
                .toList();
    }

    public ScheduleResponseDto updateSchedule(Long id, String title, String contents) {

        return new ScheduleResponseDto(id, title, contents);
    }

    public void delete(Long id) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        scheduleRepository.delete(findSchedule);
    }
}
