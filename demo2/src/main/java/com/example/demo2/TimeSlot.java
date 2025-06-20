package com.example.demo2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimeSlot {
    private LocalDateTime start;
    private LocalDateTime end;

    public TimeSlot(LocalDateTime start, int durationHours) {
        if (start == null || durationHours <= 0) {
            throw new IllegalArgumentException("Invalid time or duration.");
        }
        this.start = start;
        this.end = start.plusHours(durationHours);
    }

    public LocalDateTime getStart() {
        return this.start;
    }

    public LocalDateTime getEnd() {
        return this.end;
    }

    public boolean overlaps(TimeSlot other) {
        return !(this.end.isBefore(other.start) || this.start.isAfter(other.end));
    }

    @Override
    public String toString() {
        return start.toLocalTime() + " to " + end.toLocalTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSlot that = (TimeSlot) o;
        return start.equals(that.start) && end.equals(that.end);
    }

}
