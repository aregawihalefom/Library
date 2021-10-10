package librarysystem.guiElements.checkOut;
import business.*;
import business.exceptions.BookCopyException;
import business.exceptions.LibraryMemberException;
import librarysystem.Config;
import librarysystem.Messages;
import librarysystem.Util;
import librarysystem.ruleSet.RuleException;
import librarysystem.ruleSet.RuleSet;
import librarysystem.ruleSet.RuleSetFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.HashMap;

public class PrintMemberCheckOut extends JPanel{


    public static  PrintMemberCheckOut INSTANCE = new PrintMemberCheckOut();

    // form elements
    private final String[] printMemberAttributes = {"Member ID"};
    private final JTextField[] printMemberFields = new JTextField[printMemberAttributes.length];

    // Gui elements
    private JTable myTable;
    private JPanel printMemberCheckOutPanel;

    private ControllerInterface ci = new SystemController();
    private JButton printButton = new JButton("Print");


    private PrintMemberCheckOut() {
        printMemberCheckOutForm();
        myTable = loadTableData();
        myTable.setDefaultEditor(Object.class , null);
        printMemberCheckOutPanel.add(new JScrollPane(myTable) , BorderLayout.AFTER_LAST_LINE);
        printButton.setEnabled(false);
        printButton.addActionListener(new PrintJTable());

    }

    private JTable loadTableData() {

        String column[]={"ISBN","TITLE","COPY N0.", "MEMBER ID"};
        DefaultTableModel model = new DefaultTableModel(null, column);

        model = listOverDueBooks(model);

        return new JTable(model);
    }
    public JTextField[] getprintMemberFields() {
        return printMemberFields;
    }

    private void printMemberCheckOutForm() {

        printMemberCheckOutPanel = new JPanel(new BorderLayout());
        JLabel panelTitle = new JLabel("Print Member Checkouts");
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(new JSeparator(JSeparator.HORIZONTAL));
        titlePanel.add(panelTitle);

        panelTitle.setFont(Config.DEFUALT_FONT);
        panelTitle.setForeground(Util.DARK_BLUE);
        printMemberCheckOutPanel.add(titlePanel , BorderLayout.NORTH);

        JPanel addFormPanel = createsprintMemberCheckOutForm();

        // add add button
        JButton searchBtn = new JButton("Search");
        searchBtn.addActionListener(new searchBookListener());

        addFormPanel.add(searchBtn);
        addFormPanel.add(printButton);

        // add to book Panel at the bottom
        printMemberCheckOutPanel.add(addFormPanel, BorderLayout.CENTER);

    }

    public  JPanel getPrintMemberCheckOutPanel(){ return printMemberCheckOutPanel; }

    private JPanel getElementWithLabelBook(String labelName, int jtextFieldIndex) {

        JLabel label = new JLabel(" " + labelName);
        printMemberFields[jtextFieldIndex] = new JTextField(20);

        JPanel nameForm = new JPanel();
        nameForm.add(label, BorderLayout.NORTH);
        nameForm.add(printMemberFields[jtextFieldIndex], BorderLayout.CENTER);

        return nameForm;
    }

    private JPanel createsprintMemberCheckOutForm() {

        JPanel bookFormPanel = new JPanel();
        for (int i = 0; i < printMemberFields.length; i++) {
            bookFormPanel.add(getElementWithLabelBook(printMemberAttributes[i], i));
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
        for(JTextField field : printMemberFields){
            field.setText("");
        }

    }

    private class searchBookListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) throws NumberFormatException {

            try {

                RuleSet searchRuleset = RuleSetFactory.getRuleSet(PrintMemberCheckOut.this);
                searchRuleset.applyRules(PrintMemberCheckOut.this);

                String memberId = printMemberFields[0].getText().trim();

                // check if member already exists
                if(!ci.allMemberIds().contains(memberId))
                    throw new BookCopyException("No Member  with ID  =  " + printMemberFields[0].getText().trim() + " found");

                // Check across library members

                int counter = printMembers(memberId);
                if(counter == 0)
                    new Messages.InnerFrame().showMessage("No Checkout record with Member ID = " + memberId, "Error");
                else{
                    new Messages.InnerFrame().showMessage(counter +" checkouts ready to be printed for Member ID =  " +memberId , "Info");
                    printButton.setEnabled(true);

                }

                clearFormFields();

            } catch (BookCopyException | RuleException | NumberFormatException  ex) {
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
                    model.insertRow(0, new  Object[]{ entry.getCopy().getBook().getIsbn(),  entry.getCopy().getBook().getTitle(),  entry.getCopy().getCopyNum(), member.getMemberId()});
                }
            }
        }

        return model;
    }

    // filtered by memberID
    int printMembers(String memberId){

        HashMap<String , LibraryMember> libraryMemberHashMap = ci.getMembers();
        DefaultTableModel model = (DefaultTableModel) myTable.getModel();
        int counter = 0 ;

        if( libraryMemberHashMap !=null){
            model.setRowCount(0);
            for(String key : libraryMemberHashMap.keySet()){
                LibraryMember member = libraryMemberHashMap.get(key);
               if(member.getMemberId().equals(memberId))
                for(CheckOutEntry entry : member.getRecord().getEntries()){
                    model.insertRow(0, new  Object[]{entry.getCopy().getBook().getIsbn(), entry.getCopy().getBook().getTitle(), entry.getCopy().getCopyNum(), member.getMemberId()});

                    System.out.println("ISBN :" + entry.getCopy().getBook().getIsbn());
                    System.out.println("Member ID :" + entry.getCopy().getBook().getTitle());
                    System.out.println("Copy NO :" +entry.getCopy().getCopyNum());
                    System.out.println("Member ID :" +key);
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

                    counter++;
                }
            }
        }

        return counter;
    }


    private class PrintJTable implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                myTable.print();
            } catch (PrinterException ex) {
                new Messages.InnerFrame().showMessage(ex.getMessage() + " Details printed to console", "Error");
                ;
            }
        }
    }
}
