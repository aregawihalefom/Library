package business;

import java.io.Serializable;

final public class LibraryMember extends Person implements Serializable {
	private String memberId;
	private CheckOutRecord record  = new CheckOutRecord();
	
	public LibraryMember(String memberId, String fname, String lname, String tel, Address add) {
		super(fname,lname, tel, add);
		this.memberId = memberId;
	}
	
	public String getMemberId() {
		return memberId;
	}

	/***
	 *  Checking out book
	 * @return
	 */

	public void addCheckoutRecord(BookCopy copy){
		this.record.addCheckOutEntry(copy);
	}
	
	
	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + 
				", " + getTelephone() + " " + getAddress() + record.toString();
	}

	private static final long serialVersionUID = -2226197306790714013L;
}
