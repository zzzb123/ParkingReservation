package parkingclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;

import javax.net.ssl.SSLSocketFactory;

public class ServerInterconnect {
    private Socket connection;
    private BufferedReader in;
    private PrintWriter out;

    private boolean coordSet = false;
    private boolean userSet = false;
    private boolean reservationSet = false;
    private boolean targetLotSet = false;

    public ServerInterconnect() {
        try {
            connection = SSLSocketFactory.getDefault().createSocket("localhost", 12345);
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            out = new PrintWriter(connection.getOutputStream(), true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setPosition(String coords) {
        out.println("set-coordinates");
        out.println(coords);
        coordSet = true;
    }

    public boolean setUser(String username, String passhash) {
        out.println("set-user");
        out.println(username);
        out.println(passhash);
        try {
            if (in.readLine().equals("sorry"))
                return false;
        } catch (Exception e) {
        }
        userSet = true;
        return true;
    }

    public void setReservation(String startTime, String endTime) {
        out.println("set-reservation-times");
        out.println(startTime);
        out.println(endTime);
        reservationSet = true;
    }

    public boolean setTargetLot(String lotname) {
        out.println("set-target-lot");
        out.println(lotname);
        try {
            if (in.readLine().equals("ok")) {
                targetLotSet = true;
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public String[][] listLots() throws ActionOutOfOrderException {
        if (!targetLotSet || !reservationSet || !coordSet) {
            throw new ActionOutOfOrderException();
        }
        out.println("get-lots");
        String[][] dataset = null;
        try {
            String[] elems = in.readLine().split("::");
            dataset = new String[2][elems.length];
            int counter = 0;
            for(String s : elems){
                String[] lot = s.split("//");
                dataset[0][counter] = lot[0];
                dataset[1][counter++] = lot[1];
            }
        } catch (IOException e) {}
        return dataset;
    }

    public boolean registerUser(String username, String email, String identification, String password){
        out.println("register-user");
        out.println(username);
        out.println(email);
        out.println(password.hashCode());
        out.println(identification);
        try{
            if(in.readLine().equals("ok")){
                userSet = true;
                return true;
            }
        }catch(Exception e){}
        return false;
    }

    public Lot getLotData() throws ActionOutOfOrderException{
        if(!targetLotSet || !reservationSet){
            throw new ActionOutOfOrderException();
        }
        out.println("get-lot-data");
        try{
            return new Lot(in.readLine());
        }catch(Exception e){}
        return null;
    }

    public boolean reserveSpot(Spot s) throws ActionOutOfOrderException{
        if(!targetLotSet || !userSet)
            throw new ActionOutOfOrderException();
        out.println("reserve-spot");
        if(s == Lot.unmappedNormalSpot){
            out.println("normal");
        }
        else if(s == Lot.unmappedHandicapSpot){
            out.println("handicap");
        }
        else{
            out.println(s.coords[0] + "," + s.coords[1]);
        }
        try{
            if(in.readLine().equals("error"))
                return false;
        }catch(Exception e){}
        return true;
    }

    public void disconnect(){
        out.println("disconnect");
    }

    public LinkedList<String> getReservations()throws ActionOutOfOrderException{
        if(!userSet)
            throw new ActionOutOfOrderException();
        out.println("list-reservations");
        LinkedList<String> out = new LinkedList<>();
        String r;
        try{
            while(!(r = in.readLine()).equals("ok")){
                out.add(r);
            }
        }catch(Exception e){}
        return out;
    }

    public void cancelReservation() throws ActionOutOfOrderException{
        if(!userSet || !reservationSet){
            throw new ActionOutOfOrderException();
        }
        out.println("cancel-reservation");
    }
}