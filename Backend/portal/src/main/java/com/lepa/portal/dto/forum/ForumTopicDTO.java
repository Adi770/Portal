package com.lepa.portal.dto.forum;

import com.lepa.portal.dto.UsersDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ForumTopicDTO {

    private Long id;
    private String titleTopic;
    private LocalDate date;
    private UsersDTO user;
    private List<ForumTopicCommDTO> forumTopicCommList;

    public List<ForumTopicCommDTO> getForumTopicCommList() {
        return forumTopicCommList;
    }

    public void setForumTopicCommList(List<ForumTopicCommDTO> forumTopicCommList) {
        this.forumTopicCommList = forumTopicCommList;
    }
}
