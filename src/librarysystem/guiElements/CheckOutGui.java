package librarysystem.guiElements;

import business.*;
import business.exceptions.*;

import librarysystem.Config;
import librarysystem.Messages;
import librarysystem.Util;
import librarysystem.ruleSet.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.HashMap;
import java.util.List;

public class CheckOutGui extends JPanel{

    private final String[] checkOutAttributes = {"Member ID", "ISBN"};

    public JTextField[] getCheckOutFields() {
        return checkOutFields;
    }

    private final JTextField[] checkOutFields = new JTextField[checkOutAttributes.length];
    private JPanel addCheckoutForm;

    public static final CheckOutGui INSTANCE = new CheckOutGui();

    CheckOutGui() {  addCheckoutForm();}

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

    public  JScrollPane getCheckOutList() {

        System.out.println(ci.getBooks());
        String column[]={"ISBN","TITLE","AUTHORS", "MAX BORROW DAYS", "NUMBER OF COPIES"};
        HashMap<String , Book> bookHashMap = ci.getBooks();

        String bookData [][] = new String[bookHashMap.size()][column.length];
        List<String> bookID = ci.allBookIds();

        for(int i = 0 ; i < bookID.size(); i++){

            Book book = bookHashMap.get(bookID.get(i));
            bookData[i][0] = book.getIsbn();
            bookData[i][1] = book.getTitle();

            bookData[i][2] = book.getAuthors().toString();
            bookData[i][3] = ""+book.getMaxCheckoutLength();
            bookData[i][4] = ""+book.getNumCopies();
        }

        JTable jt=new JTable(bookData,column);
        JScrollPane sp=new JScrollPane(jt);
        return sp;

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

                System.out.println(memeberId);

                // Check if member is available
                if( !ci.checkMemberId(memeberId))
                    throw new RuleException("No member found with Member ID = "+ memeberId);

                //System.out.println(member);
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
                member.addCheckoutRecord(copy);

                // Save member
                ci.saveLibraryMember(member);

                // Save the user
                ci.addBook(book);

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
