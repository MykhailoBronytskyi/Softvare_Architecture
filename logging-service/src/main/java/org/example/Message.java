package org.example;

import java.util.UUID;

public class Message {
    public UUID id;
    public String text;

    @Override
    public String toString() {
        return "Message { id = " + id + ", text = '" + text + "'}";
    }
    Message(UUID id, String text){
        this.id = id;
        this.text = text;
    }
}