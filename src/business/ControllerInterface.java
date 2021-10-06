package business;

import java.util.HashMap;
import java.util.List;

import business.exceptions.BookCopyException;
import business.exceptions.LibraryMemberException;

public interface ControllerInterface {

	void login(String id, String password) throws LoginException;
	List<String> allMemberIds();
	List<String> allBookIds();

	void saveLibraryMember(LibraryMember member) throws LibraryMemberException;
	boolean addBook(Book book) throws BookCopyException;
	boolean addBookCopy(String ISBN, int copyNumber) throws BookCopyException;

	HashMap<String, LibraryMember> getMembers();
    HashMap<String, Book> getBooks();
	boolean checkMemberId(String member_id);

	Book checkBookISBN(String isbn);
	Address addAddress(String street , String city , String state , String zip);
	LibraryMember addLibraryMember(String memberNumber , String firstName , String lastName , String phoneNumber , Address address);
}
