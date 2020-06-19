package br.com.osworksapi.api.exceptionHandler;

import br.com.osworksapi.api.domain.exception.BusinessException;
import br.com.osworksapi.api.domain.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Locale;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handlerBusiness(BusinessException ex, WebRequest request) {
        var status = HttpStatus.BAD_REQUEST;

        var problem = new Problem();
        problem.setStatus(status.value());
        problem.setTitle(ex.getMessage());
        problem.setDateTime(OffsetDateTime.now());

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handlerEntityNotFound(BusinessException ex, WebRequest request) {
        var status = HttpStatus.NOT_FOUND;

        var problem = new Problem();
        problem.setStatus(status.value());
        problem.setTitle(ex.getMessage());
        problem.setDateTime(OffsetDateTime.now());

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        var fields = new ArrayList<Problem.Field>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String name = ((FieldError) error).getField();
            String msg = messageSource.getMessage(error, Locale.ENGLISH); // LocaleContextHolder

            fields.add(new Problem.Field(name, msg));
        }

        var problem = new Problem();
        problem.setStatus(status.value());
        problem.setTitle("one or two fields is invalid!");
        problem.setDateTime(OffsetDateTime.now());
        problem.setFields(fields);

        return super.handleExceptionInternal(ex, problem, headers, status, request);
    }
}
