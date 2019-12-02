package parkingserver;
public class Reservation{
    public TimePoint startTime;
    public TimePoint endTime;
    public User user;
    public Reservation(TimePoint s, TimePoint e){
        startTime = s;
        endTime = e;
    }
}