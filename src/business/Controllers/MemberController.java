package business.Controllers;

import business.LibraryMember;
import dataaccess.DataAccessFacade;

import java.util.List;

public class MemberController {

    public boolean memberIdTakenCheck(String memberId, List<String> strings){
        return strings.contains(memberId);
    }

    public void addNewMember(LibraryMember member, DataAccessFacade dataAccessFacade) {
        dataAccessFacade.saveNewMember(member);
    }

}
