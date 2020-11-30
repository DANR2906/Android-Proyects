package com.example.vacacionesproyecto.NotasApp.Modelo;

import java.util.Date;

public class Nota {

    private String titulo;
    private String texto;
    private String fecha;

    public Nota(String titulo, String texto , String fecha){
        this.titulo = titulo;
        this.texto = texto;
        this.fecha = fecha;
    }

    public Nota(){

    }


    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
