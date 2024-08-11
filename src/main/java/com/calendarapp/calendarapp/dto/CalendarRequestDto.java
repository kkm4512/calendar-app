package com.calendarapp.calendarapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

//클라이언트로부터 받을 캘린더 DTO
@Getter
@Setter
@AllArgsConstructor
public class CalendarRequestDto {
    private String todo;
    private String password;
    private String createAt;
    private String updateAt;

    @Override
    public String toString() {
        return "CalendarRequestDto{" +
                "todo='" + todo + '\'' +
                ", password='" + password + '\'' +
                ", createAt='" + createAt + '\'' +
                ", updateAt='" + updateAt + '\'' +
                '}';
    }
}
