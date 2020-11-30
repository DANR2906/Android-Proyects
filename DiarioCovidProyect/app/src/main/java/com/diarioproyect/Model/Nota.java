package com.diarioproyect.Model;


import java.util.Date;

public class Nota {

    private String titulo;
    private String texto;
    private String fecha;
    private String dia;

    public Nota(String titulo, String texto , String fecha, String dia){
        this.titulo = titulo;
        this.texto = texto;
        this.fecha = fecha;
        this.dia = dia;
    }

    public Nota(){

    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
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

