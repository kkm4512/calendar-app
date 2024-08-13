package com.calendarapp.calendarapp.service;

import com.calendarapp.calendarapp.dto.CalendarRequestDto;
import com.calendarapp.calendarapp.dto.CalendarRequestUpdateDto;
import com.calendarapp.calendarapp.dto.CalendarResponseDto;
import com.calendarapp.calendarapp.repository.CalendarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarService {
    private final CalendarRepository calendarRepository;

    public CalendarService(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    //모든 일정 조회
    public List<CalendarResponseDto> getAllCalendars() {
        return calendarRepository.findAll();
    }

    // 아이디로 단일 일정 조회
    //todoId 기준으로 검색
    public CalendarResponseDto getCalendarById(Long id) {
        return calendarRepository.findById(id);
    }

    //일정 저장
    public CalendarResponseDto saveCalendar(CalendarRequestDto calendarRequestDto) {
        return calendarRepository.save(calendarRequestDto);
    }

    // 수정일로 전체 일정 조회
    public List<CalendarResponseDto> getCalendarByUpdatedAt(String updatedAt) {
        return calendarRepository.findAllByUpdatedAt(updatedAt);
    }


    // 담당자명으로 전체 일정 조회
    public List<CalendarResponseDto> getCalendarByMemberName(String memberName) {
        return calendarRepository.findAllByMemberName(memberName);
    }

    //할일내용, 담당자명 수정
    //수정 진행시, 내부적으로 updateAt 자동 업데이트
    //memberId로 검색
    public CalendarResponseDto updateCalendar(Long id, CalendarRequestUpdateDto calendarRequestUpdateDto) {
        return calendarRepository.update(id,calendarRequestUpdateDto);
    }

    //할일 삭제
    //memberId로 검색
    public boolean deleteCalendar(Long id) {
        return calendarRepository.delete(id);
    }


}
