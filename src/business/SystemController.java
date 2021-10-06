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
import librarysystem.LibrarianDashboard;
import librarysystem.LibrarySystem;
import librarysystem.guiElements.UtilGui;

public class SystemController implements ControllerInterface {

	public static Auth currentAuth = null;
	
	public void login(String id, String password) throws LoginException {

		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();

		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}

		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Username/Password combination is incorrect");
		}
		currentAuth = map.get(id).getAuthorization();
		System.out.println(currentAuth.name());

		if(currentAuth.name().equals("LIBRARIAN")){
			LibrarySystem.hideAllWindows();
			LibrarianDashboard.INSTANCE.init();
			LibrarianDashboard.INSTANCE.setVisible(true);
		}
		else if(currentAuth.name().equals("ADMIN")){
			UtilGui.hideAllWindows();
			AdministratorsDashboard.INSTANCE.init();
			AdministratorsDashboard.INSTANCE.setVisible(true);
		}
		else if(currentAuth.name().equals("BOTH")){

//			UtilGui.hideAllWindows();
//			AdministratorsDashboard.INSTANCE.init();
//			AdministratorsDashboard.INSTANCE.setVisible(true);

		}else{
			throw new LoginException("Cannot Authorize");
		}

	}
	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
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
		DataAccess da = new DataAccessFacade();
		da.saveNewMember(member);
	}

	@Override
	public boolean addBook(Book book) throws BookCopyException {
		BookController bookController = new BookController();
		if(bookController.bookAlreadyAdded(book.getIsbn(), allBookIds()))
			throw  new BookCopyException("Book with ISBN = " + book.getIsbn() + " already exists.");
		bookController.addNewBook(book);
		return true;
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

	@Override
	public HashMap<String, LibraryMember> getMembers() {
		DataAccess da = new DataAccessFacade();
		return da.readMemberMap();
	}

	public boolean checkMemberId(String member_id){
		System.out.println(allMemberIds());
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
		 DataAccess da = new DataAccessFacade();
		 return da.readBooksMap();
	}

	public Address addAddress(String street, String city , String state , String zip){
		return new Address(street, city, state, zip);
	}

	@Override
	public LibraryMember addLibraryMember(String memberNumber, String firstName, String lastName, String phoneNumber, Address address) {
		return new LibraryMember(memberNumber, firstName, lastName, phoneNumber, address);
	}
}
