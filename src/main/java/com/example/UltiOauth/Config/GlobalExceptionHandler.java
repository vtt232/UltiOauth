package com.example.UltiOauth.Config;

import com.example.UltiOauth.Exception.NoteNotFoundException;
import com.example.UltiOauth.Exception.RepoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({RepoNotFoundException.class, NoteNotFoundException.class})
    public ResponseEntity<String> handleRepoNotFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}