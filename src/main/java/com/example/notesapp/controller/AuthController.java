package com.example.notesapp.controller;

import com.example.notesapp.dto.LoginRequest;
import com.example.notesapp.dto.RegistroRequest;
import com.example.notesapp.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService=authService;
    }


    @PostMapping("/registrar")
    public ResponseEntity<Void> registrarUsuario(@Valid @RequestBody RegistroRequest registroRequest){
        authService.registrarUsuario(registroRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> loguearUsuario(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.loguearUsuario(loginRequest));
    }


}
