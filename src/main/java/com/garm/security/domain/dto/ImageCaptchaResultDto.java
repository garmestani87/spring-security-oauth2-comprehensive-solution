package com.garm.security.domain.dto;

import lombok.Data;

@Data
public class ImageCaptchaResultDto {
    private byte[] image;
    private String uniqueId;
}
