package com.engagetech.infrastructure.aspect;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalControllerExceptionHandler {


    @ExceptionHandler(Exception.class) //one to rule them all !
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleAllExceptions(HttpServletRequest request, Exception e){

        log.info(String.format("General exception occured, URL: %s, message: %s",
                request.getRequestURL(),
                e.getMessage()));

        return Collections.singletonMap("error", "unexpected error, please contact engagetech support");
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class,
            HttpMessageNotReadableException.class,
            MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleDataException(HttpServletRequest request, Exception e){

        log.info(String.format("Data exception occured, URL: %s, cause: %s",
                request.getRequestURL(),
                e.getMessage()));

        return Collections.singletonMap("error", "malformed data error");
    }
}
