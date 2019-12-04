package parkingserver;

import java.io.Serializable;

public class Reservation implements Serializable{
    private static final long serialVersionUID = 1L;
    public TimePoint startTime;
    public TimePoint endTime;
    public User user;
    public Reservation(TimePoint s, TimePoint e){
        startTime = s;
        endTime = e;
    }
    @Override
    public boolean equals(Object arg0){
        Reservation r = (Reservation)arg0;
        return startTime.equals(r.startTime) && endTime.equals(r.endTime);
    }
}