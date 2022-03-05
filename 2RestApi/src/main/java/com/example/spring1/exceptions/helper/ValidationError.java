package com.example.spring1.exceptions.helper;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.security.Timestamp;
import java.time.LocalTime;
import java.util.Date;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ValidationError {
    int status;
//    String message;
    long timestamp;
    String path;
    Map<String, String> validationErrors;

    public ValidationError(HttpStatus status, String path) {
        this.status = status.value();
//        this.message = message;
        this.path = path;
        timestamp = System.currentTimeMillis();
    }
}
