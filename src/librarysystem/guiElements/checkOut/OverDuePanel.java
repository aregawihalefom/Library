package librarysystem.guiElements.checkOut;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OverDuePanel extends JPanel{


    public static  OverDuePanel INSTANCE = new OverDuePanel();


    // form elements
    private final String[] overDueAttributes = {"ISBN"};
    private final JTextField[] overDueFields = new JTextField[overDueAttributes.length];

    // Gui elements
    private JTable myTable;
    private JPanel overDuePanel;

    private ControllerInterface ci = new SystemController();

    private OverDuePanel() {
        overDueForm();
        myTable = loadTableData();
        myTable.setDefaultEditor(Object.class , null);
        overDuePanel.add(new JScrollPane(myTable) , BorderLayout.AFTER_LAST_LINE);
    }

    private JTable loadTableData() {

        String column[]={"ISBN","TITLE","COPY N0.", "MEMBER ID"};
        DefaultTableModel model = new DefaultTableModel(null, column);

        model = listOverDueBooks(model);

        return new JTable(model);
    }
    public JTextField[] getOverDueFields() {
        return overDueFields;
    }

    private void overDueForm() {

        overDuePanel = new JPanel(new BorderLayout());
        JLabel panelTitle = new JLabel("Search Over Due Books");
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(new JSeparator(JSeparator.HORIZONTAL));
        titlePanel.add(panelTitle);

        panelTitle.setFont(Config.DEFUALT_FONT);
        panelTitle.setForeground(Util.DARK_BLUE);
        overDuePanel.add(titlePanel , BorderLayout.NORTH);

        JPanel addFormPanel = createsoverDueForm();

        // add add button
        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(new searchBookListener());

        addFormPanel.add(searchBtn);

        // add to book Panel at the bottom
        overDuePanel.add(addFormPanel, BorderLayout.CENTER);

    }

    public  JPanel getSearchOverDuePanel(){ return overDuePanel; }

    private JPanel getElementWithLabelBook(String labelName, int jtextFieldIndex) {

        JLabel label = new JLabel(" " + labelName);
        overDueFields[jtextFieldIndex] = new JTextField(20);

        JPanel nameForm = new JPanel();
        nameForm.add(label, BorderLayout.NORTH);
        nameForm.add(overDueFields[jtextFieldIndex], BorderLayout.CENTER);

        return nameForm;
    }

    private JPanel createsoverDueForm() {

        JPanel bookFormPanel = new JPanel();
        for (int i = 0; i < overDueFields.length; i++) {
            bookFormPanel.add(getElementWithLabelBook(overDueAttributes[i], i));
        }
        return bookFormPanel;
    }

    private void addRowToJTable(Book book , String member_Id , int copyNum){

        DefaultTableModel model = (DefaultTableModel) myTable.getModel();
        if(model.getRowCount() > 0)
            model.setRowCount(0);
        model.insertRow(0, new  Object[]{book.getIsbn() , book.getTitle(), member_Id, copyNum, member_Id});

    }

    public void clearFormFields(){
        for(JTextField field : overDueFields){
            field.setText("");
        }

    }

    private class searchBookListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) throws NumberFormatException {

            try {


                RuleSet searchRuleset = RuleSetFactory.getRuleSet(OverDuePanel.this);
                searchRuleset.applyRules(OverDuePanel.this);

                String isbn = overDueFields[0].getText().trim();

                // check if member already exists
                if(!ci.allBookIds().contains(isbn))
                    throw new BookCopyException("No book with ISBN  =  " + overDueFields[0].getText().trim() + " found");

                // Check across library members

                int counter = listOverDueBooksByIsbn(isbn);
                if(counter == 0)
                      new Messages.InnerFrame().showMessage("No OverDue copies with ISBN = " + isbn, "Error");
              else
                    new Messages.InnerFrame().showMessage(counter +" copies of ISBN = " + isbn + " found ", "Info");
                clearFormFields();

            } catch (BookCopyException | RuleException | NumberFormatException ex) {
                new Messages.InnerFrame().showMessage(ex.getMessage(), "Error");
            }

        }
    }

    DefaultTableModel listOverDueBooks(DefaultTableModel model){

        HashMap<String , LibraryMember> libraryMemberHashMap = ci.getMembers();

        if( libraryMemberHashMap !=null){

            for(String key : libraryMemberHashMap.keySet()){
                LibraryMember member = libraryMemberHashMap.get(key);
                for(CheckOutEntry entry : member.getRecord().getEntries()){
                    if(entry.getDueDate().isBefore(LocalDate.now())){
                        model.insertRow(0, new  Object[]{entry.getCopy().getBook().getIsbn(),  key, entry.getCopy().getCopyNum(), member.getMemberId()});
                    }

                }
            }
        }

        return model;
    }

    // filtered by isbn
    int listOverDueBooksByIsbn(String isbn){

        HashMap<String , LibraryMember> libraryMemberHashMap = ci.getMembers();
        DefaultTableModel model = (DefaultTableModel) myTable.getModel();

        model.setRowCount(0);
        int counter = 0 ;
        if( libraryMemberHashMap !=null){

            for(String key : libraryMemberHashMap.keySet()){
                LibraryMember member = libraryMemberHashMap.get(key);
                for(CheckOutEntry entry : member.getRecord().getEntries()){
                   if(entry.getCopy().getBook().getIsbn().equals(isbn)){

                       if(entry.getDueDate().isBefore(LocalDate.now())){
                           model.insertRow(0, new  Object[]{ key , entry.getCopy().getBook().getIsbn(), entry.getCopy().getCopyNum(), member.getMemberId()});
                           counter++;
                       }
                   }
                }
            }
        }



        return counter;
    }

}
