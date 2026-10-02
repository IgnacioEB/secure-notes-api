package com.example.notesapp.repository;

import com.example.notesapp.model.Nota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotaRepository extends JpaRepository<Nota, Long> {
    Nota findNotaById(Long id);


    @NativeQuery("SELECT * FROM notas  WHERE usuario_id=?1")
    List<Nota> findAllByUsuarioId(Long id);
}
