package com.example.demo2;

import java.time.LocalDate;

public class Admin extends User {

    private String role;
    private String workingHours;

    public Admin(){
        super("","",LocalDate.now());
        setWorkingHours("");
    }

    public Admin(String username,String password,LocalDate DateOfBirth, String workingHours) {
        super(username,password,DateOfBirth);
        //setRole(role);
        setWorkingHours(workingHours);

    }

    public void updateWorkingHours(String workingHours) {

        this.workingHours = workingHours;
    }

    public void setRole(String role) {
        if (role == null || role.trim().isEmpty()) {
            throw new IllegalArgumentException("Role cannot be null or empty");
        }
        this.role = role.trim();
    }

    public void setWorkingHours(String WorkingHours) {
        if (WorkingHours == null || WorkingHours.trim().isEmpty()) {
            throw new IllegalArgumentException("Working hours cannot be null or empty");
        }
        this.workingHours = WorkingHours.trim();
    }

    public void AddRoom(String name, int capacity){
        Room r = new Room(name,capacity);
        Database.rooms.add(r);
    }

    public String getRole() {
        return role;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    @Override
    public String toString() {
        return super.toString()
                + "Admin Info:"
                + "\nDate of Birth: " + super.getDateOfBirth()
                + "\nRole: " + role
                + "\nWorking Hours: " + workingHours;

    }
    /*public void Display(List<Room> rooms,List<Attendee> attendees, List<Organizer> organizers) {


        System.out.println("ATTENDEES");
        for (Attendee attendee : attendees) {
            System.out.println(attendee);
        }

        System.out.println("ORGANIZERS");
        for (Organizer organizer  : organizers) {
            System.out.println(organizer);
        }

    }*/

    public void addCategory(String name,int ID,String description){
        Category c = new Category(name,ID,description);
        Database.Categories.add(c);
    }
    public void deleteCategory(int ID){
        for(Category c: Database.Categories){
            if(c.getID()==ID){
                Database.Categories.remove(c);
            }
        }
    }
    public void displayCategories(){
        for (Category c:Database.Categories){
            System.out.println(c);
            System.out.println("Number of Categories: "+ Category.NumOfCategories);
        }
    }
    public void displayRooms(){
        System.out.println("ROOMS");
        for (Room room : Database.rooms) {
            System.out.println(room);
        }
    }
    public void displayEvents(){
        System.out.println("EVENTS");
        for (Events event : Database.events) {
            System.out.println(event);
        }
    }
    public void displayUsers(){
        System.out.println("Users: ");
        for (User user  : Database.users) {
            System.out.println(user);
        }
    }





}