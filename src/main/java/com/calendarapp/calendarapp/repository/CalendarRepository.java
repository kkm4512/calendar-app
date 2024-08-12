package com.calendarapp.calendarapp.repository;

import com.calendarapp.calendarapp.dto.CalendarRequestDto;
import com.calendarapp.calendarapp.dto.CalendarRequestUpdateDto;
import com.calendarapp.calendarapp.dto.CalendarResponseDto;
import com.calendarapp.calendarapp.entity.Calendar;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class CalendarRepository {
    private final JdbcTemplate jdbcTemplate;
    public CalendarRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 일정 전체 조회
    public List<CalendarResponseDto> getAllCalendars() {
        String sql = "SELECT * FROM calendar";
        return jdbcTemplate.query(sql, new RowMapper<CalendarResponseDto>() {
            @Override
            public CalendarResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 Memo 데이터들을 MemoResponseDto 타입으로 변환해줄 메서드
                Long id = rs.getLong("id");
                String author = rs.getString("author");
                String todo = rs.getString("todo");
                String password = rs.getString("password");
                Timestamp timestampCreateAt = rs.getTimestamp("createAt");
                Timestamp timestampUpdateAt = rs.getTimestamp("updateAt");
                LocalDate createAt = timestampCreateAt.toLocalDateTime().toLocalDate();
                LocalDate updateAt = timestampUpdateAt.toLocalDateTime().toLocalDate();
                return new CalendarResponseDto(id,author,todo,password,createAt,updateAt);
            }
        });
    }

    // 일정 단일 조회
    public CalendarResponseDto getCalendarById(Long id) {
        String sql = "SELECT * FROM calendar WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<>() {
            @Override
            public CalendarResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("id");
                String author = rs.getString("author");
                String todo = rs.getString("todo");
                String password = rs.getString("password");
                Timestamp timestampCreateAt = rs.getTimestamp("createAt");
                Timestamp timestampUpdateAt = rs.getTimestamp("updateAt");
                LocalDate createAt = timestampCreateAt.toLocalDateTime().toLocalDate();
                LocalDate updateAt = timestampUpdateAt.toLocalDateTime().toLocalDate();
                return new CalendarResponseDto(id, author, todo, password, createAt, updateAt);
            }
        });
    }

    // 수정일로 일정 조회
    // 반환할 때 updateAt 기준으로 내림차순 정렬
    public List<CalendarResponseDto> getCalendarByUpdateAt(String updateAt) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate formatUpdateAt = LocalDate.parse(updateAt, dateTimeFormatter);
        String sql = "SELECT * FROM calendar WHERE DATE(updateAt) = ? ORDER BY updateAt DESC";
        return jdbcTemplate.query(sql, new Object[]{Date.valueOf(formatUpdateAt)}, new RowMapper<>() {
            @Override
            public CalendarResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("id");
                String author = rs.getString("author");
                String todo = rs.getString("todo");
                String password = rs.getString("password");
                Timestamp timestampCreateAt = rs.getTimestamp("createAt");
                Timestamp timestampUpdateAt = rs.getTimestamp("updateAt");
                LocalDate createAt = timestampCreateAt.toLocalDateTime().toLocalDate();
                LocalDate updateAt = timestampUpdateAt.toLocalDateTime().toLocalDate();
                return new CalendarResponseDto(id, author, todo, password, createAt, updateAt);
            }
        });
    }

    //담당자명으로 일정 조회
    public List<CalendarResponseDto> getCalendarByAuthor(String author) {
        String sql =" SELECT * from calendar WHERE author = ?";
        return jdbcTemplate.query(sql,new Object[]{author}, new RowMapper<>() {
            @Override
            public CalendarResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("id");
                String author = rs.getString("author");
                String todo = rs.getString("todo");
                String password = rs.getString("password");
                Timestamp timestampCreateAt = rs.getTimestamp("createAt");
                Timestamp timestampUpdateAt = rs.getTimestamp("updateAt");
                LocalDate createAt = timestampCreateAt.toLocalDateTime().toLocalDate();
                LocalDate updateAt = timestampUpdateAt.toLocalDateTime().toLocalDate();
                return new CalendarResponseDto(id,todo,author,password,createAt,updateAt);
            }
        });
    }

    //일정 생성
    public CalendarResponseDto saveCalendar(CalendarRequestDto calendarRequestDto) {
        LocalDate now = LocalDate.now();
        Calendar calendar = new Calendar(calendarRequestDto);
        calendar.setCreateAt(now);
        calendar.setUpdateAt(now);
        //JDBC Template
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String insertSql = "INSERT INTO calendar (author,todo,password,createAt,updateAt) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, calendarRequestDto.getAuthor());
            ps.setString(2, calendarRequestDto.getTodo());
            ps.setString(3, calendarRequestDto.getPassword());
            ps.setDate(4, Date.valueOf(calendar.getCreateAt()));
            ps.setDate(5, Date.valueOf(calendar.getUpdateAt()));
            return ps;
        }, keyHolder);
        long generatedId = keyHolder.getKey().longValue();
        calendar.setId(generatedId);
        return new CalendarResponseDto(calendar);
    }

    //일정 업데이트
    public CalendarResponseDto updateCalendar(Long id, CalendarRequestUpdateDto calendarRequestUpdateDto) {
        /**
         *  1. 일단 어떤 일정을 수정해올건지 Map 에서 찾아오고
         *  2. 해당 Map 안에 저장되있는거니까 Calendar 클래스니까, 그 클래스안에서 내용을 수정 할 수 있게 메서드들을 추가해두자
         *  3. 작성자,할일,업데이트(이건 자동으로)만 수정
         */
        Calendar calendar = new Calendar(getCalendarById(id));
        calendar.updateCalendar(calendarRequestUpdateDto);
        calendar.setUpdateAt(LocalDate.now());
        String insertSql = "UPDATE calendar SET author = ?, todo = ?, updateAt = ? where id = ?";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, calendarRequestUpdateDto.getAuthor());
            ps.setString(2, calendarRequestUpdateDto.getTodo());
            ps.setDate(3, Date.valueOf(calendar.getUpdateAt()));
            ps.setLong(4, calendar.getId());
            return ps;
        });
        return new CalendarResponseDto(calendar);
    }

    //일정 삭제
    public boolean deleteCalendar(Long id) {
        String sql = "DELETE FROM calendar WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }

}
