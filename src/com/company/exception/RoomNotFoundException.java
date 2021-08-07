package com.company.exception;

public class RoomNotFoundException extends Exception {
    public RoomNotFoundException(String message){
        super(message);
    }

    public RoomNotFoundException(String message, Exception e){
        super(message, e);
    }
}
