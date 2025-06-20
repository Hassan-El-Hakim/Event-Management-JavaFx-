package com.example.demo2;

import java.time.LocalDate;
import java.util.ArrayList;

public class Database {
    public static ArrayList<Room> rooms = new ArrayList<>(){{ add(new Room("1- El lo2lo2a",200)); add(new Room("2- El Gwhra",100)); }};
    static ArrayList<User> users = new ArrayList<>(){{ add(new Admin("sama no","Admin123456", LocalDate.now(),"12")); }};
    public static ArrayList<Events> events = new ArrayList<>();

    public static ArrayList<Category> Categories = new ArrayList<>();
    static {
        events.add(new Events("Music party", LocalDate.of(2025, 6, 1), 50.0, 100, "music", new Room("room1",500), new Organizer()));
        events.add(new Events("Technology event", LocalDate.of(2025, 7, 10), 30.0, 80, "technology", new Room("room2",600), new Organizer()));
        events.add(new Events("Marathon", LocalDate.of(2025, 8, 20), 20.0, 50, "sport", new Room("room3",700), new Organizer()));
        events.add(new Events("Art Day",LocalDate.of(2025, 5, 28), 100.0, 150, "art", new Room("room00",1000), new Organizer()));
        events.add(new Events("Art Club",LocalDate.of(2025, 9, 20), 30.0, 50, "art", new Room("room4",800), new Organizer()));

    }
    static {
        Categories.add(new Category("Art",10,"aaa"));
        Categories.add(new Category("sport",20,"bbb"));
        Categories.add(new Category("music",30,"ccc"));
        Categories.add(new Category("technology",40,"ddd"));

}

}