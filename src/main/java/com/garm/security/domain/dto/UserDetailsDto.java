package com.garm.security.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class UserDetailsDto extends BaseDto<Long> {

    private String userName;
    @JsonIgnore
    private String password;

    private String firstName;

    private String lastName;

    private String title;

    private String mobileNumber;

    private String nationalId;

    private String nationalCode;

    private boolean real;

    private boolean enabled;

    @JsonIgnore
    public String getPassword() {
        return password;
    }

}