package com.lepa.portal.dto;

import lombok.Data;

import java.util.List;


@Data
public class NewsDTO {

    private long id;
    private String title;
    private String image;
    private String article;
    private String username;
    private List<CommentsDTO> commentsSet;
    private double rate;


}
