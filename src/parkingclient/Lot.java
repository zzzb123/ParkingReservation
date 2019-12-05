package parkingclient;
public class Lot{
    public int[] lotspace;
    public boolean isMappedLot = false;
    public static Spot unmappedNormalSpot = new Spot(null, null, null);
    public static Spot unmappedHandicapSpot = new Spot(null, null, null);
    public Spot[][] lotMap;
    public Lot(String unparsedlotdata){
        String[] elems = unparsedlotdata.split(":");
        String[] size = elems[0].split(",");
        lotspace = new int[]{Integer.parseInt(size[0]), Integer.parseInt(size[1])};
        if(elems.length > 1){
            isMappedLot = true;
            lotMap = new Spot[lotspace[0]][lotspace[1]];
            for(int i = 1; i < elems.length; i++){
                String[] spotdata = elems[i].split(",");
                int p1 = Integer.parseInt(spotdata[0]);
                int p2 = Integer.parseInt(spotdata[1]);
                lotMap[p1][p2] = new Spot(new int[]{p1,p2}, spotdata[2],spotdata[3]);
            }
        }
    }
}