package librarysystem.guiElements.book;

import business.*;
import business.exceptions.BookCopyException;
import business.exceptions.LibraryMemberException;
import librarysystem.Config;
import librarysystem.Messages;
import librarysystem.UIController;
import librarysystem.Util;
import librarysystem.ruleSet.RuleException;
import librarysystem.ruleSet.RuleSet;
import librarysystem.ruleSet.RuleSetFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AddBookCopyPanel extends JPanel{


    public static  AddBookCopyPanel INSTANCE = new AddBookCopyPanel();


    // form elements
    private final String[] bookAttributes = {"ISBN"};
    private final JTextField[] bookFields = new JTextField[bookAttributes.length];

    // Gui elements
    private JPanel AddBookCopyPanel;

    private ControllerInterface ci = new SystemController();

    private AddBookCopyPanel() {
        searchBookForm();
    }

    public JTextField[] getBookFields() {
        return bookFields;
    }

    private void searchBookForm() {

        AddBookCopyPanel = new JPanel(new BorderLayout());
        JLabel panelTitle = new JLabel(" Add Book Copy");
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(new JSeparator(JSeparator.HORIZONTAL));
        titlePanel.add(panelTitle);

        panelTitle.setFont(Config.DEFUALT_FONT);
        panelTitle.setForeground(Util.DARK_BLUE);
        AddBookCopyPanel.add(titlePanel , BorderLayout.NORTH);

        JPanel addFormPanel = createsSearchBookForm();

        //A button
        JButton addBookBtn = new JButton("Add copy");
        addBookBtn.addActionListener(new AddBookCopyListener());

        addFormPanel.add(addBookBtn);
        // add to book Panel at the bottom
        AddBookCopyPanel.add(addFormPanel, BorderLayout.CENTER);

    }

    public  JPanel getAddBookCopyPanel(){ return AddBookCopyPanel; }

    private JPanel getElementWithLabelBook(String labelName, int jtextFieldIndex) {

        JLabel label = new JLabel(" " + labelName);
        bookFields[jtextFieldIndex] = new JTextField(20);

        JPanel nameForm = new JPanel();
        nameForm.add(label, BorderLayout.NORTH);
        nameForm.add(bookFields[jtextFieldIndex], BorderLayout.CENTER);

        return nameForm;
    }

    private JPanel createsSearchBookForm() {

        JPanel bookFormPanel = new JPanel();
        for (int i = 0; i < bookFields.length; i++) {
            bookFormPanel.add(getElementWithLabelBook(bookAttributes[i], i));
        }
        return bookFormPanel;
    }


    public void clearFormFields(){
        for(JTextField field : bookFields){
            field.setText("");
        }

    }

    private class AddBookCopyListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) throws NumberFormatException {

            try {

                RuleSet searchRuleset = RuleSetFactory.getRuleSet(AddBookCopyPanel.this);
                searchRuleset.applyRules(AddBookCopyPanel.this);

                String isbn = bookFields[0].getText().trim();

                // check if member already exists
                if(!ci.allBookIds().contains(isbn))
                    throw new BookCopyException("No book with ISBN  =  " + isbn + " found");
                Book book = ci.getBooks().get(isbn);

                if(book == null)
                    throw new BookCopyException("Unable to load book details");

                // add copy
                book.addCopy();

                // update book
                ci.saveBook(book);
                new Messages.InnerFrame().showMessage(  "1 Copy added successfully ", "Info");
                clearFormFields();

            } catch (BookCopyException | RuleException | NumberFormatException ex) {
                new Messages.InnerFrame().showMessage(ex.getMessage(), "Error");
            }

        }
    }

}
