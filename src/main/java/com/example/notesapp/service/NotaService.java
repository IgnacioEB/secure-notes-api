package com.example.notesapp.service;

import com.example.notesapp.dto.NotaRequest;
import com.example.notesapp.model.Nota;
import com.example.notesapp.model.Usuario;
import com.example.notesapp.repository.NotaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotaService {

    private final NotaRepository notaRepository;

    public NotaService(NotaRepository notaRepository){
        this.notaRepository=notaRepository;
    }

    public Nota crearNota(NotaRequest request, Usuario usuario) {
           return notaRepository.save(new Nota(request.getTitulo(), request.getContenido(),usuario));
    }

    public List<Nota> listarPorUsuario(Usuario usuario) {
        return notaRepository.findAllByUsuarioId(usuario.getId());
    }
}
