package com.garm.security.login;

import com.garm.security.cache.CaptchaCacheService;
import com.garm.security.domain.dto.AuthenticationDto;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Objects;

import static com.garm.security.util.SecurityConstant.CAPTCHA_KEY;

public class WebLogin extends AbstractLogin {

    private final CaptchaCacheService captchaCache;

    public WebLogin(String loginUrl, CaptchaCacheService captchaCache) {
        super(loginUrl);
        this.captchaCache = captchaCache;
    }

    @Override
    public void validate(ServletRequest request, ServletResponse response, final AuthenticationDto auth, HashMap<String, String[]> params) {
        String captcha_key = ((HttpServletRequest) request).getHeader(CAPTCHA_KEY);

        if (captcha_key == null
                || StringUtils.isEmpty(auth.getCaptcha())
                || Objects.isNull(captchaCache.get(captcha_key))
                || Objects.isNull(captchaCache.get(captcha_key).getAnswer())
                || !captchaCache.get(captcha_key).getAnswer().equals(auth.getCaptcha())) {

            expireCaptcha(captchaCache, captcha_key);
            setError((HttpServletResponse) response, "security.captcha.failed", params);
        }
    }

}
