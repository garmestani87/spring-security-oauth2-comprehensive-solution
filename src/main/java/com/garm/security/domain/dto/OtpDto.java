package com.garm.security.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OtpDto {

    private String smsIdentifier;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String verificationCode;

}
