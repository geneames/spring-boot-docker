package io.sema.shuffle.controller.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public class ExceptionResponse {

    private HttpStatus status;

    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss a")
    private final LocalDateTime timeStamp;

    public ExceptionResponse(){
        this.timeStamp = LocalDateTime.now();
    }

    public ExceptionResponse(HttpStatus status){
        this();
        this.status = status;
    }

    public ExceptionResponse(HttpStatus status, String message){
        this();
        this.status = status;
        this.message = message;
    }

    public ExceptionResponse(HttpStatus status, String message, LocalDateTime timeStamp){
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExceptionResponse that = (ExceptionResponse) o;
        return status == that.status && Objects.equals(message, that.message) && Objects.equals(timeStamp, that.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, message, timeStamp);
    }
}
