package fr.uparis.backapp.web;

import java.time.LocalDateTime;

public class Message {

    private String author;
    private LocalDateTime timeStamp;
    private String message;

    public Message(String author, String message){
        this.timeStamp = LocalDateTime.now();
        this.message = message;
        this.author = author;
    }

    public Message(){

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public String getAuthor() {
        return author;
    }
}
