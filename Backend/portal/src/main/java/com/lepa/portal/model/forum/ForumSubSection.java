package com.lepa.portal.model.forum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class ForumSubSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @JsonIgnore
    @ManyToOne
    private ForumSection forumSection;

    @OneToMany(mappedBy = "forumSubSection", cascade = CascadeType.ALL)
    private List<ForumTopic> forumTopicList;

    public ForumSubSection() {
    }

    public ForumSubSection(String name, ForumSection forumSection) {

        this.name = name;
        this.forumSection = forumSection;
    }


}
