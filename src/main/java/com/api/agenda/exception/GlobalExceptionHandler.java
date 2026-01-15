package com.api.agenda.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Este metodo captura o "throw new RuntimeException" do Service
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<java.util.Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        var body = new java.util.HashMap<String, Object>();
        body.put("timestamp", java.time.Instant.now().toString());
        body.put("status", 400);
        body.put("error", "Bad Request");
        body.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

    // Este metodo captura erros de campos vazios (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationError(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(erros);
    }

    // Este metodo captura erros de acesso negado
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDenied() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Acesso Negado: Você não tem permissão para realizar esta ação. Apenas manicures podem cadastrar serviços.");
    }
}