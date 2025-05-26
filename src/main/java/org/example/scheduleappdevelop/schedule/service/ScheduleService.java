package org.example.scheduleappdevelop.schedule.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.scheduleappdevelop.comment.entity.Comment;
import org.example.scheduleappdevelop.comment.repository.CommentRepository;
import org.example.scheduleappdevelop.common.Const;
import org.example.scheduleappdevelop.config.PasswordEncoder;
import org.example.scheduleappdevelop.schedule.dto.SchedulePageInfoResponseDto;
import org.example.scheduleappdevelop.schedule.dto.SchedulePageResponseDto;
import org.example.scheduleappdevelop.schedule.dto.ScheduleResponseDto;
import org.example.scheduleappdevelop.schedule.entity.Schedule;
import org.example.scheduleappdevelop.schedule.repository.ScheduleRepository;
import org.example.scheduleappdevelop.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;
    private final PasswordEncoder passwordEncoder;

    public ScheduleResponseDto saveSchedule(HttpServletRequest request, String title, String contents) {

        HttpSession session = request.getSession(false);
        User loginUser = (User) session.getAttribute(Const.LOGIN_USER);

        if(loginUser == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인한 사용자만 작성할 수 있습니다.");
        }

        Schedule schedule = new Schedule(title, contents);
        schedule.setUser(loginUser);

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(savedSchedule.getId(), savedSchedule.getTitle(), savedSchedule.getContents(), savedSchedule.getCreatedAt(), savedSchedule.getModifiedAt());
    }

    public List<ScheduleResponseDto> findAll() {

        return scheduleRepository.findAll()
                .stream()
                .map(ScheduleResponseDto::toDto)
                .toList();
    }

    public ScheduleResponseDto updateSchedule(Long id, HttpServletRequest request, String password, String title, String contents) {

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다."));


        // 세션에서 이메일을 불러오고 업데이트할 스케줄을 쓴 유저의 이메일이랑 다르면 예외 발생
        HttpSession session = request.getSession(false);
        User loginUser = (User) session.getAttribute(Const.LOGIN_USER);

        if(!loginUser.getEmail().equals(schedule.getUser().getEmail())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "로그인한 유저가 작성한 글이 아닙니다.");
        }

        User findUser = schedule.getUser();

        if (!passwordEncoder.matches(password, findUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");

        }

        schedule.setTitle(title);
        schedule.setContents(contents);

        Schedule updatedSchedule = scheduleRepository.save(schedule);

        return new ScheduleResponseDto(id, updatedSchedule.getTitle(), updatedSchedule.getContents(), updatedSchedule.getCreatedAt(), updatedSchedule.getModifiedAt());
    }

    public void delete(Long id) {
        Schedule findSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다."));

        scheduleRepository.delete(findSchedule);
    }


    public SchedulePageInfoResponseDto<SchedulePageResponseDto> pagingSchedule(int page, int size) {

        Page<Schedule> schedulePage = scheduleRepository.findAll(PageRequest.of(page - 1, size, Sort.by("modifiedAt").descending()));

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
