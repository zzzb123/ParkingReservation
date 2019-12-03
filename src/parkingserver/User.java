package parkingserver;

import java.util.HashMap;

public class User {
    public String username;
    private HashMap<Reservation,Lot> reservations = new HashMap<>();

    public User(String username){
        this.username = username;
    }

    public Reservation generateReservation(TimePoint start, TimePoint end, Lot l){//creates a reservation that is bound to a given lot -- expected to be uploaded to the lot seprately
        Reservation r = null;
        reservations.put(r = new Reservation(this, start, end),l);
        return r;
    }
    
    @Override
    public boolean equals(Object arg0){
        if(((User)arg0).username.equals(username))
            return true;
        return false;
    }
}