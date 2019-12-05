package parkingclient;
public class ActionOutOfOrderException extends Exception{
    public ActionOutOfOrderException(String message){
        super(message);
    }
    public ActionOutOfOrderException(){}
}