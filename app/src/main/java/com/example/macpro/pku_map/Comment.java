package com.example.macpro.pku_map;

public class Comment {

    public int commentID;
    public String content;
    public int fatherType;
    public int fatherID;

    public int getCommentID() {
        return this.commentID;
    }
    public String getContent() {
        return this.content;
    }
    public int getFatherType() {
        return this.fatherType;
    }
    public int getFatherID() {
        return this.fatherID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setFatherType(int fatherType) {
        this.fatherType = fatherType;
    }
    public void setFatherID(int fatherID) {
        this.fatherID = fatherID;
    }
}
