package com.lepa.portal.dto.search_component;


import lombok.Data;

@Data
public class CpuDTO {
    private String brand;
    private String type;
    private String socket;

    private Boolean recommend;
}
