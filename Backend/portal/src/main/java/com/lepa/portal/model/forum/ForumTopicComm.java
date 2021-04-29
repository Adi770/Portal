package com.lepa.portal.model.forum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lepa.portal.model.portal.Users;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@Entity
public class ForumTopicComm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String author;
    @Column(columnDefinition="text")
    private String text;
    private LocalDate date;

    @JsonIgnore
    @ManyToOne
    private ForumTopic forumTopic;


    @ManyToOne
    private Users user;


}
