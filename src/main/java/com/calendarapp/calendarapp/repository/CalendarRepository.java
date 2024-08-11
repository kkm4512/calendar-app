package com.calendarapp.calendarapp.repository;

import com.calendarapp.calendarapp.entity.Calendar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

// 캘린더 레파지토리 (일단은 db 대용으로 사용)
public class CalendarRepository {
    public static final Map<Long, Calendar> calendarRepository = new HashMap<>();
    public static Map<Long,Calendar> getAllCalendars() {
        return calendarRepository;
    }
    public static void addCalendar(Long id, Calendar calendar) {
        calendarRepository.put(id,calendar);
    }

}
