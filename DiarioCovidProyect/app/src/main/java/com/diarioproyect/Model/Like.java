package com.diarioproyect.Model;

public class Like {

    private String sender;
    private String postID;
    private String id;

    private boolean likeAdmin;

    public Like(String sender, String postID, String id) {
        this.sender = sender;
        this.postID = postID;
        this.id = id;
    }

    public Like(){

    }

    public boolean isLikeAdmin() {
        return likeAdmin;
    }

    public void setLikeAdmin(boolean likeAdmin) {
        this.likeAdmin = likeAdmin;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
