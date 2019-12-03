package parkingserver;
public class Lot{
    private Scheduler[] handicapSpots;
    private Scheduler[] normalSpots;
    public Lot(int numNormal, int numHandicap){
        handicapSpots = new Scheduler[numHandicap];
        for(int i = 0; i < handicapSpots.length; ++i)
            handicapSpots[i] = new Scheduler();
        normalSpots = new Scheduler[numNormal];
        for(int i = 0; i < normalSpots.length; ++i)
            normalSpots[i] = new Scheduler();
    }

    public boolean hasOpenNormalSpots(Reservation r){
        for(Scheduler s : normalSpots){
            if(s.timeAvailable(r))
                return true;
        }
        return false;
    }
    public boolean hasHandicapSpots(Reservation r){
        for(Scheduler s : handicapSpots){
            if(s.timeAvailable(r))
                return true;
        }
        return false;
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
}