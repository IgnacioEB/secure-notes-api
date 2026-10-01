package com.example.notesapp.service;

import com.example.notesapp.dto.LoginRequest;
import com.example.notesapp.dto.RegistroRequest;
import com.example.notesapp.exception.CredencialesInvalidasException;
import com.example.notesapp.exception.EmailYaRegistradoException;
import com.example.notesapp.model.Usuario;
import com.example.notesapp.repository.UsuarioRepository;
import com.example.notesapp.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public void registrarUsuario(RegistroRequest request){
        if(usuarioRepository.findByEmail(request.getEmail()).isPresent()){
            throw new EmailYaRegistradoException("El email ya esta en uso: "+request.getEmail());
        }
        String hashPassword= passwordEncoder.encode(request.getPassword());

        Usuario usuario= new Usuario(request.getEmail(),hashPassword, Usuario.Rol.USER);

        usuarioRepository.save(usuario);
    }

    public String loguearUsuario(LoginRequest request){
        Usuario usuario= usuarioRepository
                .findByEmail(request.getEmail())
                .orElseThrow(()-> new CredencialesInvalidasException("Email o contraseña incorrecta"));
        if(!passwordEncoder.matches(request.getPassword(),usuario.getPassword())){
            throw new CredencialesInvalidasException("Email o contraseña incorrecta");
        }
        return jwtService.generarToken(usuario.getEmail(),usuario.getRol().name());
    }

}
