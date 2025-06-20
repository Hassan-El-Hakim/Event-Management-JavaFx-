package com.example.demo2;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.*;


public class Room {
    private String name;
    private int capacity;
    private List<TimeSlot> bookedSlots;
    private boolean isavailable;


    public Room(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
        this.bookedSlots = new ArrayList<>();
    }
    public List<TimeSlot> getAvailableSlots(LocalDate date, BookingDuration duration) {
        List<TimeSlot> available = new ArrayList<>();

        for (int hour = 0; hour <= 24 - duration.getHours(); hour++) {
            LocalDateTime start = date.atTime(hour, 0);
            if (start.getMinute() != 0 || start.getSecond() != 0) {
                continue;
            }

            TimeSlot newSlot = new TimeSlot(start, duration.getHours());
            boolean conflict = false;

            for (TimeSlot slot : bookedSlots) {
                if (slot.overlaps(newSlot)) {
                    conflict = true;
                    break;
                }
            } if (!conflict) {
                available.add(newSlot);
            }
        }

        return available;
    }

    public boolean bookSlot(TimeSlot slot) {
        for (TimeSlot booked : bookedSlots) {
            if (booked.overlaps(slot)) {
                return false;
            }
        } bookedSlots.add(slot);
        return true;
    }

    public boolean cancelSlot(TimeSlot slot) {
        return bookedSlots.remove(slot);
    }

    public int getCapacity() {
        return capacity;
    }
    public boolean isAvailable(){
        if(this.isavailable==true)
            return true;
        else
            return false;
    }
    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return "Room: " + name + ", capacity: " + capacity + ", bookedSlots: " + bookedSlots.size();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return capacity == room.capacity && Objects.equals(name, room.name);
    }
}