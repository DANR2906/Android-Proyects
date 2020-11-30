package com.diarioproyect.Model;

public class Dia {

    private String dia;
    private String fecha;

    public Dia(String dia, String fecha) {
        this.dia = dia;
        this.fecha = fecha;
    }

    public Dia(){

    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
