package com.example.demo2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Organizer extends User {
    private Wallet wallet;
    private List<Events> events;

    public Organizer(){super("","",LocalDate.now());
    this.wallet=new Wallet(this,0.0);}
    public Organizer(String username, String password, LocalDate DateOfBirth) {
        super(username, password, DateOfBirth);
        this.events = new ArrayList<>();
        wallet = new Wallet(this, 0.0);
    }

    public List<Events> getEvents() {
        return events;
    }

    public void setEvents(List<Events> events) {
        this.events = events;
    }

    public String getPassword() {
        return super.getPassword();
    }

    public Wallet getWallet() {
        return this.wallet;
    }

    public void setPassword(String password) {
        super.setPassword(password);
    }

    public String getUsername() {
        return super.getUsername();
    }

    public void setUsername(String username) {
        super.setUsername(username);
    }


    public void addEvent(String NameOfEvent, LocalDate date, double ticketPrice, int capacity, String category,
                         Room room, Organizer organizer) {

        Events event = new Events(NameOfEvent, date,/*startTime,endTime,*/ ticketPrice, capacity, category, room, organizer);
        events.add(event);
        Database.events.add(event);
    }

    public void showAvailableRooms(List<Room> rooms) {
        System.out.println("Available Rooms:");
        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.println("Available rooms:");
                ;
                System.out.println(room.getName());
            }
        }
    }


    public double getBalance() {
        return wallet.Balance;
    }

    public void setBalance(double balance) {
        this.wallet.Balance = balance;
    }

    public void showAttendees(Events e) {
        for (Attendee attendee : e.GetAttendees()) {
            System.out.println("Name:" + attendee.getUsername());
        }

    }

    public void DeleteEvent(Events e) {
        events.remove(e);
    }

    @Override
    public String toString() {
        return "Role: Organizer " + "Name: " + super.getUsername() + "Date of Birth: " + super.getDateOfBirth() + "Working Hours: ";
    }
}