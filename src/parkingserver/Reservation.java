package parkingserver;

import java.io.Serializable;

public class Reservation implements Serializable{
    private static Integer magicNumber = 0;
    private static final long serialVersionUID = 1L;
    public TimePoint startTime;
    public TimePoint endTime;
    public User user;
    private int num;
    public Reservation(TimePoint s, TimePoint e){
        startTime = s;
        endTime = e;
        synchronized(magicNumber){
            num = magicNumber++;
        }
        num = magicNumber++;
    }
    @Override
    public boolean equals(Object arg0){
        Reservation r = (Reservation)arg0;
        return startTime.equals(r.startTime) && endTime.equals(r.endTime);
    }

    @Override
    public int hashCode(){
        return ("" + startTime.hashCode() + endTime.hashCode() + num).hashCode();
    }
}