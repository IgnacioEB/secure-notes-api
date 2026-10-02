package com.example.notesapp.controller;

import com.example.notesapp.dto.NotaRequest;
import com.example.notesapp.dto.NotaResponse;
import com.example.notesapp.model.Nota;
import com.example.notesapp.security.UsuarioDetailsImpl;
import com.example.notesapp.service.NotaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/notas")
public class NotaController {

    private final NotaService notaService;

    public NotaController(NotaService notaService){
        this.notaService=notaService;
    }
    @PostMapping("/nota")
    public ResponseEntity<NotaResponse> crear(@RequestBody NotaRequest request, Authentication authentication){
        UsuarioDetailsImpl usuarioDetails= (UsuarioDetailsImpl) authentication.getPrincipal();
        Nota nota= notaService.crearNota(request,usuarioDetails.getUsuario());//email
        return ResponseEntity.status(HttpStatus.CREATED).body(new NotaResponse(nota));
    }
    @GetMapping("/notas")
    public ResponseEntity<List<NotaResponse>> listar(Authentication authentication){
        UsuarioDetailsImpl usuarioDetails= (UsuarioDetailsImpl) authentication.getPrincipal();
        List<Nota> notas= notaService.listarPorUsuario(usuarioDetails.getUsuario());
        List<NotaResponse> notasResponse= new ArrayList<>();
        for (Nota n: notas){
            notasResponse.add(new NotaResponse(n));
        }
        return ResponseEntity.ok(notasResponse);
    }


}
