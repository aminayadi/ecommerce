package com.ecommerce.chatmanager.domain.enumeration;

public enum MessageType {
    MESSAGE("MESSAGE"),
    TYPING("TYPING"),
    JOINED("JOINED"),
    LEFT("LEFT");

    private final String messageType;

    MessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getType() {
        return messageType;
    }
}
