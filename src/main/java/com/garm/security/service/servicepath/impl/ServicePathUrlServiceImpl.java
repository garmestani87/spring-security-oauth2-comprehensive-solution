package com.garm.security.service.servicepath.impl;

import com.garm.security.cache.UserInfoCacheService;
import com.garm.security.domain.dto.UserInfoDto;
import com.garm.security.helper.SecurityHelper;
import com.garm.security.service.servicepath.ServicePathUrlService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicePathUrlServiceImpl implements ServicePathUrlService {

    private final UserInfoCacheService userInfoCacheService;

    @Override
    public void hasAccessUrlThisPath(String userName, String url) {
        boolean hasAccess = false;
        if (!SecurityHelper.hasSuperAccess(url)) {
            UserInfoDto userInfoDto = userInfoCacheService.get(userName);
            if (!userInfoDto.isEmpty()) {
                if (CollectionUtils.isNotEmpty(userInfoDto.getActions())) {
                    hasAccess = userInfoDto.getActions().stream().anyMatch(service -> service.equals(url));
                }
            }
        } else {
            hasAccess = true;
        }
        if (!hasAccess) throw new RuntimeException("security.user.service.access.denied");
    }

}
