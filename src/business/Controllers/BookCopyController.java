package business.Controllers;

import business.Book;
import dataaccess.DataAccessFacade;

import java.util.HashMap;

public class BookCopyController {


    public boolean copyNumberTakenCheck(int copyNumber, String book_isbn) {

        DataAccessFacade da = new DataAccessFacade();
        HashMap bookMap = da.readBooksMap();

        Book book = (Book) bookMap.get(book_isbn);
        // add bookcopy
        book.addCopy();

        if(!bookMap.containsKey(book_isbn))
            return true;
        return false ;
    }

    public void addNewBookCopy(String book_isbn) {

        DataAccessFacade da = new DataAccessFacade();
        HashMap bookMap = da.readBooksMap();
        Book book = (Book) bookMap.get(book_isbn);
        book.addCopy();


    }


}
