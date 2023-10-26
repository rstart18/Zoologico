package com.nelumbo.zoo.responses.exceptions;

import com.nelumbo.zoo.dtos.responses.MessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.NoSuchElementException;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MessageDTO handleIllegalArgumentException(IllegalArgumentException ex) {
        ex.printStackTrace();
        return new MessageDTO(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MessageDTO handleValidationException(MethodArgumentNotValidException ex) {
        ex.printStackTrace();
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new MessageDTO(errorMessage);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public MessageDTO handleNoSuchElementException(NoSuchElementException ex) {
        ex.printStackTrace();
        return new MessageDTO(ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public MessageDTO handleIllegalStateException(IllegalStateException ex) {
        return new MessageDTO(ex.getMessage());
    }
}
