package com.example.demo2;

import java.util.ArrayList;

public class Category  {

    private String name;
    private int ID;
    private String description;
    private ArrayList<Events> CategoryEvents;
    static int NumOfCategories;
    public Category(String name, int ID, String description){
        this.name=name;
        this.ID=ID;
        this.description=description;
        CategoryEvents = new ArrayList<>();
        NumOfCategories++;
    }
    public String getName(){
        return this.name;
    }
    public int getID(){
        return this.ID;
    }
    public String getDescription(){
        return this.description;
    }
    public ArrayList<Events> getCategoryEvents(){
        return CategoryEvents;
    }
    public int getNumOfCategories(){
        return NumOfCategories;
    }


    public void EditDescription(String text){
        this.description=text;
    }

    void EditID(int ID){
        this.ID=ID;
    }

    void EditName(String name){
        this.name=name;
    }

    void ReadEvents(){
        System.out.print("<<");
        for (Events categoryEvent : CategoryEvents) {
            System.out.print(categoryEvent + ", ");
        }
        System.out.print(">>");
    }
    void ReadCategory(){
        System.out.println("Category: "+name);
        System.out.println("ID: "+ID);
        System.out.println("Description: "+description);
        ReadEvents();
    }

    public void AddEvent(Events e){
        CategoryEvents.add(e);
    }

    void DeleteEvent(Events e){
        CategoryEvents.remove(e);
    }
    @Override
    public String toString(){
        return "Name: "+ name + ", ID: "+ ID + ", Description: " + description + "Related Events: " + CategoryEvents;
    }

}