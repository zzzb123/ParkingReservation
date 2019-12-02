package parkingserver;
public class User{
    public String username;
    
    @Override
    public boolean equals(Object arg0){
        if(((User)arg0).username.equals(username))
            return true;
        return false;
    }
}