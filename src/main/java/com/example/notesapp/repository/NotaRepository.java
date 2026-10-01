package com.example.notesapp.repository;

import com.example.notesapp.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotaRepository extends JpaRepository<Nota, Long> {
    Nota findNotaById(Long id);
    
}
