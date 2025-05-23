package org.example.scheduleappdevelop.schedule.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduleappdevelop.comment.entity.Comment;
import org.example.scheduleappdevelop.comment.repository.CommentRepository;
import org.example.scheduleappdevelop.schedule.dto.SchedulePageInfoResponseDto;
import org.example.scheduleappdevelop.schedule.dto.SchedulePageResponseDto;
import org.example.scheduleappdevelop.schedule.dto.ScheduleResponseDto;
import org.example.scheduleappdevelop.schedule.entity.Schedule;
import org.example.scheduleappdevelop.schedule.repository.ScheduleRepository;
import org.example.scheduleappdevelop.user.entity.User;
import org.example.scheduleappdevelop.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService{

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public ScheduleResponseDto saveSchedule(String username, String title, String contents) {

        User findUser = userRepository.findMemberByUsernameOrElseThrow(username);

        Schedule schedule = new Schedule(title, contents);
        schedule.setUser(findUser);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(savedSchedule.getId(), savedSchedule.getTitle(), savedSchedule.getContents(), savedSchedule.getCreatedAt(), savedSchedule.getModifiedAt());
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

        return new ScheduleResponseDto(id, updatedSchedule.getTitle(), updatedSchedule.getContents(), updatedSchedule.getCreatedAt(),updatedSchedule.getModifiedAt());
    }

    public void delete(Long id) {
        Schedule findSchedule = scheduleRepository.findByIdOrElseThrow(id);

        scheduleRepository.delete(findSchedule);
    }


    public SchedulePageInfoResponseDto<SchedulePageResponseDto> pagingSchedule(int page, int size) {

        Page<Schedule> schedulePage = scheduleRepository.findAll(PageRequest.of(page-1, size, Sort.by("modifiedAt").descending()));

        // 현재 페이지에 해당하는 스케줄 아이디를 가져온다.
        List<Long> scheduleIdList = schedulePage
                .getContent()
                .stream()
                .map(Schedule::getId)
                .toList();

        // 스케줄 아이디에 맞는 댓글을 가져온다.
        List<Comment> commentList = commentRepository.findBySchedule_IdIn(scheduleIdList);

        List<SchedulePageResponseDto> list = schedulePage
                .stream()
                .map(schedule -> SchedulePageResponseDto.toDto(schedule, commentList))
                .toList();

        return SchedulePageInfoResponseDto.toDto( 
                list,
                page,
                size
        );
    }
}
