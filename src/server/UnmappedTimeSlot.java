package server;

import java.util.ArrayList;

public class UnmappedTimeSlot implements TimeSlot{
    private ArrayList<User> registered_normal = new ArrayList<>();
    private ArrayList<User> registered_handicap = new ArrayList<>();
    public ArrayList<User> getNormalRegistrations(){
        return registered_normal;
    }
    public ArrayList<User> getHandicapRegistrations(){
        return registered_handicap;
    }
}