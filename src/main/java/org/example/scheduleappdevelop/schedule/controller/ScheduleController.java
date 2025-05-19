package org.example.scheduleappdevelop.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.example.scheduleappdevelop.schedule.dto.ScheduleRequestDto;
import org.example.scheduleappdevelop.schedule.dto.ScheduleResponseDto;
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
            @RequestBody ScheduleRequestDto requestDto){

        return new ResponseEntity<>(scheduleService.saveSchedule(requestDto.getUsername(), requestDto.getTitle(), requestDto.getContents()), HttpStatus.CREATED);
    }

    @GetMapping // 일정 전체 조회
    public ResponseEntity<List<ScheduleResponseDto>> findAll(){
        List<ScheduleResponseDto> scheduleResponseDtoList = scheduleService.findAll();

        return new ResponseEntity<>(scheduleResponseDtoList, HttpStatus.OK);
    }

    @PatchMapping("/{id}") // 일정 수정
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto requestDto){
        return new ResponseEntity<>(scheduleService.updateSchedule(id, requestDto.getTitle(), requestDto.getContents()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        scheduleService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
