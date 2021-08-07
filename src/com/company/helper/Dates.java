package com.company.helper;

import java.util.Date;

public class Dates {
    private Date checkIn;
    private Date checkOut;

    public Dates(Date checkIn, Date checkOut) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }
}
