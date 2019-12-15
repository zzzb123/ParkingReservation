package parkingserver;
public class Lot{
    public String lotName;
    private double[] position;
    private Scheduler[] handicapSpots;
    private Scheduler[] normalSpots;
    public Lot(String name,int numNormal, int numHandicap, double[] position){
        handicapSpots = new Scheduler[numHandicap];
        for(int i = 0; i < handicapSpots.length; ++i)
            handicapSpots[i] = new Scheduler();
        normalSpots = new Scheduler[numNormal];
        for(int i = 0; i < normalSpots.length; ++i)
            normalSpots[i] = new Scheduler();
        this.position = position;
        this.lotName = name;
    }

    public int getTotalSpots(){
        return normalSpots.length + handicapSpots.length;
    }

    public int getNormalOpenings(Reservation r){
        int count = 0;
        for(Scheduler s : normalSpots){
            if(s.timeAvailable(r))
                count++;
        }
        return count;
    }
    public int getHandicapOpenings(Reservation r){
        int count = 0;
        for(Scheduler s : handicapSpots){
            if(s.timeAvailable(r))
                count++;
        }
        return count;
    }
    public synchronized void reserveNormalSpot(Reservation r)throws ImpossibleReservationException{
        for(Scheduler s : normalSpots){
            if(s.timeAvailable(r)){
                s.reserveTime(r);
                return;
            }
        }
        throw new ImpossibleReservationException("No Spots available on attempt to register normal spot -- check for proper synchronization");
    }
    public synchronized void reserveHandicapSpot(Reservation r)throws ImpossibleReservationException{
        for(Scheduler s : handicapSpots){
            if(s.timeAvailable(r)){
                s.reserveTime(r);
                return;
            }
        }
        throw new ImpossibleReservationException("No Spots available on attempt to register handicap spot -- check for proper synchronization");
    }

    public void cancelReservation(Reservation r){
        for(Scheduler s : normalSpots){
            if(s.cancelReservation(r))
                return;
        }
        for(Scheduler s : handicapSpots){
            if(s.cancelReservation(r))
                return;
        }
    }

    public void triggerRGC(TimePoint currentTime){
        for(Scheduler s : normalSpots){
            s.cullEntries(currentTime);
        }
        for(Scheduler s : handicapSpots){
            s.cullEntries(currentTime);
        }
    }

    public Scheduler[] getNormalSpots(){
        return normalSpots;
    }
    public Scheduler[] getHandicapSpots(){
        return handicapSpots;
    }

    public double getDistance(double[] arg0){
        return Math.sqrt(Math.pow(position[0]*1000 - arg0[0]*1000, 2) + Math.pow(position[1]*1000 - arg0[1]*1000,2));
    }

    public boolean schedulerIsHandicap(Scheduler s){
        for(Scheduler hs : handicapSpots){
            if(hs == s)
                return true;
        }
        return false;
    }
}