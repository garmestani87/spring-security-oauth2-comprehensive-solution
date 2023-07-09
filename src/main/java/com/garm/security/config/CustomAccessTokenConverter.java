package com.garm.security.config;

import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.LinkedHashMap;
import java.util.Map;

public class CustomAccessTokenConverter extends JwtAccessTokenConverter {

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {
        OAuth2Authentication authentication = super.extractAuthentication(claims);
        authentication.setDetails(claims);
        return authentication;
    }

    @SneakyThrows
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

        SecureRandom secureRandom = new SecureRandom();

        byte[] randomFgp = new byte[50];
        secureRandom.nextBytes(randomFgp);
        String userFingerprint = DatatypeConverter.printHexBinary(randomFgp);

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] userFingerprintDigest = digest.digest(userFingerprint.getBytes(StandardCharsets.UTF_8));
        String userFingerprintHash = DatatypeConverter.printHexBinary(userFingerprintDigest);


        User user = (User) authentication.getPrincipal();
        Map<String, Object> info = new LinkedHashMap<String, Object>(accessToken.getAdditionalInformation());
        info.put("userFingerprint", userFingerprintHash);
        DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
        customAccessToken.setAdditionalInformation(info);


        return super.enhance(customAccessToken, authentication);
    }


}