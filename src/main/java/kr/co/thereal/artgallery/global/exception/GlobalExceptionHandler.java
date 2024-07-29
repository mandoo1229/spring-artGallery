package kr.co.thereal.artgallery.global.exception;

import kr.co.thereal.artgallery.global.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach((error)-> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage != null ? errorMessage : "Invalid Input");
        });
        return ApiResponse.error(HttpStatus.BAD_REQUEST,errors);
    }


    @ExceptionHandler(InvalidInputException.class)
    public ApiResponse<?> handleInvalidInputException(InvalidInputException ex) {
        Map<String, String> errors = new HashMap<>();
        String errorMessage = ex.getMessage();
        errors.put(ex.getFieldName(), errorMessage != null ? errorMessage : "Invalid Input");

        return ApiResponse.error(HttpStatus.BAD_REQUEST,errors);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception ex) {
        Map<String, String> error = new HashMap<>();
        String errorMessage = ex.getMessage();
        error.put("미처리 예외", errorMessage !=null ? errorMessage:"Error");

        return ApiResponse.error(HttpStatus.BAD_REQUEST, error);
    }
}
