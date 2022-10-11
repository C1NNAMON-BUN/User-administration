package com.nix.zhylina.services;

import com.nix.zhylina.dto.CaptchaResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.net.URI;

import static com.nix.zhylina.utils.SpringConfigConstants.ANNOTATION_SPRING_PROPERTY_SOURCE;
import static com.nix.zhylina.utils.SpringConfigConstants.CAPTCHA_SECRET_BACKEND;
import static com.nix.zhylina.utils.SpringConfigConstants.CAPTCHA_URL;

@Component
@PropertySource(ANNOTATION_SPRING_PROPERTY_SOURCE)
public class CaptchaService implements ICaptchaService {
    private final Environment environment;
    private final RestOperations restTemplate;

    @Autowired
    public CaptchaService(Environment environment, RestOperations restTemplate) {
        this.environment = environment;
        this.restTemplate = restTemplate;
    }

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