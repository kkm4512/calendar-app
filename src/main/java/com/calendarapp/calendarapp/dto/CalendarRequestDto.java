package com.calendarapp.calendarapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//클라이언트로부터 받을 캘린더 DTO
@Getter
@Setter
@AllArgsConstructor
public class CalendarRequestDto {
    private String todo;
    private String password;
    private String createAt;
    private String updateAt;

}
