package com.example.notesapp.repository;

import com.example.notesapp.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    Usuario findByEmail(String email);
}
