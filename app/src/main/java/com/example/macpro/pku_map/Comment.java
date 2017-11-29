package com.example.macpro.pku_map;

public class Comment {

    public int commentID;
    public String content;
    public String fatherType;
    public int fatherID;
    public int publisherID;
    public String username;

    public int getCommentID() {
        return this.commentID;
    }
    public String getContent() {
        return this.content;
    }
    public String getFatherType() {
        return this.fatherType;
    }
    public int getFatherID() {
        return this.fatherID;
    }
    public int getPublisherID() {
        return this.publisherID;
    }
    public String getUsername() {
        return this.username;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setFatherType(String fatherType) {
        this.fatherType = fatherType;
    }
    public void setFatherID(int fatherID) {
        this.fatherID = fatherID;
    }
    public void setPublisherID(int publisherID) {
        this.publisherID = publisherID;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
