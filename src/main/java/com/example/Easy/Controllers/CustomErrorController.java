package com.example.Easy.Controllers;

import com.example.Easy.Repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class CustomErrorController {
    private final DeviceRepository deviceRepository;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBindingErrors(MethodArgumentNotValidException exception){
        List errorList = exception.getFieldErrors().stream().map(fieldErrors -> {
            Map<String,String> errorsMap = new HashMap<>();
            errorsMap.put(fieldErrors.getField(),fieldErrors.toString());
            return errorsMap;
        }).collect(Collectors.toList());
        return ResponseEntity.badRequest().body(errorList);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ResponseEntity handleTypeMissmatch( MethodArgumentTypeMismatchException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
    @ExceptionHandler(NullPointerException.class)
    ResponseEntity handleNullPointer( NullPointerException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
    @ExceptionHandler(MissingPathVariableException.class)
    ResponseEntity handleMissingPathVariableException(MissingPathVariableException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
