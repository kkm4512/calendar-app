package com.calendarapp.calendarapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class CalendarRequestDto {
    //사용자로부터 받아야할 정보 목록들
    String memberName;
    String password;
    String todo;
}
