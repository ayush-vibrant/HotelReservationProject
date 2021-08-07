package com.company;

import com.company.api.AdminResource;
import com.company.api.HotelResource;
import com.company.exception.CustomerNotFoundException;
import com.company.helper.Helper;
import com.company.model.Customer;
import com.company.model.FreeRoom;
import com.company.model.Room;
import com.company.model.RoomInterface;
import com.company.model.RoomType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    public static void display() {
        System.out.println("Admin Menu");
        System.out.println("----------------------------------");
        System.out.println("1. See all customers");
        System.out.println("2. See all rooms");
        System.out.println("3. See all reservations");
        System.out.println("4. Add a room");
        System.out.println("5. Add test data");
        System.out.println("6. Back to Main Menu");
        System.out.println("----------------------------------");
        System.out.println("Please select a number for the menu option \n");
    }

    public static boolean runAdminMenu(Scanner scanner, int option) {
        boolean keepAdminMenuRunning = true;
        switch (option) {
            case 1 -> getAllCustomers();
            case 2 -> getAllRooms();
            case 3 -> getAllReservations();
            case 4 -> addARoom(scanner);
            case 5 -> addTestData();
            case 6 -> keepAdminMenuRunning = false;
            default -> System.out.println("Please enter a number between 1 and 6! \n");
        }
        return keepAdminMenuRunning;
    }

    private static void getAllReservations() {
        AdminResource.displayAllReservations();
    }

    private static void getAllRooms() {
        Collection<RoomInterface> allRooms = AdminResource.getAllRooms();
        if (allRooms.isEmpty())
            System.out.println("[]");
        allRooms.forEach(System.out::println);
    }

    private static void getAllCustomers() {
        Collection<Customer> allCustomers = AdminResource.getAllCustomers();
        if (allCustomers.isEmpty())
            System.out.println("[]");
        allCustomers.forEach(System.out::println);
    }

    private static void addARoom(Scanner scanner) {
        // can put this into an entirely different function
        boolean keepRunning = true;
        while (keepRunning) {
            try {
                // get room details
                System.out.println("Enter room number");
                int roomNumber = Integer.parseInt(scanner.nextLine());

                System.out.println("Enter price per night");
                Double roomPrice = Double.valueOf(scanner.nextLine());

                System.out.println("Enter room type: 1 for single bed, 2 for double bed");
                String roomType = scanner.nextLine();

                RoomType type;
                if (roomType.equalsIgnoreCase("1"))
                    type = RoomType.SINGLE;
                else if (roomType.equalsIgnoreCase("2"))
                    type = RoomType.DOUBLE;
                else
                    throw new Exception();

                // add room
                RoomInterface room;
                if (roomPrice != 0)
                    room = new Room(String.valueOf(roomNumber), roomPrice, type);
                else
                    room = new FreeRoom(String.valueOf(roomNumber), type);

                AdminResource.addRoom(List.of(room));

                System.out.println("Would you like to add another room? Y/N");
                String s = scanner.nextLine();
                if ("N".equalsIgnoreCase(s)) {
                    keepRunning = false;
                }
            } catch (Exception e) {
                System.out.println("Enter valid input!\n");
            }
        }
    }

    private static void addTestData() {
        // add test customers
        HotelResource.createACustomer("cus1@gmail.com", "Cus1TestFirstName", "Cus1TestLastName");
        HotelResource.createACustomer("cus2@gmail.com", "Cus2TestFirstName", "Cus2TestLastName");
        HotelResource.createACustomer("cus3@gmail.com", "Cus3TestFirstName", "Cus3TestLastName");
        HotelResource.createACustomer("cus4@gmail.com", "Cus4TestFirstName", "Cus4TestLastName");

        // add test Rooms
        RoomInterface room1 = new Room("100", 1000.0, RoomType.SINGLE);
        RoomInterface room2 = new Room("101", 2500.0, RoomType.DOUBLE);
        RoomInterface room3 = new FreeRoom("103", RoomType.SINGLE);
        RoomInterface room4 = new FreeRoom("104", RoomType.DOUBLE);

        List<RoomInterface> testRooms = new ArrayList<>();
        testRooms.add(room1);
        testRooms.add(room2);
        testRooms.add(room3);
        testRooms.add(room4);

        AdminResource.addRoom(testRooms);

        try {
            // add Test Reservations
            Date now = new Date();
            HotelResource.bookARoom("cus1@gmail.com", room1, now, Helper.addDays(now, 3));
            HotelResource.bookARoom("cus3@gmail.com", room3, Helper.addDays(now, 5),
                    Helper.addDays(now, 10));
        } catch (CustomerNotFoundException e){
            System.out.println("Customer does not exist with the given email!");
        }

        System.out.println("Added test data!");
    }


}
