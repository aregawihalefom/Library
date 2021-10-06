package business.Controllers;

import business.Book;
import business.LibraryMember;
import dataaccess.DataAccessFacade;

import java.util.List;

public class BookController {

    public boolean bookAlreadyAdded(String isbn, List<String> bookIsbnList){
        return bookIsbnList.contains(isbn);
    }

    public void addNewBook(Book book) {
        DataAccessFacade da = new DataAccessFacade();
        da.saveNewBook(book);
    }


}
