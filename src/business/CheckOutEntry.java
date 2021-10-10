package business;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

final public class CheckOutEntry implements  Serializable {

    @Serial
    private static final long serialVersionUID = -9020142387772885123L;

    private CheckOutRecord record;
    private LocalDate checkOutDate;
    private LocalDate dueDate;
    private BookCopy copy;

    // Package Level
    CheckOutEntry(CheckOutRecord record, BookCopy copy) {
        this.record = record;
        this.checkOutDate = LocalDate.now();
        this.dueDate = this.checkOutDate.plusDays(copy.getBook().getMaxCheckoutLength());
        this.copy = copy;
    }

    public CheckOutRecord getRecord() {
        return record;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public BookCopy getCopy() {
        return copy;
    }


}
