package com.example.whatsapp.Adapters;

public class MessagesModel {
    String id , message;
    Long timeStamp;

    public MessagesModel(String id, String message, Long timeStamp) {
        this.id = id;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public MessagesModel(String id, String message) {
        this.id = id;
        this.message = message;
    }

    public MessagesModel()
    {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
