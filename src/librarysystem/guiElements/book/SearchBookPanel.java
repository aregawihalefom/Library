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
import java.util.ArrayList;
import java.util.List;

public class SearchBookPanel extends JPanel{


    public static  SearchBookPanel INSTANCE = new SearchBookPanel();


    // form elements
    private final String[] bookAttributes = {"ISBN"};
    private final JTextField[] bookFields = new JTextField[bookAttributes.length];

    // Gui elements
    private JTable myTable;
    private JPanel searchBookPanel;

    private ControllerInterface ci = new SystemController();

    private SearchBookPanel() {
        searchBookForm();
        myTable = loadTableData();
        searchBookPanel.add(new JScrollPane(myTable) , BorderLayout.AFTER_LAST_LINE);
    }

    private JTable loadTableData() {

        String column[]={"ISBN","TITLE","AUTHORS", "MAX BORROW DAYS", "NUMBER OF COPIES"};
        DefaultTableModel model = new DefaultTableModel(null, column);
        return new JTable(model);
    }
    public JTextField[] getBookFields() {
        return bookFields;
    }

    private void searchBookForm() {

        searchBookPanel = new JPanel(new BorderLayout());
        JLabel panelTitle = new JLabel(" Search book by ISBN");
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(new JSeparator(JSeparator.HORIZONTAL));
        titlePanel.add(panelTitle);

        panelTitle.setFont(Config.DEFUALT_FONT);
        panelTitle.setForeground(Util.DARK_BLUE);
        searchBookPanel.add(titlePanel , BorderLayout.NORTH);

        JPanel addFormPanel = createsSearchBookForm();

        // add add button
        JButton addBookBtn = new JButton("Search");
        addBookBtn.addActionListener(new searchBookListener());

        addFormPanel.add(addBookBtn);
        // add to book Panel at the bottom
        searchBookPanel.add(addFormPanel, BorderLayout.CENTER);

    }

    public  JPanel getSearchBookPanel(){ return searchBookPanel; }

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

    private void addRowToJTable(Book book){

        DefaultTableModel model = (DefaultTableModel) myTable.getModel();
        if(model.getRowCount() > 0)
            model.setRowCount(0);
        model.addRow(new  Object[]{book.getIsbn() , book.getTitle(), book.getAuthors().toString(), book.getMaxCheckoutLength(), book.getNumCopies()});

    }

    public void clearFormFields(){
        for(JTextField field : bookFields){
            field.setText("");
        }

    }

    private class searchBookListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) throws NumberFormatException {

            try {


                RuleSet searchRuleset = RuleSetFactory.getRuleSet(SearchBookPanel.this);
                searchRuleset.applyRules(SearchBookPanel.this);

                String isbn = bookFields[0].getText().trim();

                System.out.println(ci.getBooks().toString());
                // check if member already exists
                if(!ci.allBookIds().contains(isbn))
                    throw new BookCopyException("No book with ISBN  =  " + bookFields[0].getText().trim() + " found");

                Book book = ci.getBooks().get(bookFields[0].getText().trim());

                if(book == null)
                    throw new BookCopyException("Unable to load book details");
                addRowToJTable(book);
                new Messages.InnerFrame().showMessage("1 Results found ", "Info");
                clearFormFields();

            } catch (BookCopyException | RuleException | NumberFormatException ex) {
                new Messages.InnerFrame().showMessage(ex.getMessage(), "Error");
            }

        }
    }

}
