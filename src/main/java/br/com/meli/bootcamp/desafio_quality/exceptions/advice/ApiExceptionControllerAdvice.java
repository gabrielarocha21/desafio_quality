package br.com.meli.bootcamp.desafio_quality.exceptions.advice;

import br.com.meli.bootcamp.desafio_quality.exceptions.DistrictNotFoundException;
import br.com.meli.bootcamp.desafio_quality.exceptions.StandardError;
import br.com.meli.bootcamp.desafio_quality.exceptions.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ApiExceptionControllerAdvice {

    @Autowired
    private MessageSource messageSource; // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/context/MessageSource.html

    @ExceptionHandler(DistrictNotFoundException.class)
    public ResponseEntity<StandardError> districtNotFound(DistrictNotFoundException e, HttpServletRequest request) {
        StandardError err = new StandardError(System.currentTimeMillis(),
                HttpStatus.NOT_FOUND.value(), "District not found", "Bairro não localizado no sistema", request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> districtNotFound(MethodArgumentNotValidException e, HttpServletRequest request) {
        ValidationError err = new ValidationError(System.currentTimeMillis(),
                HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error",
//                e.getMessage()
                "Erro de validação na requisição.",
                request.getRequestURI());
        for (FieldError x : e.getBindingResult().getFieldErrors()) {
            err.addError(x.getField(), x.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
    }
}