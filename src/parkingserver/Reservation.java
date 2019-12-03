package parkingserver;
public class Reservation{
    public TimePoint startTime;
    public TimePoint endTime;
    public User user;
    public Reservation(User u, TimePoint s, TimePoint e){
        user = u;
        startTime = s;
        endTime = e;
    }
}