package com.garm.security.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserInfoDto extends UserDetailsDto {

    private Long id;
    private String email;
    private Long activeSubsystemId;
    private String activeSubsystemName;
    private List<String> actions;
    private List<String> permissions;
    private boolean forcePasswordChange;
    private boolean active;
    private boolean vip;
    private boolean empty;

    public boolean isEmpty() {
        return StringUtils.isEmpty(getFirstName()) || StringUtils.isEmpty(getLastName());
    }
}
