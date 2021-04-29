package com.lepa.portal.dto;

import lombok.Data;

import java.time.LocalDate;


@Data
public class CommentsDTO {

    private Long id;
    private String text;
    private LocalDate date;
    private UsersDTO user;
}
