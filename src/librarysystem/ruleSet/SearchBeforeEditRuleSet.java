package librarysystem.ruleSet;

import business.exceptions.LibraryMemberException;
import librarysystem.Util;
import librarysystem.guiElements.member.EditOrDeleteMember;
import librarysystem.guiElements.member.MemberUI;

import javax.swing.*;
import java.awt.*;

public class SearchBeforeEditRuleSet implements RuleSet {

    private EditOrDeleteMember editOrDeleteMember;

    @Override
    public void applyRules(Component ob) throws RuleException {
        editOrDeleteMember = (EditOrDeleteMember) ob;
        nonEmptyMemberId();
        // idNotZipRule();
    }


    public void nonEmptyMemberId() throws RuleException {
        if(editOrDeleteMember.getMemberFields()[0].getText().isEmpty()) {
            throw new RuleException("Enter Member ID and search for Member before Deleting/Editing");
        }

    }


    private void nonemptyRule() throws RuleException {

        for(JTextField field : editOrDeleteMember.getMemberFields()){
            if(field.getText().isEmpty())
                throw new RuleException("All fields must be non-empty");
        }

    }

    private void idNumericRule() throws RuleException {
        String val = editOrDeleteMember.getMemberFields()[3].getText();
        try {
            Integer.parseInt(val);
            //val is numeric
        } catch(NumberFormatException e) {
            throw new RuleException("Phone Number should be numeric");
        }
    }

    private void zipNumericRule() throws RuleException {
        String val =  editOrDeleteMember.getMemberFields()[editOrDeleteMember.getMemberFields().length-1].getText().trim();
        try {
            Integer.parseInt(val);
        } catch(NumberFormatException e) {
            throw new RuleException("Zipcode must be numeric");
        }
        if(val.length() != 5) throw new RuleException("Zipcode must have 5 digits");
    }

    private void stateRule() throws RuleException {

        String state = editOrDeleteMember.getMemberFields()[editOrDeleteMember.getMemberFields().length-2].getText().trim();
        if(state.length() != 2) throw new RuleException("State field must have two characters");
        if(!Util.isInRangeAtoZ(state.charAt(0))
                || !Util.isInRangeAtoZ(state.charAt(1))) {
            throw new RuleException("Characters is state field must be in range A-Z");
        }
    }


}
