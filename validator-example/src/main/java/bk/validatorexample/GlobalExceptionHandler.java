package bk.validatorexample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final HttpHeaders headers,
                                                                  final HttpStatus status,
                                                                  final WebRequest request) {
        log(ex, status, request, headers);
        final var body = new LinkedHashMap<String, Object>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status);
        final var errors = ex.getBindingResult()
                             .getFieldErrors()
                             .stream()
                             .map(DefaultMessageSourceResolvable::getDefaultMessage)
                             .collect(toList());
        body.put("errors", errors);
        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleIllegalArgumentException(final IllegalArgumentException ex,
                                                                 final ServerWebExchange request) {
        final var body = new LinkedHashMap<String, Object>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", BAD_REQUEST);
        body.put("errors", ex.getMessage());
        return new ResponseEntity<>(body, new HttpHeaders(), BAD_REQUEST);
    }

    private void log(final Throwable throwable, final HttpStatus status, final WebRequest webRequest, final HttpHeaders headers) {
        final var request = ((ServletWebRequest) webRequest).getRequest();
        if (status.is4xxClientError()) {
            log.info("status: {}, path: {}, message: {}, headers: {}",
                    status, request.getRequestURI(), throwable.getMessage(), headers);
        } else if (status.is5xxServerError()) {
            log.error("status: {}, path: {}, message: {}, headers: {}",
                    status, request.getRequestURI(), throwable.getMessage(), headers, throwable);
        }
    }
}
