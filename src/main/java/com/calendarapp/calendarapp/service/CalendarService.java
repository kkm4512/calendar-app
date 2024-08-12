package com.calendarapp.calendarapp.service;

import com.calendarapp.calendarapp.dto.CalendarRequestDeleteDto;
import com.calendarapp.calendarapp.dto.CalendarRequestDto;
import com.calendarapp.calendarapp.dto.CalendarRequestUpdateDto;
import com.calendarapp.calendarapp.dto.CalendarResponseDto;
import com.calendarapp.calendarapp.entity.Calendar;
import com.calendarapp.calendarapp.repository.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CalendarService {
    private static Long id = 0L;
    private final JdbcTemplate jdbcTemplate;

    public CalendarService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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
        LocalDateTime now = LocalDateTime.now();
        KeyHolder keyHolder = new GeneratedKeyHolder();

        //JDBC Template
        String insertSql = "INSERT INTO calendar (author,todo,password,createAt,updateAt) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(insertSql,
                calendarRequestDto.getAuthor(),
                calendarRequestDto.getTodo(),
                calendarRequestDto.getPassword(),
                now,
                now
        );
        // 생성된 ID
        Long generatedId = keyHolder.getKey().longValue();

        // 생성된 ID를 사용하여 DB에서 레코드 조회
        String selectSql = "SELECT * FROM calendar WHERE id = ?";
        Map<String, Object> result = jdbcTemplate.queryForMap(selectSql, generatedId);

        // CalendarResponseDto 객체 생성 및 반환
        CalendarResponseDto calendarResponseDto = new CalendarResponseDto();
        calendarResponseDto.setId((Long) result.get("id"));
        calendarResponseDto.setAuthor((String) result.get("author"));
        calendarResponseDto.setTodo((String) result.get("todo"));
        calendarResponseDto.setPassword((String) result.get("password"));
        calendarResponseDto.setCreateAt(((Timestamp) result.get("createAt")).toLocalDateTime());
        calendarResponseDto.setUpdateAt(((Timestamp) result.get("updateAt")).toLocalDateTime());
        return calendarResponseDto;
    }

    //일정 업데이트
    public CalendarResponseDto updateCalendar(Long id, CalendarRequestUpdateDto calendarRequestUpdateDto) {
        /**
         *  1. 일단 어떤 일정을 수정해올건지 Map 에서 찾아오고
         *  2. 해당 Map 안에 저장되있는거니까 Calendar 클래스니까, 그 클래스안에서 내용을 수정 할 수 있게 메서드들을 추가해두자
         */
        Calendar calendar = CalendarRepository.getCalendar(id);
        calendar.updateCalendar(calendarRequestUpdateDto);
        calendar.setUpdateAt(LocalDateTime.now());
        return new CalendarResponseDto(calendar);
    }

    //일정 삭제
    public boolean deleteCalendar(Long id, CalendarRequestDeleteDto calendarRequestDeleteDto) {
        return CalendarRepository.deleteCalendar(id);
    }
}
