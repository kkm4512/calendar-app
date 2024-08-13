package com.calendarapp.calendarapp.repository;

import com.calendarapp.calendarapp.dto.CalendarRequestDto;
import com.calendarapp.calendarapp.dto.CalendarRequestUpdateDto;
import com.calendarapp.calendarapp.dto.CalendarResponseDto;
import com.calendarapp.calendarapp.entity.Member;
import com.calendarapp.calendarapp.entity.Todo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

@Repository
public class CalendarRepository {
    private JdbcTemplate jdbcTemplate;

    public CalendarRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public CalendarResponseDto save(CalendarRequestDto calendarRequestDto) {
        //JDBC Template
        KeyHolder memberKey = new GeneratedKeyHolder();
        KeyHolder todoKey = new GeneratedKeyHolder();
        String memberSql = "INSERT INTO member (memberName,password, email) VALUES (?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(memberSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, calendarRequestDto.getMemberName());
            ps.setString(2, calendarRequestDto.getPassword());
            ps.setString(3, calendarRequestDto.getEmail());
            return ps;
        }, memberKey);
        long memberId = memberKey.getKey().longValue();
        String todoSql = "INSERT INTO todo (todo,createdAt,updatedAt, memberId) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(todoSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, calendarRequestDto.getTodo());
            ps.setDate(2, Date.valueOf(LocalDate.now()));
            ps.setDate(3, Date.valueOf(LocalDate.now()));
            ps.setLong(4, memberId);
            return ps;
        }, todoKey);

        return findById(memberId);
    }

    public List<CalendarResponseDto> findAll() {
        String sql = "SELECT t.todoId, t.memberId, m.memberName, m.password, m.email, t.todo, t.createdAt, t.updatedAt " +
                "FROM todo t " +
                "JOIN member m ON t.memberId = m.memberId";
        return jdbcTemplate.query(sql, new RowMapper<CalendarResponseDto>() {
            @Override
            public CalendarResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                // SQL 의 결과로 받아온 Memo 데이터들을 MemoResponseDto 타입으로 변환해줄 메서드
                Long todoId = rs.getLong("todoId");
                Long memberId = rs.getLong("memberId");
                String memberName = rs.getString("memberName");
                String password = rs.getString("password");
                String todo = rs.getString("todo");
                String email = rs.getString("email");
                Timestamp timestampCreateAt = rs.getTimestamp("createdAt");
                Timestamp timestampUpdateAt = rs.getTimestamp("updatedAt");
                LocalDate createdAt = timestampCreateAt.toLocalDateTime().toLocalDate();
                LocalDate updatedAt = timestampUpdateAt.toLocalDateTime().toLocalDate();

                return new CalendarResponseDto(todoId, memberId, memberName, password, todo, email,createdAt, updatedAt);
            };
        });
    }

    //memberId 기준으로 검색
    public CalendarResponseDto findById(Long id) {
        String sql = "SELECT t.todoId, t.memberId, m.memberName, m.password, m.email, t.todo, t.createdAt, t.updatedAt " +
                "FROM todo t " +
                "JOIN member m ON t.memberId = m.memberId " +
                "WHERE t.todoId = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<>() {
            @Override
            public CalendarResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long todoId = rs.getLong("todoId");
                Long memberId = rs.getLong("memberId");
                String memberName = rs.getString("memberName");
                String password = rs.getString("password");
                String todo = rs.getString("todo");
                String email = rs.getString("email");
                Timestamp timestampCreateAt = rs.getTimestamp("createdAt");
                Timestamp timestampUpdateAt = rs.getTimestamp("updatedAt");
                LocalDate createdAt = timestampCreateAt.toLocalDateTime().toLocalDate();
                LocalDate updatedAt = timestampUpdateAt.toLocalDateTime().toLocalDate();
                return new CalendarResponseDto(todoId, memberId, memberName, password, todo, email,createdAt, updatedAt);
            }
        });
    }

    public List<CalendarResponseDto> findAllByUpdatedAt(String updatedAt) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate formatUpdateAt = LocalDate.parse(updatedAt, dateTimeFormatter);
        String sql = "SELECT t.todoId, t.memberId, m.memberName, m.password, m.email, t.todo, t.createdAt, t.updatedAt " +
                "FROM todo t " +
                "JOIN member m ON t.memberId = m.memberId " +
                "WHERE t.updatedAt = ? " +
                "ORDER BY t.updatedAt DESC";
        return jdbcTemplate.query(sql, new Object[]{Date.valueOf(formatUpdateAt)}, new RowMapper<>() {
            @Override
            public CalendarResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long todoId = rs.getLong("todoId");
                Long memberId = rs.getLong("memberId");
                String memberName = rs.getString("memberName");
                String password = rs.getString("password");
                String todo = rs.getString("todo");
                String email = rs.getString("email");
                Timestamp timestampCreateAt = rs.getTimestamp("createdAt");
                Timestamp timestampUpdateAt = rs.getTimestamp("updatedAt");
                LocalDate createdAt = timestampCreateAt.toLocalDateTime().toLocalDate();
                LocalDate updatedAt = timestampUpdateAt.toLocalDateTime().toLocalDate();
                return new CalendarResponseDto(todoId, memberId, memberName, password, todo, email,createdAt, updatedAt);
            }
        });
    }

    public List<CalendarResponseDto> findAllByMemberName(String memberName) {
        String sql = "SELECT t.todoId, t.memberId, m.memberName, m.password, m.email, t.todo, t.createdAt, t.updatedAt " +
                "FROM todo t " +
                "JOIN member m ON t.memberId = m.memberId " +
                "WHERE m.memberName = ?";
        return jdbcTemplate.query(sql, new Object[]{memberName}, new RowMapper<>() {
            @Override
            public CalendarResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long todoId = rs.getLong("todoId");
                Long memberId = rs.getLong("memberId");
                String memberName = rs.getString("memberName");
                String password = rs.getString("password");
                String todo = rs.getString("todo");
                String email = rs.getString("email");
                Timestamp timestampCreateAt = rs.getTimestamp("createdAt");
                Timestamp timestampUpdateAt = rs.getTimestamp("updatedAt");
                LocalDate createdAt = timestampCreateAt.toLocalDateTime().toLocalDate();
                LocalDate updatedAt = timestampUpdateAt.toLocalDateTime().toLocalDate();
                return new CalendarResponseDto(todoId, memberId, memberName, password, todo, email,createdAt, updatedAt);
            }
        });
    }

    //memberId 기준
    public CalendarResponseDto update(Long id, CalendarRequestUpdateDto calendarRequestUpdateDto) {
        /**
         *  1. 일단 어떤 일정을 수정해올건지 Map 에서 찾아오고
         *  2. 해당 Map 안에 저장되있는거니까 Calendar 클래스니까, 그 클래스안에서 내용을 수정 할 수 있게 메서드들을 추가해두자
         *  3. 작성자,할일,업데이트(이건 자동으로)만 수정
         */
        String todoSql = "UPDATE todo SET todo = ?, updatedAt = ? where memberId = ?";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(todoSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, calendarRequestUpdateDto.getTodo());
            ps.setDate(2, Date.valueOf(LocalDate.now()));
            ps.setLong(3, id);
            return ps;
        });
        String memberSql = "UPDATE member SET memberName = ? where memberId = ?";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(memberSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, calendarRequestUpdateDto.getMemberName());
            ps.setLong(2, id);
            return ps;
        });
        return findById(id);
    }

    public boolean delete(Long id) {
        String sql = "DELETE FROM member WHERE memberId = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        return rowsAffected > 0;
    }
}
