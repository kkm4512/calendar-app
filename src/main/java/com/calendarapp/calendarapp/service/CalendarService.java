package com.calendarapp.calendarapp.service;

import com.calendarapp.calendarapp.dto.CalendarRequestDto;
import com.calendarapp.calendarapp.dto.CalendarResponseDto;
import com.calendarapp.calendarapp.entity.Calendar;
import com.calendarapp.calendarapp.repository.CalendarRepository;
import java.util.List;

public class CalendarService {
    private static Long id = 0L;

    //일정 전체 조회
    public List<CalendarResponseDto> getAllCalendars() {
        return CalendarRepository.getAllCalendars().values().stream().map(CalendarResponseDto::new).toList();
    }

    //일정 생성
    public CalendarResponseDto saveCalendar(CalendarRequestDto calendarRequestDto) {
        Calendar calendar =  new Calendar(calendarRequestDto);
        calendar.setId(++id);
        CalendarRepository.addCalendar(id,calendar);
        return new CalendarResponseDto(calendar);
    }
}
