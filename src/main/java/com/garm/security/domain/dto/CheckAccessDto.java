package com.garm.security.domain.dto;

import lombok.Data;

@Data
public class CheckAccessDto {

    private String uri;
    private String subsystem;

}