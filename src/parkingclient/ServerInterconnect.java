package parkingclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;

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
            connection = new Socket("localhost", 12345);
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

    public boolean setUser(String username, String password) {
        try {
            out.println("set-user");
            out.println(username);
            out.println(password);
            if (in.readLine().equals("sorry"))
                return false;
            userSet = true;
            return true;
        } catch (Exception e) {}
        return false;
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

    public String[] listLots(int num) throws ActionOutOfOrderException {
        if (!reservationSet || !coordSet) {
            throw new ActionOutOfOrderException();
        }
        out.println("get-lots");
        out.println(num);
        try {
            return in.readLine().split("::");
        } catch (IOException e) {}
        return null;
    }

    public boolean registerUser(String username, String email, String identification, String password){
        out.println("register-user");
        out.println(username);
        out.println(email);
        out.println(password);
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
        //TODO reserving a mapped spot is not implemented
        return false;
    }

    public boolean reserveSpot(boolean normalSpot){
        out.println("reserve-spot");
        out.println((normalSpot?"normal":"handicap"));
        try{
            boolean res = in.readLine().equals("ok");
            System.out.println("successfully reserved spot!");
            return res;
        }catch(Exception e){return false;}
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

    public void fixReservationSystem(){
        out.println("fix-reservation");
    }
}