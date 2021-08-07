package com.company.model;

import java.util.Date;

public class Reservation {
    private Customer customer;
    private RoomInterface room;
    private Date checkIn;
    private Date checkOut;

    public Reservation(Customer customer, RoomInterface room, Date checkIn, Date checkOut) {
        this.customer = customer;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public Customer getCustomer() {
        return customer;
    }

    public RoomInterface getRoom() {
        return room;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setRoom(RoomInterface room) {
        this.room = room;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    @Override
    public String toString() {
        return "\n" + customer +
                "\n" + room +
                "\ncheckIn = " + checkIn +
                "\ncheckOut = " + checkOut +
                '}' + "\n";
    }

    public boolean isRoomAvailable(Date checkIn, Date checkOut) {
        return checkIn.before(this.checkIn) && checkOut.before(this.checkIn)
                || checkIn.after(this.checkOut) && checkOut.after(this.checkOut);
    }
}
