package parkingserver;

import java.util.HashMap;
import java.util.LinkedList;

public class User {
    public String username;
    public String password;
    public String email;
    public String identifier;
    public LinkedList<Reservation> reservations = new LinkedList<>();

    public User(String username, String email, String password, String identifier){
        this.username = username;
        this.email = email;
        this.password = password;
        this.identifier = identifier;
    }
    public User(String user, String email, String pass){
        this(user, email, pass, null);
    }

    public void linkReservation(Reservation r, Lot l){//creates a reservation that is bound to a given lot -- expected to be already uploaded to the lot
        r.user = this;
        r.lot = l;
        reservations.add(r);
    }
    
    @Override
    public boolean equals(Object arg0){
        if(((User)arg0).username.equals(username))
            return true;
        return false;
    }
}