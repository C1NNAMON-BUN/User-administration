package com.nix.zhylina.services;

public interface ICaptchaService {
    Boolean processResponse(final String response);
}
