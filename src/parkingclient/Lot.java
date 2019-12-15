package parkingclient;
public class Lot{
    public boolean isMappedLot = false;
    public String name;
    private int total;
    private int normal;
    private int handicap;
    public String readytimes;
    public Lot(String unparsedlotdata){
        if(unparsedlotdata.contains(":")){
            isMappedLot = true;
            //TODO not implemented
        }
        else{
            String[] data = unparsedlotdata.split("\t");
            name = data[0];
            readytimes = data[1] + ":" + data[2];
            total = Integer.parseInt(data[3]);
            normal = Integer.parseInt(data[4]);
            handicap = Integer.parseInt(data[5]);
        }
    }

    public int getNormal(){
        if(isMappedLot)
            return -1;
        return normal;
    }
    public int getHandicap(){
        if(isMappedLot){
            return -1;
        }
        return handicap;
    }
    public int getTotal(){
        if(isMappedLot)
            return -1;
        return total;
    }
}