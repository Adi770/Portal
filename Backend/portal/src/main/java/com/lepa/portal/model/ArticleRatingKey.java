package com.lepa.portal.model;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class ArticleRatingKey implements Serializable {

    @Column(name="user_id")
    Long userId;

    @Column(name="article_id")
    Long articleId;



}
