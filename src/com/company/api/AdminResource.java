package com.company.api;

import com.company.exception.CustomerNotFoundException;
import com.company.model.Customer;
import com.company.model.RoomInterface;
import com.company.service.CustomerService;
import com.company.service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {

    public static Customer getCustomer(String customerEmail) throws CustomerNotFoundException {
        return CustomerService.getCustomer(customerEmail);
    }

    public static void addRoom(List<RoomInterface> rooms) {
        rooms.forEach(ReservationService::addRoom);
    }

    public static Collection<RoomInterface> getAllRooms() {
        return ReservationService.getAllRooms();
    }

    public static Collection<Customer> getAllCustomers() {
        return CustomerService.getAllCustomers();
    }

    public static void displayAllReservations() {
        ReservationService.printAllReservation();
    }

}
