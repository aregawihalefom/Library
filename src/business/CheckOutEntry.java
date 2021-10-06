package business;

import java.io.Serializable;
import java.time.LocalDate;

public class CheckOutEntry  {

    private CheckOutRecord record;
    private LocalDate checkOutDate;
    private LocalDate dueDate;
    private BookCopy copy;

    // Package Level
    CheckOutEntry(CheckOutRecord record, BookCopy copy) {
        this.record = record;
        this.checkOutDate = LocalDate.now();
        this.dueDate = checkOutDate.plusDays(copy.getBook().getMaxCheckoutLength());
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
