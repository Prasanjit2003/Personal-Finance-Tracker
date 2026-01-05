package com.jit.financetracker.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
	public void ignoreSwaggerErrors() {
	    
	}

    // 400 ERROR
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            BadRequestException ex,
            HttpServletRequest request
    ) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    // IllegalArgumentException (email exists, validation logic)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request
    ) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    // 401 ERROR
    @ExceptionHandler({UnauthorizedException.class, BadCredentialsException.class})
    public ResponseEntity<ErrorResponse> handleUnauthorized(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        return buildResponse(ex.getMessage(), HttpStatus.UNAUTHORIZED, request);
    }

    // 404 ERROR
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request
    ) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    //  Validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(e -> e.getField() + " " + e.getDefaultMessage())
                .orElse("Validation error");

        return buildResponse(message, HttpStatus.BAD_REQUEST, request);
    }

    // RuntimeException (OTP / Email failures)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        ex.printStackTrace(); // FOR DEBUGGING
        return buildResponse(
                ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );
    }

    // Fallback
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(
            Exception ex,
            HttpServletRequest request
    ) {

        String path = request.getRequestURI();

        // Swagger / OpenAPI ERROR
        if (path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/swagger-ui.html")) {
            throw new RuntimeException(ex);
        }

        ex.printStackTrace();

        return buildResponse(
        		 ex.getMessage(),
//                "Internal server error",
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );
    }

    // Helper
    private ResponseEntity<ErrorResponse> buildResponse(
            String message,
            HttpStatus status,
            HttpServletRequest request
    ) {
        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI()
        );

        return new ResponseEntity<>(response, status);
    }
}
