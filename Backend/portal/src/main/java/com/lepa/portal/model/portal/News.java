package com.lepa.portal.model.portal;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lepa.portal.model.ArticleRating;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Data
@Entity
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String image;
    @Column(columnDefinition="text")
    private String article;
    private LocalDate date;

    @JsonIgnore
    @ManyToOne(optional = false)
    private Users user;

    @OneToMany(mappedBy = "news",cascade = CascadeType.ALL)
    private List<Comments> commentsSet;

    @OneToMany(mappedBy = "news",cascade = CascadeType.ALL)
    List<ArticleRating> ratings;


}
