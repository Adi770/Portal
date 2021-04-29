package com.lepa.portal.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lepa.portal.model.portal.News;
import com.lepa.portal.model.portal.Users;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ArticleRating {

    @EmbeddedId
    ArticleRatingKey id;

    @JsonIgnore
    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name="user_id")
    Users users;


    @JsonIgnore
    @ManyToOne
    @MapsId("article_id")
    @JoinColumn(name="article_id")
    News news;

    int rating;

}
