package com.lepa.portal.model.portal;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@Entity
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition="text")
    private String text;
    private LocalDate date;


    @ManyToOne
    private Users user;

    @JsonIgnore
    @ManyToOne
    private News news;



}
