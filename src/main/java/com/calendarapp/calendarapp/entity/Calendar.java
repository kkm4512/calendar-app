package com.calendarapp.calendarapp.entity;

import com.calendarapp.calendarapp.dto.CalendarRequestDto;
import com.calendarapp.calendarapp.dto.CalendarRequestUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

//실제 Calendar Entity 에 저장시킬 캘린더
//나중에 실제 db 연결할때는 createdAt,updatedAt에 어노테이션 달아주기
@Getter
@Setter
@AllArgsConstructor
public class Calendar {
    private Long id;
    private String author;
    private String todo;
    private String password;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public Calendar(CalendarRequestDto calendarRequestDto) {
        this.author = calendarRequestDto.getAuthor();
        this.todo = calendarRequestDto.getTodo();
        this.password = calendarRequestDto.getPassword();
    }

    public void updateCalendar(CalendarRequestUpdateDto calendarRequestUpdateDto){
        this.todo = calendarRequestUpdateDto.getTodo();
        this.author = calendarRequestUpdateDto.getAuthor();
    }
}
