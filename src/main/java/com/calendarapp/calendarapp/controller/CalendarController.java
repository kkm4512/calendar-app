package com.calendarapp.calendarapp.controller;

import com.calendarapp.calendarapp.dto.CalendarRequestDto;
import com.calendarapp.calendarapp.dto.CalendarRequestUpdateDto;
import com.calendarapp.calendarapp.dto.CalendarResponseDto;
import com.calendarapp.calendarapp.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {
    private final CalendarService calendarService;

    //생성자 하나이기떄문에 Autowired 생략
    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    //모든 일정 조회
    @GetMapping
    public List<CalendarResponseDto> getAllCalendars(){
        return calendarService.getAllCalendars();
    }

    // 아이디로 단일 일정 조회
    @GetMapping("/{id}")
    public CalendarResponseDto getCalendarById(@PathVariable Long id){
        return calendarService.getCalendarById(id);
    }

    // 수정일로 전체 일정 조회
    @GetMapping("/updateAt")
    public List<CalendarResponseDto> getCalendarByUpdateAt(@RequestParam String updateAt){
        return calendarService.getCalendarByUpdateAt(updateAt);
    }

    // 담당자명으로 전체 일정 조회
    @GetMapping("/author")
    public List<CalendarResponseDto> getCalendarByAuthor(@RequestParam String author){
        return calendarService.getCalendarByAuthor(author);
    }

    //일정 저장
    @PostMapping
    public CalendarResponseDto saveCalendar(@RequestBody CalendarRequestDto calendarRequestDto){
        return calendarService.saveCalendar(calendarRequestDto);
    }

    //할일내용, 담당자명 수정
    //수정 진행시, 내부적으로 updateAt 자동 업데이트
    @PutMapping("/{id}")
    public CalendarResponseDto updateCalendar(@PathVariable Long id, @RequestBody CalendarRequestUpdateDto calendarRequestUpdateDto){
        return calendarService.updateCalendar(id,calendarRequestUpdateDto);
    }

    //할일 삭제
    @DeleteMapping("/{id}")
    public boolean deleteCalendar(@PathVariable Long id){
        return calendarService.deleteCalendar(id);
    }
}
