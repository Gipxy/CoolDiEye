package com.david.support.service;

public class SmsService implements MessageService {

    public String send(String message, String address) {
        System.out.println("SmsService : message '" + message + "' was sent to mobile number '" + address + "'");
        return "SmsService";
    }
}
