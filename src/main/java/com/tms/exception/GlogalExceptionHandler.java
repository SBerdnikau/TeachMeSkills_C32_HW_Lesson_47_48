package com.tms.exception;

import com.tms.controller.UserController;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlogalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlogalExceptionHandler.class);

    @ExceptionHandler(value = AgeException.class)
    public String ageExceptionHandler(AgeException exception, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_CONFLICT);
        return exception.getMessage();
    }

    @ExceptionHandler(value = Exception.class)
    public String allExceptionsHandler(Exception exception, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_CONFLICT);
        return exception.getMessage();
    }

    @ExceptionHandler(value = LoginUsedException.class)
    public ResponseEntity<String> loginUsedHandler(LoginUsedException exception) {
        logger.error("Login is problem -> " + exception.getMessage());
        return  new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
