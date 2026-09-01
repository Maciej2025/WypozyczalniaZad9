package org.example.spring;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> badRequest(IllegalArgumentException e){
        Map<String, String> odpowiedz = new HashMap<>();
        odpowiedz.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(odpowiedz);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> conflict(IllegalStateException e){
        Map<String, String> odpowiedz = new HashMap<>();
        odpowiedz.put("error", e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(odpowiedz);
    }
}