package com.calendarapp.calendarapp.controller;

import com.calendarapp.calendarapp.dto.CalendarRequestDto;
import com.calendarapp.calendarapp.dto.CalendarResponseDto;
import com.calendarapp.calendarapp.service.CalendarService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {
    private final CalendarService calendarService = new CalendarService();

    @GetMapping("/findAll")
    public List<CalendarResponseDto> getAllCalendars(){
        return calendarService.getAllCalendars();
    }

    @PostMapping("/save")
    public CalendarResponseDto createCalendar(@RequestBody CalendarRequestDto calendarRequestDto){
        return calendarService.saveCalendar(calendarRequestDto);
    }
}
