package business.exceptions;

public class LibraryMemberException extends Exception {

    public LibraryMemberException() {
        super();
    }
    public LibraryMemberException(String msg) {
        super(msg);
    }
    public LibraryMemberException(Throwable t) {
        super(t);
    }
}
