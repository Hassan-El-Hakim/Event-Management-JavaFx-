package com.example.demo2;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Events {
    private static int IDCounter=1;
    private int EventID;
    private String NameOfEvent;
    private LocalDate date;
    private double ticketPrice;
    private int capacity;
    private String category;
    private Room room;
    private Organizer organizer;
    private ArrayList<Attendee> attendees;
    public ArrayList<Events> events;
    static int availableSeats;
    private LocalTime startTime;
    private LocalTime endTime;

    public Events(String NameOfEvent, LocalDate date,double ticketPrice,
                  int capacity, String category, Room room, Organizer organizer) {
        this.NameOfEvent = NameOfEvent;
        this.date = date;
        this.ticketPrice = ticketPrice;
        this.capacity = capacity;
        this.room = room;
        this.organizer = organizer;
        this.attendees = new ArrayList<Attendee>();
        this.category=category;
        EventID=IDCounter;
        IDCounter++;
    }

    /*public Events(String NameOfEvent, LocalDate date,LocalTime startTime,LocalTime endTime,double ticketPrice,
                  int capacity, String category, Room room, Organizer organizer) {
        this.NameOfEvent = NameOfEvent;
        this.date = date;
        this.ticketPrice = ticketPrice;
        this.capacity = capacity;
        this.room = room;
        this.organizer = organizer;
        this.attendees = new ArrayList<Attendee>();
        this.category=category;
        EventID=IDCounter;
        IDCounter++;
        this.startTime=startTime;
        this.endTime=endTime;
    }*/

    public void setCategory(String category) {
        this.category =category;
    }
    public LocalTime getStartTime(){
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
    /*public Event getEventByID(int ID){
        for (Event e : events){
            if(e.getEventID()==ID){
                return e;
            }

        }
        System.out.println("Event not Found :(");
        return null;

    }*/

    public int getEventID(){
        return EventID;
    }
    public String getTitle() {
        return NameOfEvent;
    }

    public LocalDate getDateTime() {
        return date;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public String getCategory(){
        return category;
    }
    public boolean addAttendee(Attendee attendee){
        if (attendees.size()<capacity){
            attendees.add(attendee);
            return true;
        }
        else
            return false;
    }
    public Organizer getOrganizer(){
        return this.organizer;
    }
    public ArrayList<Attendee> GetAttendees(){
        return this.attendees;
    }
    @Override
    public String toString() {
        return "Event ID: "+ EventID +", Event Name: " + NameOfEvent + ", Date: " + date + ", Price: "
                + ticketPrice + ", Available Seats: " + getAvailableSeats()+
                ", Type Of Event: "+ category + ", "+room;
    }
    public int getAvailableSeats() {
        availableSeats=capacity - attendees.size();
        return availableSeats ;
    }
    public int getCapacity() {
        return capacity;
    }

    public Room getRoom() {
        return room;
    }

    public Boolean hasAvailableSeats(){
        if (getAvailableSeats()>0){
            return true;
        }
        else
            return false;
    }
    public void bookSeat(Attendee a) {
        if (hasAvailableSeats()) {
            attendees.add(a);
            availableSeats--;
        }
    }
    public LocalDate getDate(){
        return date;
    }
}