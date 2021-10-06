package librarysystem.ruleSet;

import librarysystem.guiElements.MemberGui;

import javax.swing.*;
import java.awt.*;

public class MemberRuleSet implements RuleSet {

    private MemberGui memberGui;

    @Override
    public void applyRules(Component ob) throws RuleException {
        memberGui = (MemberGui) ob;
        nonemptyRule();
//        idNumericRule();
//        zipNumericRule();
//        stateRule();
//        idNotZipRule();
    }

    private void nonemptyRule() throws RuleException {

        for(JTextField field : memberGui.getMemberFields()){
            if(field.getText().isEmpty())
                throw new RuleException("All fields must be non-empty");
        }

    }



}
