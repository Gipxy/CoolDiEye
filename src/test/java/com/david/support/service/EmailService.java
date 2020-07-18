package com.david.support.service;

public class EmailService implements MessageService {

    public String send(String message, String address) {
        System.out.println("EmailService : message '" + message + "' was sent to email address '" + address + "'");
        return "EmailService";
    }

    public void onDestroy() {
        System.out.println("EmailService - Call me onDestroy!");
    }
}
