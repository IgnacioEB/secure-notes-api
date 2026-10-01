package com.example.notesapp.security;

import com.example.notesapp.model.Usuario;
import com.example.notesapp.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository){
        this.usuarioRepository=usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario= usuarioRepository.findByEmail(email);
        if(usuario==null){
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        return new UsuarioDetailsImpl(usuario);
    }

}
