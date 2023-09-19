package com.example.Easy.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class CustomErrorController {
    private final ResourceBundleMessageSource source;

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity handleIllegalArgumentException(IllegalArgumentException e, WebRequest request){
        return  ResponseEntity.badRequest().body(source.getMessage("error.illegal.argument",null,LocaleContextHolder.getLocale()));
    }
    @ExceptionHandler(NullPointerException.class)
    ResponseEntity handleNullPointer( NullPointerException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBindingErrors(MethodArgumentNotValidException exception){
        List errorList = exception.getFieldErrors().stream().map(fieldErrors -> {
            Map<String,String> errorsMap = new HashMap<>();
            errorsMap.put(fieldErrors.getField(),fieldErrors.toString());
            return errorsMap;
        }).collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errorList);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity handleDataIntegrityViolationException(DataIntegrityViolationException e){
        return  ResponseEntity.badRequest().body("duplicate error");
    }
}
