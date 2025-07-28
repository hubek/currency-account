package net.hubek.nn.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Value;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Value
public class ErrorResponse {

    int status;
    String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime timestamp;

    public ErrorResponse(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

}