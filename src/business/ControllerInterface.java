package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import business.exceptions.BookCopyException;
import business.exceptions.LibraryMemberException;

public interface ControllerInterface {

	void login(String id, String password) throws LoginException;
	List<String> allMemberIds();
	List<String> allBookIds();

	void saveLibraryMember(LibraryMember member) throws LibraryMemberException;
	boolean addBook(String isbn, String  title, int maxBorrowDays, List<Author> authors) throws BookCopyException;
	boolean addBookCopy(String ISBN, int copyNumber) throws BookCopyException;

	HashMap<String, LibraryMember> getMembers();
    HashMap<String, Book> getBooks();
	boolean checkMemberId(String member_id);

	Book checkBookISBN(String isbn);
	Address addAddress(String street , String city , String state , String zip);
	LibraryMember addLibraryMember(String memberNumber , String firstName , String lastName , String phoneNumber , Address address);

}
