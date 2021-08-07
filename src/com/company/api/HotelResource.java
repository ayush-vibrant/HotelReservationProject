package com.company.api;

import com.company.exception.CustomerNotFoundException;
import com.company.exception.NoRoomsAddedException;
import com.company.exception.RoomNotFoundException;
import com.company.model.Customer;
import com.company.model.Reservation;
import com.company.model.RoomInterface;
import com.company.service.CustomerService;
import com.company.service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {
    public static Customer getCustomer(String email) throws CustomerNotFoundException {
        return CustomerService.getCustomer(email);
    }

    public static void createACustomer(String email, String firstName, String lastName) {
        CustomerService.addCustomer(email, firstName, lastName);
    }

    public static RoomInterface getRoom(String roomNumber) throws RoomNotFoundException {
        return ReservationService.getARoom(roomNumber);
    }

    public static Reservation bookARoom(String customerEmail, RoomInterface room, Date checkIn, Date checkOut) throws CustomerNotFoundException {
        Customer customer = CustomerService.getCustomer(customerEmail);
        return ReservationService.reserveARoom(customer, room, checkIn, checkOut);
    }

    public static Collection<Reservation> getCustomersReservation(String customerEmail) throws CustomerNotFoundException {
        Customer customer = CustomerService.getCustomer(customerEmail);
        return ReservationService.getCustomersReservation(customer);
    }

    public static Collection<RoomInterface> findARoom(Date checkIn, Date checkOut) throws NoRoomsAddedException {
        return ReservationService.findRooms(checkIn, checkOut);
    }
}
