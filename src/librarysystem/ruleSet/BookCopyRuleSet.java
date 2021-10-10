package librarysystem.ruleSet;

import business.BookCopy;
import librarysystem.guiElements.book.AddBookCopyPanel;
import librarysystem.guiElements.book.BookGui;

import javax.swing.*;
import java.awt.*;

public class BookCopyRuleSet implements RuleSet {

    private AddBookCopyPanel bookCopyPanel;

    @Override
    public void applyRules(Component ob) throws RuleException {

        bookCopyPanel = (AddBookCopyPanel) ob;
        nonemptyRule();
        //maxDays();
    }

    private void nonemptyRule() throws RuleException {
        for(JTextField field : bookCopyPanel.getBookFields()){
            if(field.getText().isEmpty())
                throw new RuleException("All fields must be non-empty");
        }
    }

//    private void maxDays() throws RuleException{
//
//        int maxBorrowDays = Integer.parseInt(bookCopyPanel.getBookFields()[1].getText());
//        if(maxBorrowDays <= 0)
//            throw new RuleException("Number of copies should be greater than one");
//
//    }
}
