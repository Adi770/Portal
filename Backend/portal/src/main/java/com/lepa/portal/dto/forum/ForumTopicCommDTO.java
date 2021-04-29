package com.lepa.portal.dto.forum;

import com.lepa.portal.dto.UsersDTO;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ForumTopicCommDTO {

    private Long id;
    private String author;
    private String text;
    private LocalDate date;
    private UsersDTO user;

}
