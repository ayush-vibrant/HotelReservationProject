package com.company;


import com.company.api.HotelResource;
import com.company.exception.CustomerNotFoundException;
import com.company.exception.InvalidDatesOrderingException;
import com.company.exception.NoRoomsAddedException;
import com.company.helper.Dates;
import com.company.helper.Helper;
import com.company.model.Reservation;
import com.company.model.RoomInterface;

import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {
    private static final String CHECK_IN = "CheckIn";
    private static final String CHECK_OUT = "CheckOut";
    private static final Integer SEVEN = 7;

    public static void display() {
        System.out.println("Main Menu");
        System.out.println("----------------------------------");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.println("----------------------------------");
        System.out.println("Please select a number for the menu option");
    }

    public static boolean runMainMenu(Scanner scanner, int option) {
        boolean keepRunning = true;
        switch (option) {
            case 1 -> findAndReserveARoom(scanner);
            case 2 -> getCustomerReservations(scanner);
            case 3 -> createAccount(scanner);
            case 4 -> runAdminMenu(scanner);
            case 5 -> keepRunning = false;
            default -> System.out.println("Please enter a number between 1 and 5! \n");
        }
        return keepRunning;
    }

    private static void findAndReserveARoom(Scanner scanner) {
        try {
            // get checkIn date
            Date in = getDates(scanner, CHECK_IN);
            // get checkOut date
            Date out = getDates(scanner, CHECK_OUT);

            try {
                // findRooms on available dates
                int iteration = 1;
                Dates availableDates = findRooms(in, out, iteration);

                // book the room
                bookRoom(scanner, availableDates.getCheckIn(), availableDates.getCheckOut());
            } catch (NoRoomsAddedException | InvalidDatesOrderingException e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Enter valid input!\n");
        }
    }

    private static void bookRoom(Scanner scanner, Date in, Date out) {
        boolean keepRunning = true;
        while (keepRunning) {
            try {
                System.out.println("Would you like to book a room? Y or N");
                String ans = scanner.nextLine();
                String email = null;

                if ("y".equalsIgnoreCase(ans)) {
                    System.out.println("Do you have an account with us? Y or N");
                    String acc = scanner.nextLine();
                    if ("n".equalsIgnoreCase(acc)) {
                        System.out.println("Please create an account first!");
                        keepRunning = false;
                    } else if ("y".equalsIgnoreCase(acc)) {
                        System.out.println("Enter Email format: name@domain.com");
                        email = scanner.nextLine();

                        System.out.println("What room number would you like to reserve?");
                        int roomNumber = Integer.parseInt(scanner.nextLine());

                        RoomInterface r = HotelResource.getRoom(String.valueOf(roomNumber));
                        Reservation reservation = HotelResource.bookARoom(email, r, in, out);
                        if (reservation != null) {
                            System.out.println("Your reservation has been confirmed, Please find the details as follows: ");
                            System.out.println(reservation);
                            keepRunning = false;
                        }
                    } else
                        System.out.println("Either enter Y or N!");
                } else if ("n".equalsIgnoreCase(ans))
                    keepRunning = false;
                else
                    System.out.println("Either enter Y or N!");
            } catch (Exception e) {
                System.out.println("Enter valid Input");
            }
        }
    }

    private static Dates findRooms(Date in, Date out, int iteration) throws NoRoomsAddedException, InvalidDatesOrderingException {
        if(in.after(out)){
            throw new InvalidDatesOrderingException("checkIn date " + in + " is after "
                    + "checkOut date " + out + ", Enter valid dates!");
        }
        if (iteration >= 10) {
            System.out.println("No rooms are available for the next 70 days!");
            return new Dates(in, out);
        }

        Collection<RoomInterface> rooms = HotelResource.findARoom(in, out);

        if (rooms.isEmpty()) {
            // No rooms available in the given dates, suggest new dates and rooms
            System.out.println("Rooms aren't available in the duration of " + in + " - " + out + ".\n");
            in = Helper.addDays(in, SEVEN);
            out = Helper.addDays(out, SEVEN);
            System.out.println("Alternatively, Rooms are available from " + in + " to " + out);
            findRooms(in, out, ++iteration);
        } else {
            System.out.println("These rooms are available in the duration of " + in + " - " + out);
            rooms.forEach(System.out::println);
        }
        return new Dates(in, out);
    }

    private static Date getDates(Scanner scanner, String type) {
        Date date = null;
        while (date == null) {
            // get date
            System.out.println("Enter " + type + " Date dd/mm/yyyy example 02/08/2021");
            String input = scanner.nextLine();
            date = Helper.getDate(input);
        }
        return date;
    }

    private static void getCustomerReservations(Scanner scanner) {
        System.out.println("Enter Email format: name@domain.com");
        String email = scanner.nextLine();
        if (Helper.isValidEmail(email)) {
            try {
                Collection<Reservation> customersReservation = HotelResource.getCustomersReservation(email);
                if (customersReservation.isEmpty())
                    System.out.println("[]");
                else
                    customersReservation.forEach(System.out::println);
            } catch (CustomerNotFoundException e) {
                System.out.println(e.getMessage());
            }
        } else
            System.out.println("Enter valid email address! \n");
    }

    private static void createAccount(Scanner scanner) {
        try {
            // get customer details
            System.out.println("Enter Email format: name@domain.com");
            String emailAddress = scanner.nextLine();

            System.out.println("First Name");
            String firstName = scanner.nextLine();

            System.out.println("Last Name");
            String lastName = scanner.nextLine();

            HotelResource.createACustomer(emailAddress, firstName, lastName);

            System.out.println("Welcome to Hotel Reservation Application \n");
        } catch (IllegalArgumentException e) {
            System.out.println("Enter valid email address! \n");
        } catch (Exception e) {
            System.out.println("Enter valid input! \n");
        }
    }


    private static void runAdminMenu(Scanner scanner) {
        boolean keepAdminMenuRunning = true;
        while (keepAdminMenuRunning) {
            try {
                AdminMenu.display();
                int option = Integer.parseInt(scanner.nextLine());
                keepAdminMenuRunning = AdminMenu.runAdminMenu(scanner, option);
            } catch (Exception e) {
                System.out.println("Please enter a number between 1 and 6! \n");
            }
        }
    }

}
