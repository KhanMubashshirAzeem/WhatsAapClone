package com.example.whatsapp.Models;

public class Users {
    String profilePic, name, mail, password, Id, lastMessage;
    public Users(String profilePic, String name, String mail, String password, String id, String lastMessage) {
        this.profilePic = profilePic;
        this.name = name;
        this.mail = mail;
        this.password = password;
        Id = id;
        this.lastMessage = lastMessage;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Users(){}

    // SignUp constructor
    public Users(String name, String mail, String password) {
        this.name = name;
        this.mail = mail;
        this.password = password;
    }


    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
