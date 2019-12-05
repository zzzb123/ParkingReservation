package parkingclient;
public class Spot{
    public int[] coords;
    public boolean isAvailable;
    public boolean isHandicap;
    public Spot(int[] coords, String availability, String type){
        this.coords = coords;
        isAvailable = availability.equals("open");
        isHandicap = type.equals("handicap");
    }
}