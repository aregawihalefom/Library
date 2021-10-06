package librarysystem.ruleSet;

import librarysystem.guiElements.BookGui;
import librarysystem.guiElements.MemberGui;

import javax.swing.*;
import java.awt.*;

public class BookRuleSet implements RuleSet {

    private BookGui memberGui;

    @Override
    public void applyRules(Component ob) throws RuleException {

        BookGui  bookGui= (BookGui) ob;
        nonemptyRule();
//        idNumericRule();
//        zipNumericRule();
//        stateRule();
//        idNotZipRule();
    }

    private void nonemptyRule() throws RuleException {
        for(JTextField field : memberGui.getBookFields()){
            if(field.getText().isEmpty())
                throw new RuleException("All fields must be non-empty");
        }

    }
}
