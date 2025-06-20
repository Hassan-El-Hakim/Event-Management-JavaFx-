package com.example.demo2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Wallet {
    String username;
    double Balance;

    Wallet(User U,double Balance){
        username=U.getUsername();
        this.Balance=Balance;
    }
    void AddCredit(double value) {
        Balance = Balance + value;
    }

    void RemoveCredit(double value){
        Balance=Balance-value;
    }
    void Transaction(double value,Wallet Receiver/*,Organizer o*/){
        this.RemoveCredit(value);
        Receiver.AddCredit(value);

        /*double B1 = a.getBalance();
       B1=B1-value;
       a.setBalance(B1);
       double B2 = o.getBalance();
       B2=B2+value;
       o.setBalance(B2);*/
    }
    void ShowBalance(){
        System.out.println("Balance: "+Balance);
    }
}
