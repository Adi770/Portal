package com.lepa.portal.model.search_component;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class GPU implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String manufacture;
    private String brand;
    private String type;
    private String tdp;

    private Boolean recommend;

    @ManyToMany( fetch = FetchType.EAGER, cascade = CascadeType.ALL,mappedBy = "gpu")
    private List<CPU> cpu;

    public GPU(String manufacture, String brand, String type, String tdp, Boolean recommend) {
        this.manufacture = manufacture;
        this.brand = brand;
        this.type = type;
        this.tdp = tdp;
        this.recommend = recommend;
    }

    public GPU() {
    }
}
