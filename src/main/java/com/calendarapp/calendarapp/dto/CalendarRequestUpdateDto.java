package com.calendarapp.calendarapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CalendarRequestUpdateDto {
    String memberName;
    String todo;
}
