package com.calendarapp.calendarapp.service;

import com.calendarapp.calendarapp.dto.CalendarRequestDto;
import com.calendarapp.calendarapp.dto.CalendarRequestUpdateDto;
import com.calendarapp.calendarapp.dto.CalendarResponseDto;
import com.calendarapp.calendarapp.repository.CalendarRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarService {
    private final CalendarRepository calendarRepository;

    public CalendarService(JdbcTemplate jdbcTemplate) {
        this.calendarRepository = new CalendarRepository(jdbcTemplate);
    }

    //일정 전체 조회
    public List<CalendarResponseDto> getAllCalendars() {
        return calendarRepository.getAllCalendars();
    }

    // 일정 단일 조회
    public CalendarResponseDto getCalendarById(Long id) {
        return calendarRepository.getCalendarById(id);
    }

    // 수정일로 일정 조회
    // 반환할 때 updateAt 기준으로 내림차순 정렬
    public List<CalendarResponseDto> getCalendarByUpdateAt(String updateAt) {
        return calendarRepository.getCalendarByUpdateAt(updateAt);
    }

    //담당자명으로 일정 조회
    public List<CalendarResponseDto> getCalendarByAuthor(String author) {
        return calendarRepository.getCalendarByAuthor(author);
    }

    //일정 생성
    public CalendarResponseDto saveCalendar(CalendarRequestDto calendarRequestDto) {
        return calendarRepository.saveCalendar(calendarRequestDto);
    }

    //일정 업데이트
    public CalendarResponseDto updateCalendar(Long id, CalendarRequestUpdateDto calendarRequestUpdateDto) {
        return calendarRepository.updateCalendar(id, calendarRequestUpdateDto);
    }

    //일정 삭제
    public boolean deleteCalendar(Long id) {
        return calendarRepository.deleteCalendar(id);
    }
}
