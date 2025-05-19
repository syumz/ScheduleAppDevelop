package org.example.scheduleappdevelop.schedule.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduleappdevelop.schedule.dto.ScheduleResponseDto;
import org.example.scheduleappdevelop.schedule.entity.Schedule;
import org.example.scheduleappdevelop.schedule.repository.ScheduleRepository;
import org.example.scheduleappdevelop.user.entity.User;
import org.example.scheduleappdevelop.user.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService{

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    public ScheduleResponseDto saveSchedule(String username, String title, String contents) {

        User findUser = userRepository.findMemberByUsernameOrElseThrow(username);

        Schedule schedule = new Schedule(title, contents);
        schedule.setUser(findUser);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(savedSchedule.getId(), savedSchedule.getTitle(), savedSchedule.getContents());
    }

    public List<ScheduleResponseDto> findAll() {

        return scheduleRepository.findAll()
                .stream()
                .map(ScheduleResponseDto::toDto)
                .toList();
    }

    public ScheduleResponseDto updateSchedule(Long id, String password, String title, String contents) {

        User findUser = userRepository.findByIdOrElseThrow(id);

        if(!findUser.getPassword().equals(password)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");

        }

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다."));

        schedule.setTitle(title);
        schedule.setContents(contents);

        Schedule updatedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(id, updatedSchedule.getTitle(), updatedSchedule.getContents());
    }

    public void delete(Long id) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        scheduleRepository.delete(findSchedule);
    }
}
