package com.example.demo2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Attendee extends User{
    private String address;
    private Gender gender;
    private ArrayList<String> interests;
    private Wallet wallet;


    public Gender getgender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Attendee(){
        super("","",LocalDate.now());
        gender=Gender.Other;
        address="";
        this.interests=new ArrayList<>();
    }
    public Attendee(String username, String password, Gender gender, LocalDate DateOfBirth, String address) {
        super(username, password, DateOfBirth);
        this.address = address;
        this.gender = gender;
        wallet = new Wallet(this, 0.0);
        this.interests=new ArrayList<>();
    }

    public double getBalance() {
        return wallet.Balance;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setBalance(double balance) {
        wallet.Balance = balance;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public Wallet getWallet(){
        return this.wallet;
    }


    // public void setInterests(String interests) {
    //     this.interests = interests;
    // }
    public void setInterests(ArrayList<String> interests) {
        this.interests = interests;
    }

    public void addInterests(String interests) {
        this.interests.add(interests);
    }

    // public void CreateNewAccount(String username, String password, Gender gender) {
    //     this.username = username;
    //     this.password = password;
    //     this.gender = gender;
    // }

    public void GetTicket(double value, Organizer o) {
        this.wallet.Transaction(value, o.getWallet());
    }

    public void ShowRecommenededEvents(ArrayList<Events> events) {
        System.out.println("Based on your interests this are the recommened events");
        for (Events e : events) {
            if (interests.contains(e.getCategory())) {
                System.out.println(e);
            }
        }

    }

    public boolean ChooseEvent(Events event) {

        if (event.getAvailableSeats() <= 0) {
            System.out.println("No Available seats.");
            return false;
        }

        double price = event.getTicketPrice();
        if (wallet.Balance < price) {
            System.out.println(" Insufficient balance. Ticket price: " + price + ", Your balance: " + wallet.Balance);
            return false;
        }

        if(BuyTickets(event)){
            event.bookSeat(this);
            System.out.println("Booking successful! You have been registered for: " + event.getTitle());
            return true;
        }
        return false ;
    }

    public boolean BuyTickets(Events event) {


        double price = event.getTicketPrice();
        double balance = wallet.Balance;

        if (balance >= price) {
            wallet.Transaction(price, event.getOrganizer().getWallet());
            System.out.println( this.getUsername() + " bought a ticket for Event ID: " + event.getEventID());
            return true;
        } else {
            System.out.println("Insufficient Funds. Ticket: " + price + ", Your balance: " + balance);
            return false;
        }
    }
    @Override
    public String toString(){
        return "Role: Attendee "+"Name: "+ this.getUsername() +"Date of Birth: "+this.getDateOfBirth()+"Working Hours: "+"Gender: "+gender;
    }

}