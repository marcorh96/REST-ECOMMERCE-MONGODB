package com.marcorh96.springboot.rest.ecommerce.app.controllers.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class AuthExceptionHandler {

    /*
     * @ExceptionHandler(value = { AuthenticationException.class })
     * public ResponseEntity<Object>
     * handleAuthenticationException(AuthenticationException ex, WebRequest request)
     * {
     * String message = "Username or Password is incorrect";
     * 
     * Map<String, Object> body = new LinkedHashMap<>();
     * body.put("timestamp", LocalDateTime.now());
     * body.put("status", HttpStatus.UNAUTHORIZED.value());
     * body.put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
     * body.put("message", message);
     * 
     * return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
     * }
     */

    @ExceptionHandler(value = { AuthenticationException.class })
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        String message = "";

        if (ex instanceof LockedException) {
            message = "Your account has been locked. Please contact support for assistance.";
        } else if (ex instanceof BadCredentialsException) {
            message = "Incorrect username or password. Please try again.";
        } else {
            message = "Authentication error: " + ex.getMessage();
        }

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.UNAUTHORIZED.value());
        body.put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
        body.put("message", message);

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            // El usuario está autenticado pero no tiene acceso
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("timestamp", LocalDateTime.now());
            body.put("status", HttpStatus.FORBIDDEN.value());
            body.put("error", HttpStatus.FORBIDDEN.getReasonPhrase());
            body.put("message", "You don't have permission to access this resource.");
            return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
        } else {
            // El usuario no está autenticado
            Map<String, Object> body = new LinkedHashMap<>();
            body.put("timestamp", LocalDateTime.now());
            body.put("status", HttpStatus.UNAUTHORIZED.value());
            body.put("error", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            body.put("message", "You must be authenticated to access this resource.");
            return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
        }
    }

}
