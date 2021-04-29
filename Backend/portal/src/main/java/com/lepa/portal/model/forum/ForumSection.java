package com.lepa.portal.model.forum;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
public class ForumSection  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String section;

    @OneToMany(mappedBy = "forumSection", cascade = CascadeType.ALL)
    private List<ForumSubSection> forumSubSectionsList;

    public ForumSection() {
    }

    public ForumSection(Long id, String section) {
        this.id = id;
        this.section = section;
    }
}
