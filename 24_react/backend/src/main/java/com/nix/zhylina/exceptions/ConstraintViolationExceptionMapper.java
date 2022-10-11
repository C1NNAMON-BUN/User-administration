package com.nix.zhylina.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nix.zhylina.utils.Constants;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Component
@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        return Response.status(Response.Status.OK)
                .entity(jsonObject(exception))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

    @SneakyThrows
    private String jsonObject(ConstraintViolationException exception) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        ObjectNode errorsValues = mapper.createObjectNode();

        json.put(Constants.CONST_VALUE_STATUS_CODE, HttpStatus.BAD_REQUEST.value());

        for (ConstraintViolation<?> cv : exception.getConstraintViolations()) {
            errorsValues.put(cv.getPropertyPath().toString(), cv.getMessage());
        }
        json.set(Constants.CONST_ERROR, errorsValues);

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
    }

}