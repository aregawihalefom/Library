package librarysystem.ruleSet;

import librarysystem.guiElements.book.BookGui;

import javax.swing.*;
import java.awt.*;

public class BookRuleSet implements RuleSet {

    private BookGui bookGui;

    @Override
    public void applyRules(Component ob) throws RuleException {

        bookGui= (BookGui) ob;
        nonemptyRule();
        maxDays();
    }

    private void nonemptyRule() throws RuleException {
        for(JTextField field : bookGui.getBookFields()){
            if(field.getText().isEmpty())
                throw new RuleException("All fields must be non-empty");
        }
    }

    private void maxDays() throws RuleException{

        int maxBorrowDays = Integer.parseInt(bookGui.getBookFields()[2].getText());
        if(maxBorrowDays != 7 && maxBorrowDays != 21)
            throw new RuleException("Maximum checkout days it either 7 or 21 according to the university rules");

    }
}
