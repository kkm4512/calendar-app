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
        CalendarRepository.addCalendar(calendar);
        return new CalendarResponseDto(calendar);
    }

    //일정 업데이트
    public CalendarResponseDto updateCalendar(Long id, CalendarRequestDto calendarRequestDto) {
        /**
         *  1. 일단 어떤 일정을 수정해올건지 Map 에서 찾아오고
         *  2. 해당 Map 안에 저장되있는거니까 Calendar 클래스니까, 그 클래스안에서 내용을 수정 할 수 있게 메서드들을 추가해두자
         */
        Calendar calendar = CalendarRepository.getCalendar(id);
        calendar.updateCalendar(calendarRequestDto);
        return new CalendarResponseDto(calendar);
    }

    //일정 삭제
    public boolean deleteCalendar(Long id) {
        return CalendarRepository.deleteCalendar(id);
    }

}
