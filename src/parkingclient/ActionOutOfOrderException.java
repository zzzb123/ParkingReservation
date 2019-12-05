package parkingclient;
public class ActionOutOfOrderException extends Exception{
    private static final long serialVersionUID = 1L;

    public ActionOutOfOrderException(String message) {
        super(message);
    }
    public ActionOutOfOrderException(){}
}