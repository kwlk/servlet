package com.responses;

public class Response {
    private String message;
    private int status;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
