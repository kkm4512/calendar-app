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

    @Autowired
    public CalendarController(JdbcTemplate jdbcTemplate) {
        this.calendarService = new CalendarService(jdbcTemplate);
    }

    @GetMapping("/findAll")
    public List<CalendarResponseDto> getAllCalendars(){
        return calendarService.getAllCalendars();
    }

    @GetMapping("/findById/{id}")
    public CalendarResponseDto getCalendarById(@PathVariable Long id){
        return calendarService.getCalendarById(id);
    }

    // 수정일로 전체 일정 조회
    @GetMapping("/findByUpdateAt/{updateAt}")
    public List<CalendarResponseDto> getCalendarByUpdateAt(@PathVariable String updateAt){
        return calendarService.getCalendarByUpdateAt(updateAt);
    }

    // 담당자명으로 전체 일정 조회
    @GetMapping("/findByAuthor/{author}")
    public List<CalendarResponseDto> getCalendarByAuthor(@PathVariable String author){
        return calendarService.getCalendarByAuthor(author);
    }

    @PostMapping("/save")
    public CalendarResponseDto saveCalendar(@RequestBody CalendarRequestDto calendarRequestDto){
        return calendarService.saveCalendar(calendarRequestDto);
    }

    //할일내용, 담당자명 수정
    //수정 진행시, 내부적으로 updateAt 자동 업데이트
    @PutMapping("/update/{id}")
    public CalendarResponseDto updateCalendar(@PathVariable Long id, @RequestBody CalendarRequestUpdateDto calendarRequestUpdateDto){
        return calendarService.updateCalendar(id,calendarRequestUpdateDto);
    }

    //할일 삭제
    @DeleteMapping("/delete/{id}")
    public boolean deleteCalendar(@PathVariable Long id){
        return calendarService.deleteCalendar(id);
    }
}
