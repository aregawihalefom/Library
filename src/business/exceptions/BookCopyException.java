package business.exceptions;

public class BookCopyException extends Exception {
    public BookCopyException() {
        super();
    }
    public BookCopyException(String msg) {
        super(msg);
    }
    public BookCopyException(Throwable t) {
        super(t);
    }
}
