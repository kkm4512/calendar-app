package com.calendarapp.calendarapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
//전체 리스트 보여줄떄, db가 나뉘어져있어서 각각 set 해줘야해서 하나만듬
@NoArgsConstructor
public class CalendarResponseDto {
    private Long memberId;
    private Long todoId;
    private String memberName;
    private String password;
    private String todo;
    private String email;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
