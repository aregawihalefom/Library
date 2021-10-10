package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import business.Controllers.BookController;
import business.Controllers.BookCopyController;
import business.exceptions.BookCopyException;
import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import librarysystem.AdministratorsDashboard;
import librarysystem.BothDashboard;
import librarysystem.LibrarianDashboard;
import librarysystem.guiElements.UtilGui;

public class SystemController implements ControllerInterface {

	public static Auth currentAuth = null;
	private DataAccess da = new DataAccessFacade();

	public void login(String id, String password) throws LoginException {

		
		HashMap<String, User> map = da.readUserMap();

		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}

		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Username/Password combination is incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
		UtilGui.hideAllWindows();


		if(currentAuth.name().equals("LIBRARIAN")){
			if(!LibrarianDashboard.INSTANCE.isInitialized()){
				LibrarianDashboard.INSTANCE.init();
			}

			LibrarianDashboard.INSTANCE.setVisible(true);
		}
		else if(currentAuth.name().equals("ADMIN")){
			if(!AdministratorsDashboard.INSTANCE.isInitialized())
			  AdministratorsDashboard.INSTANCE.init();
			AdministratorsDashboard.INSTANCE.setVisible(true);
		}
		else if(currentAuth.name().equals("BOTH")){

			if(!AdministratorsDashboard.INSTANCE.isInitialized())
			   BothDashboard.INSTANCE.init();
			BothDashboard.INSTANCE.setVisible(true);

		}else{
			throw new LoginException("Cannot Authorize");
		}
	}
	@Override
	public List<String> allMemberIds() {
		
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		
		List<String> retval = new ArrayList<>();
		List<Book> retBook = new ArrayList<>();

		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}

	/**
	 *  Add New book
	 * @param member
	 */
	public void saveLibraryMember(LibraryMember member){
		da.saveNewMember(member);
	}

	@Override
	public boolean addBook(String isbn , String title , int maxBorrowDays, List<Author> authors) throws BookCopyException {

		Book book = new Book(isbn, title, maxBorrowDays, authors);
		BookController bookController = new BookController();
		bookController.addNewBook(book);
		return true;
	}

	public void saveBook(Book book){
		BookController bookController = new BookController();
		bookController.addNewBook(book);
	}

	/**
	 *
	 * @param ISBN
	 * @param copyNumber
	 * @return
	 */
	public boolean addBookCopy(String ISBN , int copyNumber) throws BookCopyException{

		BookCopyController bookCopyController = new BookCopyController();
		 if(bookCopyController.copyNumberTakenCheck(copyNumber, ISBN))
			 throw  new BookCopyException("There is no book with ISBN = " + ISBN);
		 bookCopyController.addNewBookCopy(ISBN);
		return true;
	}

	
	public HashMap<String, LibraryMember> getMembers() {
		
		return da.readMemberMap();
	}

	public boolean checkMemberId(String member_id){
		if(!allMemberIds().contains(member_id.trim()))
			return false;
		return true;
	}

	@Override
	public Book checkBookISBN(String isbn) {

		if(!allBookIds().contains(isbn))
			return null;
		return getBooks().get(isbn);
	}

	@Override
	public HashMap<String, Book> getBooks() {
		 
		 return da.readBooksMap();
	}


	public Address addAddress(String street, String city , String state , String zip){
		return new Address(street, city, state, zip);
	}

	@Override
	public LibraryMember addLibraryMember(String memberNumber, String firstName, String lastName, String phoneNumber, Address address) {
		return new LibraryMember(memberNumber, firstName, lastName, phoneNumber, address);
	}

	@Override
	public void deleteMember(LibraryMember memberId) {
		da.deleteMember(memberId);
	}
}
