package com.lepa.portal.dto.search_component;

import lombok.Data;

@Data
public class GpuDTO {

    private String manufacture;
    private String brand;
    private String type;
    private String tdp;

    private Boolean recommend;

}
