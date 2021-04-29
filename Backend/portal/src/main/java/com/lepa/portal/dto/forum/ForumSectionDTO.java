package com.lepa.portal.dto.forum;

import lombok.Data;

import java.util.List;

@Data
public class ForumSectionDTO {

    private Long id;

    private String section;

    private List<ForumSubSectionDTO> forumSubSectionsList;


}
