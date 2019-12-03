package parkingserver;

import java.util.HashMap;

public class User {
    public String username;
    public String password;
    public String email;
    public String identifier;
    private HashMap<Reservation,Lot> reservations = new HashMap<>();

    public User(String username, String email, String password, String identifier){
        this.username = username;
        this.email = email;
        this.password = password;
        this.identifier = identifier;
    }
    public User(String user, String email, String pass){
        this(user, email, pass, null);
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