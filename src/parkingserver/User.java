package parkingserver;

import java.util.HashMap;

public class User {
    public String username;
    public String password;
    public String email;
    public String identifier;
    public HashMap<Reservation,Lot> reservations = new HashMap<>();

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
        reservations.put(r,l);
    }
    
    @Override
    public boolean equals(Object arg0){
        if(((User)arg0).username.equals(username))
            return true;
        return false;
    }
}