package business;

import java.util.HashMap;
import java.util.List;

import business.exceptions.BookCopyException;
import business.exceptions.LibraryMemberException;

import javax.swing.*;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	public boolean addMember(LibraryMember memberId) throws LibraryMemberException;
	public boolean addBook(Book book) throws BookCopyException;
	public boolean addBookCopy(String ISBN , int copyNumber) throws BookCopyException;

	public HashMap<String, LibraryMember> getMembers();
}
