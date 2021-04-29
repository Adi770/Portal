package com.lepa.portal.dto.forum;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ForumTopicLastDTO {

    private Long id;
    private String titleTopic;
    private LocalDate date;

}
