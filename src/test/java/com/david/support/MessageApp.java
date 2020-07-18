package com.david.support;

import com.david.support.service.MessageService;

public class MessageApp {
    private MessageService messageService;

    public MessageService getMessageService() {
        return messageService;
    }

    /**
     * Send a message to the address
     * @param message content of the message
     * @param address the address to send message to, it can be email address like abc@gmail.com or can be mobile number 12345678
     * @return the name of delivery service
     */
    public String sendMessage(String message, String address) {
        return messageService.send(message, address);
    }

    public void onDestroy() {
        System.out.println("MessageApp - Call me onDestroy!");
    }
}
