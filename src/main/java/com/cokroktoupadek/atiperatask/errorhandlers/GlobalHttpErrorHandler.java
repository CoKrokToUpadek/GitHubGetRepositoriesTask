package com.cokroktoupadek.atiperatask.errorhandlers;

import com.cokroktoupadek.atiperatask.domain.dto.response.output.ExceptionResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoUserFoundException.class)
    public ResponseEntity<ExceptionResponseDto> noUserFoundExceptionHandler() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(new ExceptionResponseDto(404, ErrorMessagesEnum.USER_NOT_FOUND.value),headers, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotAcceptableException.class)
    public ResponseEntity<ExceptionResponseDto> requestNotAcceptableExceptionHandler() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(new ExceptionResponseDto(406, ErrorMessagesEnum.NOT_ACCEPTABLE.value), headers, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(InternalApplicationException.class)
    public ResponseEntity<ExceptionResponseDto> internalApplicationExceptionHandler() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(new ExceptionResponseDto(500, ErrorMessagesEnum.INTERNAL_SERVER_ERROR.value),headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
