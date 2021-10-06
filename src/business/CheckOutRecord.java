package business;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckOutRecord implements Serializable {

    private List<CheckOutEntry> entries;

    public CheckOutRecord(){ entries = new ArrayList<>();}

    public void addCheckOutEntry(BookCopy bookCopy){
        entries.add(new CheckOutEntry(this , bookCopy));
    }

    public List<CheckOutEntry> getEntry() {
        return entries;
    }

    @Override
    public String toString() {
        return entries.toString();
    }
}
