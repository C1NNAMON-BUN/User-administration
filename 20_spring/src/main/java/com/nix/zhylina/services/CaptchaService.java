package com.nix.zhylina.services;

import com.nix.zhylina.dto.CaptchaResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestOperations;

import java.net.URI;

import static com.nix.zhylina.utils.SpringConfigConstants.ANNOTATION_SPRING_PROPERTY_SOURCE;
import static com.nix.zhylina.utils.SpringConfigConstants.CAPTCHA_SECRET_BACKEND;
import static com.nix.zhylina.utils.SpringConfigConstants.CAPTCHA_URL;

@PropertySource(ANNOTATION_SPRING_PROPERTY_SOURCE)
public class CaptchaService implements ICaptchaService {
    @Autowired
    private org.springframework.core.env.Environment environment;
    @Autowired
    private RestOperations restTemplate;

    @Override
    public Boolean isProcessResponse(String response) {
        URI verifyUri = URI.create(String.format(CAPTCHA_URL, environment.getProperty(CAPTCHA_SECRET_BACKEND), response));
        CaptchaResponseDto googleResponse = restTemplate.getForObject(verifyUri, CaptchaResponseDto.class);

        if (googleResponse != null) {
            return googleResponse.isSuccess();
        } else {
            return false;
        }
    }
}