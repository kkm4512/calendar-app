package com.calendarapp.calendarapp.dto;

import com.calendarapp.calendarapp.entity.Calendar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

//클라이언트로 보내줄 캘린더 DTO
@Getter
@Setter
@AllArgsConstructor
public class CalendarResponseDto {
    private Long id;
    private String todo;
    private String password;
    private String createAt;
    private String updateAt;

    public CalendarResponseDto(Calendar calendar) {
        this.id = calendar.getId();
        this.todo = calendar.getTodo();
        this.password = calendar.getPassword();
        this.createAt = calendar.getCreateAt();
        this.updateAt = calendar.getUpdateAt();
    }
}
