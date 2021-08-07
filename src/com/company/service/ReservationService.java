package com.company.service;

import com.company.exception.NoRoomsAddedException;
import com.company.exception.RoomNotFoundException;
import com.company.model.Customer;
import com.company.model.Reservation;
import com.company.model.RoomInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ReservationService {

    private final static ReservationService SINGLETON = new ReservationService();

    private static Map<String, Collection<Reservation>> reservations = new HashMap<>();
    private static Map<String, RoomInterface> rooms = new HashMap<>();

    private ReservationService() {
    }

    public static ReservationService getInstance() {
        return SINGLETON;
    }


    public static void addRoom(RoomInterface room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public static RoomInterface getARoom(String roomId) throws RoomNotFoundException {
        RoomInterface room = rooms.get(roomId);
        if (room != null)
            return room;
        throw new RoomNotFoundException("Room not found with room number: " + roomId);
    }

    public static Reservation reserveARoom(Customer customer, RoomInterface room, Date checkIn, Date checkOut) {
        if (isRoomReserved(room, checkIn, checkOut))
            return null;

        Reservation reservation = new Reservation(customer, room, checkIn, checkOut);
        Collection<Reservation> customersReservation = getCustomersReservation(customer);

        if (customersReservation == null)
            customersReservation = new LinkedList<>();

        customersReservation.add(reservation);
        reservations.put(customer.getEmail(), customersReservation);

        return reservation;
    }


    public static Collection<RoomInterface> findRooms(Date checkIn, Date checkOut) throws NoRoomsAddedException {
        // get all reserved room within the given range from reservations.
        Collection<RoomInterface> reservedRooms = getAllReservedRooms(checkIn, checkOut);

        // get all other rooms which aren't reserved
        Collection<RoomInterface> availableRooms = new ArrayList<>();
        for (RoomInterface room : getAllRooms()) {
            if (!reservedRooms.contains(room))
                availableRooms.add(room);
        }
        if(reservedRooms.isEmpty() && availableRooms.isEmpty())
            throw new NoRoomsAddedException("No rooms added in the hotel, add rooms first!");
        return availableRooms;
    }

    private static Collection<RoomInterface> getAllReservedRooms(Date checkIn, Date checkOut) {
        Collection<RoomInterface> reservedRooms = new ArrayList<>();
        for (Reservation r : getAllReservations()) {
            if (!r.isRoomAvailable(checkIn, checkOut))
                reservedRooms.add(r.getRoom());
        }
        return reservedRooms;
    }

    private static Collection<Reservation> getAllReservations() {
        Collection<Reservation> res = new ArrayList<>();
        reservations.forEach((k, v) -> res.addAll(v));
        return res;
    }

    public static Collection<Reservation> getCustomersReservation(Customer customer) {
        return reservations.get(customer.getEmail());
    }

    public static Collection<RoomInterface> getAllRooms() {
        return rooms.values();
    }

    private static boolean isRoomReserved(RoomInterface room, Date checkIn, Date checkOut) {
        Collection<RoomInterface> allReservedRooms = getAllReservedRooms(checkIn, checkOut);
        return allReservedRooms.contains(room);
    }

    public static void printAllReservation() {
        if (reservations.isEmpty())
            System.out.println("[]");
        else {
            for (Map.Entry entry : reservations.entrySet()) {
                System.out.println("------------");
                System.out.println("Customer email: " + entry.getKey());
                System.out.println("Reservation details: ");
                System.out.println(entry.getValue());
            }
        }
    }
}
