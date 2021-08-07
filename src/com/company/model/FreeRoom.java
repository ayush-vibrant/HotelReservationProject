package com.company.model;

public class FreeRoom extends Room {
    private boolean isFree = true;

    public FreeRoom(String roomNumber, RoomType roomType) {
        super(roomNumber, Double.valueOf(0), roomType);
    }

    @Override
    public boolean isFree() {
        return isFree;
    }

    @Override
    public Double getRoomPrice() {
        return Double.valueOf(0);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
