package com.example.notesapp.dto;

import com.example.notesapp.model.Nota;

public class NotaResponse {
    private String titulo, contenido;

    public NotaResponse(Nota nota){
        this.contenido=nota.getContenido();
        this.titulo= nota.getTitulo();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
