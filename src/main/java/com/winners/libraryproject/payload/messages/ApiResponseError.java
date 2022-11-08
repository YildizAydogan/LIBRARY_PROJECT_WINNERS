package com.winners.libraryproject.payload.messages;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiResponseError {

    private HttpStatus status;
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="dd-MM-yyyy hh:mm:ss" )
    private final LocalDateTime timestamp;
    private String message;
    private String requestURI;

    private ApiResponseError(){
        timestamp=LocalDateTime.now();
    }

    public ApiResponseError(HttpStatus status){
        this();
        this.status=status;
    }

    public ApiResponseError(HttpStatus status, String message, String requestURI){
        this(status);
        this.message=message;
        this.requestURI=requestURI;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
