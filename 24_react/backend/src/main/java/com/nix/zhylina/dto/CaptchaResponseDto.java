package com.nix.zhylina.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.nix.zhylina.utils.ErrorMessagesConstants;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "success",
        "challenge_ts",
        "hostname",
        "error-codes"
})
public class CaptchaResponseDto {
    @JsonProperty("success")
    private boolean success;

    @JsonProperty("challenge_ts")
    private String challengeTs;

    @JsonProperty("hostname")
    private String hostname;

    @JsonProperty("error-codes")
    private ErrorCode[] errorCodes;

    enum ErrorCode {
        MISSING_SECRET, INVALID_SECRET,
        MISSING_RESPONSE, INVALID_RESPONSE;

        private static final Map<String, ErrorCode> errorsMap = Map.of(ErrorMessagesConstants.ERROR_CAPTCHA_MISSING_SECRET, MISSING_SECRET,
                ErrorMessagesConstants.ERROR_CAPTCHA_INVALID_SECRET, INVALID_SECRET , ErrorMessagesConstants.ERROR_CAPTCHA_MISSING_RESPONSE, MISSING_RESPONSE,
                ErrorMessagesConstants.ERROR_CAPTCHA_INPUT_RESPONSE, INVALID_RESPONSE);

        @JsonCreator
        public static ErrorCode forValue(String value) {
            return errorsMap.get(value.toLowerCase());
        }
    }
}
