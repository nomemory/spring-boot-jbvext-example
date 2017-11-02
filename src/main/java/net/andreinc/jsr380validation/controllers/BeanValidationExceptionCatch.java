package net.andreinc.jsr380validation.controllers;


import net.andreinc.jbvext.utils.BeanValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class BeanValidationExceptionCatch extends ResponseEntityExceptionHandler {

    /**
     * This method acts as a "Global" catch block for all the exceptions of type BeanValidationException.
     * We will use to identify all the "mal-formatted" requests that are made against our REST API and treat them
     * in a generic way.
     *
     * @param ex Can be cast to (BeanValidationException).
     * @param webRequest The webRequest.
     * @return A ResponseEntity that is a BAD_REQUEST and contains the error message supplied by the JSR-380 validation.
     */
    @ExceptionHandler(value = {BeanValidationException.class})
    public ResponseEntity<?> handleValidationException(RuntimeException ex, WebRequest webRequest) {
        BeanValidationException exception = (BeanValidationException) ex;
        return handleExceptionInternal(exception, exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest);
    }

}
