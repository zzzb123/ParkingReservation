package parkingserver;

import java.util.ArrayList;

public class Scheduler{
    private ArrayList<Reservation> blocks = new ArrayList<>();
    public synchronized boolean timeAvailable(Reservation t){
        if(blocks.size() == 0)
            return true;
        for(int i = 0; i < blocks.size()-1; ++i){
            if(blocks.get(i).endTime.compareTo(t.startTime) <= 0 && blocks.get(i + 1).startTime.compareTo(t.endTime) >= 0)
                return true;
        }
        return false;
    }
    public synchronized void reserveTime(Reservation t) throws ImpossibleReservationException{
        if(!timeAvailable(t))
            throw new ImpossibleReservationException("error on attempt to register spot in scheduler -- make sure you verified an opening first and synchronized!");
        if(blocks.size() == 0)
            blocks.add(t);
        for(int i = 0; i < blocks.size(); ++i){
            if(blocks.get(i).endTime.compareTo(t.startTime) <= 0){
                blocks.add(i + 1, t);
                break;
            }
        }
    }
    public synchronized void cullEntries(TimePoint currentTime){
        int i = 0;
        while(i < blocks.size()){
            if(blocks.get(i).endTime.compareTo(currentTime) < 0)
                blocks.remove(i);
            else
                ++i;
        }
    }
    public synchronized Reservation getReservation(User u)throws ImpossibleReservationException{
        for (Reservation r : blocks){
            if(r.user.equals(u))
                return r;
        }
        throw new ImpossibleReservationException("error on appempt to retreive a reservation -- verify its existance and make sure user is being kept up to date with the lots!");
    }
    public synchronized boolean cancelReservation(Reservation r){
        return blocks.remove(r);
    }
}