package librarysystem.ruleSet;

import librarysystem.Util;
import librarysystem.guiElements.member.MemberUI;

import javax.swing.*;
import java.awt.*;

public class MemberRuleSet implements RuleSet {

    private MemberUI memberGui;

    @Override
    public void applyRules(Component ob) throws RuleException {
         memberGui = (MemberUI) ob;
         nonemptyRule();
         idNumericRule();
         zipNumericRule();
         stateRule();
         // idNotZipRule();
    }

    private void nonemptyRule() throws RuleException {

        for(JTextField field : memberGui.getMemberFields()){
            if(field.getText().isEmpty())
                throw new RuleException("All fields must be non-empty");
        }

    }

    private void idNumericRule() throws RuleException {
        String val = memberGui.getMemberFields()[3].getText();
        try {
            Integer.parseInt(val);
            //val is numeric
        } catch(NumberFormatException e) {
            throw new RuleException("Phone Number should be numeric");
        }
    }

    private void zipNumericRule() throws RuleException {
        String val =  memberGui.getMemberFields()[memberGui.getMemberFields().length-1].getText().trim();
        try {
            Integer.parseInt(val);
        } catch(NumberFormatException e) {
            throw new RuleException("Zipcode must be numeric");
        }
        if(val.length() != 5) throw new RuleException("Zipcode must have 5 digits");
    }

    private void stateRule() throws RuleException {

        String state = memberGui.getMemberFields()[memberGui.getMemberFields().length-2].getText().trim();
        if(state.length() != 2) throw new RuleException("State field must have two characters");
        if(!Util.isInRangeAtoZ(state.charAt(0))
                || !Util.isInRangeAtoZ(state.charAt(1))) {
            throw new RuleException("Characters is state field must be in range A-Z");
        }
    }


}
