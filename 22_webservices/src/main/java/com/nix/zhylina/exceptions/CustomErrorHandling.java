package com.nix.zhylina.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.ControllerAdvice;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

import static com.nix.zhylina.utils.Constants.CONST_VALUE_STATUS_CODE;

@ControllerAdvice
public class CustomErrorHandling {
    private CustomErrorHandling() {
    }

    public static Response toResponse(ApiError error) {
        return Response.status(Response.Status.OK)
                .entity(jsonObject(error))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @SneakyThrows
    public static String jsonObject(ApiError errors) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        ObjectNode errorValues = mapper.createObjectNode();

        json.put(CONST_VALUE_STATUS_CODE, errors.getStatus().value());

        for (Map.Entry<String, String> value : errors.getErrors().entrySet()) {
            errorValues.put(value.getKey(), value.getValue());
        }
        json.set(errors.getMessage(), errorValues);

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
    }
}
