package com.diarioproyect.Model;

public class Comentario {

    private String texto;
    private String sender;
    private String postID;
    private String id;



    private boolean comentAdmin;

    public Comentario(String texto, String sender, String postID, String id) {
        this.texto = texto;
        this.sender = sender;
        this.postID = postID;
        this.id = id;
        comentAdmin = false;

    }

    public Comentario(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public boolean getComentAdmin() {
        return comentAdmin;
    }

    public void setComentAdmin(boolean comentAdmin) {
        this.comentAdmin = comentAdmin;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
