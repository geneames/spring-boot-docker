package io.sema.shuffle.controller.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DeckNotFoundException.class)
    public ResponseEntity<Object> handleDeckNotFoundException(HttpServletRequest request, DeckNotFoundException ex){
        return  this.buildResponse(new ExceptionResponse(HttpStatus.NOT_FOUND, ex.getMessage()));
    }

    private ResponseEntity<Object> buildResponse(ExceptionResponse exceptionResponse){
        return new ResponseEntity<>(exceptionResponse, exceptionResponse.getStatus());
    }
}
