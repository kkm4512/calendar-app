package com.calendarapp.calendarapp.service;

import com.calendarapp.calendarapp.dto.CalendarRequestDeleteDto;
import com.calendarapp.calendarapp.dto.CalendarRequestDto;
import com.calendarapp.calendarapp.dto.CalendarRequestUpdateDto;
import com.calendarapp.calendarapp.dto.CalendarResponseDto;
import com.calendarapp.calendarapp.entity.Calendar;
import com.calendarapp.calendarapp.repository.CalendarRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CalendarService {
    private static Long id = 0L;

    //일정 전체 조회
    public List<CalendarResponseDto> getAllCalendars() {
        return CalendarRepository.getAllCalendars().values().stream().map(CalendarResponseDto::new).toList();
    }

    //일정 단일 조회
    public CalendarResponseDto getCalendarById(Long id) {
        return new CalendarResponseDto(CalendarRepository.getCalendar(id));
    }

    //수정일로 일정 조회
    // 반환해줄때는 updateAt 기준으로 내림차순하기
    public List<CalendarResponseDto> getCalendarByUpdateAt(String updateAt) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate formatUpdateAt = LocalDate.parse(updateAt, dateTimeFormatter);
        return CalendarRepository.getAllCalendars().values().stream()
                .filter(calendar -> calendar.getUpdateAt().equals(formatUpdateAt))
                .map(CalendarResponseDto::new)
                //내림차순 정렬
                .sorted((cal1,cal2) -> cal2.getUpdateAt().compareTo(cal1.getUpdateAt()))
                .toList();
    }

    //담당자명으로 일정 조회
    public List<CalendarResponseDto> getCalendarByAuthor(String author) {
        return CalendarRepository.getAllCalendars().values().stream()
                .filter(calendar -> calendar.getAuthor().equals(author))
                .map(CalendarResponseDto::new)
                .toList();
    }

    //일정 생성
    public CalendarResponseDto saveCalendar(CalendarRequestDto calendarRequestDto) {
        LocalDate now = LocalDate.now();
        Calendar calendar =  new Calendar(calendarRequestDto);
        calendar.setId(++id);
        calendar.setCreateAt(now);
        calendar.setUpdateAt(now);
        CalendarRepository.addCalendar(calendar);
        return new CalendarResponseDto(calendar);
    }

    //일정 업데이트
    public CalendarResponseDto updateCalendar(Long id, CalendarRequestUpdateDto calendarRequestUpdateDto) {
        /**
         *  1. 일단 어떤 일정을 수정해올건지 Map 에서 찾아오고
         *  2. 해당 Map 안에 저장되있는거니까 Calendar 클래스니까, 그 클래스안에서 내용을 수정 할 수 있게 메서드들을 추가해두자
         */
        Calendar calendar = CalendarRepository.getCalendar(id);
        calendar.updateCalendar(calendarRequestUpdateDto);
        calendar.setUpdateAt(LocalDate.now());
        return new CalendarResponseDto(calendar);
    }

    //일정 삭제
    public boolean deleteCalendar(Long id, CalendarRequestDeleteDto calendarRequestDeleteDto) {
        return CalendarRepository.deleteCalendar(id);
    }
}
