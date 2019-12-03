package parkingserver;

import java.io.Serializable;

public class Reservation implements Serializable{
    private static final long serialVersionUID = 1L;
    public TimePoint startTime;
    public TimePoint endTime;
    public User user;
    public Reservation(User u, TimePoint s, TimePoint e){
        user = u;
        startTime = s;
        endTime = e;
    }
}