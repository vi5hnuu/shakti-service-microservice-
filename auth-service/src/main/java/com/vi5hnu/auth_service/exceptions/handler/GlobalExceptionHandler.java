package com.vi5hnu.auth_service.exceptions.handler;

import com.vi5hnu.auth_service.enums.Environment;
import com.vi5hnu.auth_service.exceptions.UserAlreadyExistsException;
import com.vi5hnu.auth_service.exceptions.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Value("${spring.servlet.multipart.max-request-size}") String maxRequestSize;
    @Value("$environment") String environment;

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleMaxRequestSizeException(IllegalStateException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof MultipartException) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", String.format("size should be below %s", maxRequestSize));
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        throw ex;
    }
    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<Map<String,Object>> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("success",false,"message",ex.getMessage()));
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String,Object>> userAlreadyExists(UserAlreadyExistsException ex, WebRequest webRequest){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("success",false,"message",ex.getMessage()));
    }
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Map<String,Object>> apiException(ApiException ex, WebRequest webRequest){
        return ResponseEntity.status(ex.getHttpStatus()).body(Map.of("success",false,"message", environment.equals(Environment.development.getValue()) ? ex.getStackTrace() : ex.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        StringBuilder errorBuilder=new StringBuilder();
        final var errors=ex.getBindingResult().getFieldErrors();
        for (int errIdx=0;errIdx<errors.size();errIdx++) {
            errorBuilder.append(String.format("%s%s",errors.get(errIdx).getDefaultMessage(),errIdx-1<errors.size() ? ", ":"."));
        }
        return ResponseEntity.badRequest().body(Map.of("success",false,"message","validation failed","error",errorBuilder.toString()));
    }
}
