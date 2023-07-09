package com.garm.security.controller;


import com.garm.security.domain.dto.ImageCaptchaResultDto;
import com.garm.security.util.CaptchaGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CaptchaController {

    private final CaptchaGenerator captchaGenerator;

    @PostMapping("/resource/get-captcha")
    public ImageCaptchaResultDto getCaptcha() {
        return captchaGenerator.getCaptchaImage();
    }
}
