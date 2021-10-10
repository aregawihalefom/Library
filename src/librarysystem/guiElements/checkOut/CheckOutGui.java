package librarysystem.guiElements.checkOut;

import business.*;
import business.exceptions.*;

import librarysystem.Config;
import librarysystem.Messages;
import librarysystem.Util;
import librarysystem.ruleSet.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.HashMap;

public class CheckOutGui extends JPanel{

    private final String[] checkOutAttributes = {"Member ID", "ISBN"};

    public JTextField[] getCheckOutFields() {
        return checkOutFields;
    }
    private final JTextField[] checkOutFields = new JTextField[checkOutAttributes.length];
    private JPanel addCheckoutForm;

    public static final CheckOutGui INSTANCE = new CheckOutGui();

    CheckOutGui() {
        addCheckoutForm();
    }

    private ControllerInterface ci = new SystemController();

    public  JPanel getCheckOutPanel() {
        return addCheckoutForm;
    }

    private void addCheckoutForm() {

        addCheckoutForm = new JPanel(new BorderLayout());
        JLabel panelTitle = new JLabel(" Check out Book");
        panelTitle.setFont(Config.DEFUALT_FONT);
        panelTitle.setForeground(Util.DARK_BLUE);
        addCheckoutForm.add(panelTitle , BorderLayout.NORTH);

        JPanel addFormPanel = createCheckOutForm();
        addCheckoutForm.add(addFormPanel , BorderLayout.CENTER);

        // add add button
        JButton checkoutBtn = new JButton("Check Out");
        checkoutBtn.addActionListener(new checkOutListener());
        JPanel checkoutBtnPanel = new JPanel(new BorderLayout());
        checkoutBtnPanel.add(checkoutBtn, BorderLayout.CENTER);

        // add to book Panel at the bottom
        addCheckoutForm.add(checkoutBtnPanel, BorderLayout.SOUTH);

    }


    private JPanel getElementWithLabelBook(String labelName, int jtextFieldIndex) {

        JLabel label = new JLabel(" " + labelName);
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.add(label, BorderLayout.NORTH);

        checkOutFields[jtextFieldIndex] = new JTextField(20);
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.add(checkOutFields[jtextFieldIndex], BorderLayout.NORTH);

        JPanel nameForm = new JPanel(new BorderLayout());
        nameForm.add(labelPanel, BorderLayout.NORTH);
        nameForm.add(formPanel, BorderLayout.CENTER);

        return nameForm;
    }

    public  JTable getCheckOutList() {

        String column[]={"Member ID","ISBN","COPY N0.", "CHECKOUT DATE", "DUE DATE"};
        HashMap<String , LibraryMember> libraryMemberHashMap = ci.getMembers();
        DefaultTableModel model = new DefaultTableModel(null, column);
        if( libraryMemberHashMap !=null){

            for(String key : libraryMemberHashMap.keySet()){
                LibraryMember member = libraryMemberHashMap.get(key);
                for(CheckOutEntry entry : member.getRecord().getEntries()){
                    model.addRow(new  Object[]{ key , entry.getCopy().getBook().getIsbn(), entry.getCopy().getCopyNum(), entry.getCheckOutDate(), entry.getDueDate()});
                }
            }
        }
        return new JTable(model);
    }

    private JPanel createCheckOutForm() {

        JPanel checkoutFormPanel = new JPanel(new GridLayout(checkOutFields.length, 0));
        for (int i = 0; i < checkOutFields.length; i++) {
            checkoutFormPanel.add(getElementWithLabelBook(checkOutAttributes[i], i));
        }
        return checkoutFormPanel;
    }

    private class checkOutListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) throws NumberFormatException {

            try {

                // First check standard rules
                RuleSet checkoutRules = RuleSetFactory.getRuleSet(CheckOutGui.this);
                checkoutRules.applyRules(CheckOutGui.this);

                // Get the values
                String memeberId = checkOutFields[0].getText().trim();
                String isbn = checkOutFields[1].getText().trim();


                // Check if member is available
                if( !ci.checkMemberId(memeberId))
                    throw new RuleException("No member found with Member ID = "+ memeberId);

                // Check if book is available
                Book book = ci.checkBookISBN(isbn);
                if(book == null)
                    throw new RuleException("No book with ISBN = "+ isbn + " found");

                // Check if book is available
               if(!book.isAvailable())
                   throw new CheckOutException("The book with ISBN  = "+ isbn +" is not currently available");

               // Now available copy
                BookCopy copy = book.getNextAvailableCopy();

               if(copy == null)
                   throw new CheckOutException("No copies of this book available currently");

               // change status of the book
               copy.changeAvailability();

               // Create record -> checkout date , due date and max_days
               //  Are Incorporated already
                LibraryMember member = ci.getMembers().get(memeberId);

                // Record check out for a given
                member.addCheckoutRecord(copy);

                // Save checkout
               // ci.saveCheckout(member.getMemberId(), member.getRecord());

                // Save member
               ci.saveLibraryMember(member);


                // Save the book
                ci.addBook(book.getIsbn(), book.getTitle(), book.getMaxCheckoutLength(), (ArrayList<Author>) book.getAuthors());

                // successful
                new Messages.InnerFrame().showMessage("Check out successfully completed", "Info");

            } catch (NumberFormatException ex){
                new Messages.InnerFrame().showMessage("Input for Max days should be a number", "Error");
            } catch (RuleException | CheckOutException | LibraryMemberException | BookCopyException ex) {
                new Messages.InnerFrame().showMessage(ex.getMessage(), "Error");
           }

        }
    }
}
