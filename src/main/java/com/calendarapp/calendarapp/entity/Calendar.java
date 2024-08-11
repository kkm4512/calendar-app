package com.calendarapp.calendarapp.entity;

import com.calendarapp.calendarapp.dto.CalendarRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

//실제 Calendar Entity 에 저장시킬 캘린더
//나중에 실제 db 연결할때는 createdAt,updatedAt에 어노테이션 달아주기
@Getter
@Setter
@AllArgsConstructor
public class Calendar {
    private Long id;
    private String todo;
    private String password;
    private String createAt;
    private String updateAt;

    public Calendar(CalendarRequestDto calendarRequestDto) {
        this.todo = calendarRequestDto.getTodo();
        this.password = calendarRequestDto.getPassword();
        this.createAt = calendarRequestDto.getCreateAt();
        this.updateAt = calendarRequestDto.getUpdateAt();
    }
}
