package com.example.demo2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public enum BookingDuration {
    ONE_HOUR(1),
    TWO_HOURS(2),
    THREE_HOURS(3),
    FOUR_HOURS(4),
    FIVE_HOURS(5),
    SIX_HOURS(6),
    SEVEN_HOURS(7),
    EIGHT_HOURS(8),
    NINE_HOURS(9),
    TEN_HOURS(10),
    ELEVEN_HOURS(11),
    TWELVE_HOURS(12);

    private final int hours;

    BookingDuration(int hours) {
        this.hours = hours;
    }

    public int getHours() {
        return hours;
    }

    @Override
    public String toString() {
        return hours + " hour(s)";
    }
}
