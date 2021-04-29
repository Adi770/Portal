package com.lepa.portal.dto;


import com.lepa.portal.model.forum.ForumSection;
import lombok.Data;




@Data
public class ForumSubSectionDTO {

    private String name;
    private ForumSection forumSection;

}
