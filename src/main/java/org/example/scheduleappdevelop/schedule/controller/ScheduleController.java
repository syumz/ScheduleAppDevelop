package org.example.scheduleappdevelop.schedule.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.scheduleappdevelop.schedule.dto.*;
import org.example.scheduleappdevelop.schedule.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping// 일정 생성
    public ResponseEntity<ScheduleResponseDto> saveSchedule(
            HttpServletRequest request,
            @RequestBody ScheduleRequestDto requestDto) {

        return new ResponseEntity<>(scheduleService.saveSchedule(request, requestDto.getTitle(), requestDto.getContents()), HttpStatus.CREATED);
    }

    @GetMapping // 일정 전체 조회
    public ResponseEntity<List<ScheduleResponseDto>> findAll() {
        List<ScheduleResponseDto> scheduleResponseDtoList = scheduleService.findAll();

        return new ResponseEntity<>(scheduleResponseDtoList, HttpStatus.OK);
    }

    @PatchMapping("/{id}") // 일정 수정
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            HttpServletRequest request,
            @RequestBody UpdateScheduleRequestDto requestDto) {
        return new ResponseEntity<>(scheduleService.updateSchedule(id, request, requestDto.getPassword(), requestDto.getTitle(), requestDto.getContents()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        scheduleService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/paging")// 일정 페이지 조회
    public ResponseEntity<SchedulePageInfoResponseDto<SchedulePageResponseDto>> pagingSchedule(
            @ModelAttribute SchedulePageRequestDto requestDto) {
        return new ResponseEntity<>(scheduleService.pagingSchedule(requestDto.getPage(), requestDto.getSize()), HttpStatus.OK);

    }
}
