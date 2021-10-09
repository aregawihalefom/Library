package business;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

final public class CheckOutRecord implements Serializable {

    @Serial
    private static final long serialVersionUID = 772723204937015073L;
    private List<CheckOutEntry> entries;

    public CheckOutRecord(){
        entries = new ArrayList<>();
    }

    public void addCheckOutEntry(BookCopy bookCopy){
        entries.add(new CheckOutEntry(this , bookCopy));
    }

    public List<CheckOutEntry> getEntries() {
        return entries;
    }

    @Override
    public String toString() {
        return entries.toString();
    }
}
