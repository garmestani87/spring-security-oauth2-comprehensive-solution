package com.garm.security.login;


import com.garm.security.cache.OtpCacheService;
import com.garm.security.domain.dto.AuthenticationDto;
import com.garm.security.domain.dto.OtpDto;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Objects;

public class OtpLoginStep2 extends AbstractLogin {

    private final OtpCacheService cacheService;

    public OtpLoginStep2(String loginUrl, OtpCacheService otpCacheService) {
        super(loginUrl);
        this.cacheService = otpCacheService;
    }

    @Override
    public void validate(ServletRequest request, ServletResponse response, AuthenticationDto auth, HashMap<String, String[]> params) {

        boolean verified = false;
        if (auth.getOtp() != null) {
            OtpDto cachedDto = cacheService.get(auth.getOtp().getSmsIdentifier());
            if (Objects.nonNull(cachedDto)
                    && Objects.nonNull(cachedDto.getVerificationCode())
                    && Objects.nonNull(auth.getOtp().getVerificationCode())) {

                verified = cachedDto.getVerificationCode().equals(auth.getOtp().getVerificationCode());
                expireOtp(cacheService, auth.getOtp().getSmsIdentifier());
            }
        }
        if (!verified) {
            setError((HttpServletResponse) response, "security.otp.verify.failed", params);
        }
    }

}
