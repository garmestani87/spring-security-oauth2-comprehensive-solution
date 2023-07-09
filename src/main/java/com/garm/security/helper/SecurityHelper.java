package com.garm.security.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.garm.security.cache.UserCacheService;
import com.garm.security.cache.UserInfoCacheService;
import com.garm.security.domain.dto.UserDetailsDto;
import com.garm.security.domain.dto.UserInfoDto;
import com.garm.security.util.SecurityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

import static com.garm.security.util.SecurityConstant.TOKEN_KEY;


@Component
public class SecurityHelper {

    private static UserInfoCacheService userInfoCache;
    private static UserCacheService userProfileCache;

    @Autowired
    private UserInfoCacheService userInfoCacheService;
    @Autowired
    private UserCacheService userProfileCacheService;

    @PostConstruct
    public void init() {
        userInfoCache = this.userInfoCacheService;
        userProfileCache = this.userProfileCacheService;
    }

    public static Cookie setCookie(final String Key, final String content) {
        return new Cookie(Key, content);
    }

    public static void removeCookie(HttpServletResponse response, String key) {
        Cookie cookie = setCookie(key, "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public static void clearCookie(HttpServletResponse response) {
        SecurityHelper.removeCookie(response, TOKEN_KEY);
    }

    public static void clearUserInfoCache(String username) {
        userInfoCache.put(username, new UserInfoDto());
        userProfileCache.put(username, new UserDetailsDto());
    }

    public static void setCharset(HttpServletResponse response) {
        response.setHeader("Content-type", "text/html; charset=utf-8");
    }

    public static boolean hasSuperAccess(String url) {
        return Arrays.asList(SecurityConstant.SpecialPath.LOGOUT).contains(url);
    }

    public static byte[] setResponse(final String content) throws JsonProcessingException {
        //set your response model
        return null;
    }

    public static boolean IsPasswordMatch(String password, String encodedPassword) {
        return new BCryptPasswordEncoder(8).matches(password, encodedPassword);
    }

    public static String EncryptPassword(String password) {
        return new BCryptPasswordEncoder(8).encode(password);
    }

    public static String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    public static String getClientId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ((OAuth2Authentication) auth).getOAuth2Request().getClientId();
    }
}
