package dataaccess;

import java.util.HashMap;

import business.Book;
import business.CheckOutRecord;
import business.LibraryMember;
import dataaccess.DataAccessFacade.StorageType;

public interface DataAccess { 
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	public HashMap<String , CheckOutRecord> readCheckOutRecord();
	public void saveNewMember(LibraryMember member);
    public void saveNewBook(Book book);
	public void saveNewUser(User user);
	public void saveCheckout(String memberId , CheckOutRecord record);


	void deleteMember(LibraryMember member);
}
