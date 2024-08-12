package com.calendarapp.calendarapp.dto;

import com.calendarapp.calendarapp.entity.Calendar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

//클라이언트로 보내줄 캘린더 DTO
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CalendarResponseDto {
    private Long id;
    private String author;
    private String todo;
    private String password;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    //Calendar 객체로 받은 데이터를, Dto 로 바꿔줌
    public CalendarResponseDto(Calendar calendar) {
        this.id = calendar.getId();
        this.author = calendar.getAuthor();
        this.todo = calendar.getTodo();
        this.password = calendar.getPassword();
        this.createAt = calendar.getCreateAt();
        this.updateAt = calendar.getUpdateAt();
    }

}
