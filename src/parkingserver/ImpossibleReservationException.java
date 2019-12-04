package parkingserver;
public class ImpossibleReservationException extends Exception{
    private static final long serialVersionUID = 1L;
    public ImpossibleReservationException(String message){
        super(message);
    }
}