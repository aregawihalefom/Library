package librarysystem.guiElements.checkOut;

import business.*;
import business.exceptions.*;

import librarysystem.Config;
import librarysystem.Messages;
import librarysystem.UIController;
import librarysystem.Util;
import librarysystem.ruleSet.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckOutBookPanel extends JPanel{

    private final String[] checkOutAttributes = {"Member ID", "ISBN"};

    public JTextField[] getCheckOutFields() {
        return checkOutFields;
    }
    private final JTextField[] checkOutFields = new JTextField[checkOutAttributes.length];
    private JPanel addCheckoutForm;

    public static final CheckOutBookPanel INSTANCE = new CheckOutBookPanel();

    CheckOutBookPanel() {  addCheckoutForm();}

    private ControllerInterface ci = new SystemController();

    public  JPanel getCheckOutPanel() {
        return addCheckoutForm;
    }

    private void addCheckoutForm() {

        addCheckoutForm = new JPanel(new BorderLayout());

        // Panel Title
        JLabel panelTitle = new JLabel(" CheckOut Book ");
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(new JSeparator(JSeparator.HORIZONTAL));
        titlePanel.add(panelTitle);
        panelTitle.setFont(Config.DEFUALT_FONT);
        panelTitle.setForeground(Util.DARK_BLUE);
        addCheckoutForm.add(titlePanel , BorderLayout.NORTH);

        JPanel checkOutPanel = createCheckOutForm();
        addCheckoutForm.add(checkOutPanel , BorderLayout.CENTER);

        // add add button
        JButton checkoutBtn = new JButton("Check Out");
        checkoutBtn.addActionListener(new checkOutListener());
        checkOutPanel.add(checkoutBtn);


        // add to book Panel at the bottom
        addCheckoutForm.add(checkoutBtn, BorderLayout.SOUTH);

    }


    private JPanel getElementWithLabel(String labelName, int jtextFieldIndex) {

        JLabel label = new JLabel(" " + labelName);
        checkOutFields[jtextFieldIndex] = new JTextField(20);

        JPanel nameForm = new JPanel();
        nameForm.add(label);
        nameForm.add(checkOutFields[jtextFieldIndex]);

        return nameForm;
    }


    private JPanel createCheckOutForm() {

        JPanel checkoutFormPanel = new JPanel();
        for (int i = 0; i < checkOutFields.length; i++) {
            checkoutFormPanel.add(getElementWithLabel(checkOutAttributes[i], i));
        }
        return checkoutFormPanel;
    }

    private class checkOutListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) throws NumberFormatException {

            try {

                // First check standard rules
                RuleSet checkoutRules = RuleSetFactory.getRuleSet(CheckOutBookPanel.this);
                checkoutRules.applyRules(CheckOutBookPanel.this);

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
                //ci.saveCheckout(member.getMemberId(), member.getRecord());

                // Save member
                ci.saveLibraryMember(member);

                // Save the book
                ci.addBook(book.getIsbn(), book.getTitle(), book.getMaxCheckoutLength(), book.getAuthors());


                // Now update tables
                clearFormFields();

                // update the tables
                // "Member ID","ISBN","Checkout Date", "Due Date"
                CheckOutEntry entry = member.getRecord().getEntries().get( member.getRecord().getEntries().size()-1);
                DefaultTableModel model = (DefaultTableModel) UIController.INSTANCE.librarianDashboard.checkOutList.getModel();
                model.insertRow(0, new  Object[]{ member.getMemberId() , book.getIsbn(),  entry.getCheckOutDate(), entry.getDueDate()});

                // successful
                new Messages.InnerFrame().showMessage("Check out successfully completed", "Info");

            } catch (NumberFormatException ex){
                new Messages.InnerFrame().showMessage("Input for Max days should be a number", "Error");
            } catch (RuleException | CheckOutException | LibraryMemberException | BookCopyException ex) {
                new Messages.InnerFrame().showMessage(ex.getMessage(), "Error");
            }

        }
    }
    public void clearFormFields(){
        for(JTextField field : checkOutFields){
            field.setText("");
        }

    }
}
