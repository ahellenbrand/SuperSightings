package com.sg.superherosightings.models;

import java.util.ArrayList;
import java.util.List;

public class Result<T> {
    
    private boolean success = true;
    private List<String> messages = new ArrayList<>();
    private T payload;
    
    public boolean isSuccess() {
        return success;
    }
    
    public List<String> getMessages() {
        return messages;
    }
    
    public Result<T> addMessage(String message) {
        this.success = false;
        this.messages.add(message);
        return this;
    }
    
    public T getPayload() {
        return payload;
    }
    
    public void setPayload(T payload) {
        this.payload = payload;
    }
    
}
