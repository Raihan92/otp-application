package com.progcreek.otpapplication.exception;

import com.progcreek.otpapplication.global.ResponseCodes;
import com.progcreek.otpapplication.global.ResponseMessages;
import com.progcreek.otpapplication.response.Reply;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        List<String> errors = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult()
                .getFieldErrors()) {
            String defaultMessage = fieldError.getDefaultMessage();
            errors.add(defaultMessage);
        }

        Reply reply = new Reply(false, ResponseCodes.Failed, ResponseMessages.Failed,  errors.get(0), null);
        return new ResponseEntity(reply, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolationException(Exception ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        Reply reply = new Reply(false, ResponseCodes.BadRequest, ResponseMessages.BadRequest, ex.getMessage(),null);
        return new ResponseEntity(reply, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        Reply reply = new Reply(false, ResponseCodes.Failed, ResponseMessages.Failed, errors.get(0),null);
        return new ResponseEntity(reply, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ValidationException.class)
    public final ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        Reply reply = new Reply(false, ResponseCodes.BadRequest, ResponseMessages.BadRequest, errors.get(0), null);
        return new ResponseEntity(reply, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public final ResponseEntity<Object> handleNullPointerException(NullPointerException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        Reply reply = new Reply(false, ResponseCodes.BadRequest, ResponseMessages.BadRequest, errors.get(0),null);
        return new ResponseEntity(reply, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handleRunTimeException(RuntimeException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        Reply reply = new Reply(false, ResponseCodes.Failed, ResponseMessages.Failed, errors.get(0),null);
        return new ResponseEntity(reply, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
