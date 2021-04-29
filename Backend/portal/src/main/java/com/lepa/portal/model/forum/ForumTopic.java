package com.lepa.portal.model.forum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lepa.portal.model.portal.Users;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class ForumTopic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titleTopic;
    private LocalDate date;


    @JsonIgnore
    @ManyToOne
    private Users user;

    @JsonIgnore
    @ManyToOne
    private ForumSubSection forumSubSection;

    @OneToMany(mappedBy = "forumTopic", cascade = CascadeType.ALL)
    private List<ForumTopicComm> forumTopicCommList;



}
