package com.diarioproyect.Model;

public class Post {

    private String id;
    private String sender;
    private String mensaje;
    private String animo;

    private boolean postAdmin;
    private boolean estado_like;



    private Comentario comentario;
    private Like like;

    public Post(String id, String sender, String mensaje, String animo, Comentario comentario, Like like) {
        this.id = id;
        this.sender = sender;
        this.mensaje = mensaje;
        this.animo = animo;
        this.comentario = comentario;
        this.like = like;
        postAdmin = false;
        estado_like = false;
    }

    public Post(){
        postAdmin = false;
        estado_like = false;
    }

    public boolean isEstado_like() {
        return estado_like;
    }

    public void setEstado_like(boolean estado_like) {
        this.estado_like = estado_like;
    }

    public Like getLike() {
        return like;
    }

    public void setLike(Like like) {
        this.like = like;
    }

    public boolean isPostAdmin() {
        return postAdmin;
    }

    public void setPostAdmin(boolean postAdmin) {
        this.postAdmin = postAdmin;
    }

    public Comentario getComentario() {
        return comentario;
    }

    public void setComentario(Comentario comentario) {
        this.comentario = comentario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getAnimo() {
        return animo;
    }

    public void setAnimo(String animo) {
        this.animo = animo;
    }
}
