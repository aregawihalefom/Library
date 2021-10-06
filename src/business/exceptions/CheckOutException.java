package business.exceptions;

public class CheckOutException extends Exception {
    public CheckOutException() {
        super();
    }
    public CheckOutException(String msg) {
        super(msg);
    }
    public CheckOutException(Throwable t) {
        super(t);
    }
}
