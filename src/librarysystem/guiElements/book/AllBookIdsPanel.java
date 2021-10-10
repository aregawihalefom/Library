package librarysystem.guiElements.book;

import business.*;
import business.exceptions.BookCopyException;
import librarysystem.Config;
import librarysystem.Messages;
import librarysystem.UIController;
import librarysystem.Util;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AllBookIdsPanel extends JPanel{

    private String[] bookAttributes = {"Title", "ISBN", "Max days" , "Authors"};
    private JTextField[] bookFields = new JTextField[bookAttributes.length];

    private JPanel allBookIdsPanel;
    public static  AllBookIdsPanel INSTANCE = new AllBookIdsPanel();
    private JTable myTable;

    private ControllerInterface ci = new SystemController();

    private AllBookIdsPanel() {
        // UIController.INSTANCE.bookGui = this;
        addBookForm();
        myTable = loadTableData();
    }

    private JTable loadTableData() {

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

        DefaultTableModel model = new DefaultTableModel(bookData, column);
        return new JTable(model);
    }
    public JTextField[] getBookFields() {
        return bookFields;
    }

    private void addBookForm() {

        allBookIdsPanel = new JPanel(new BorderLayout());
        JLabel panelTitle = new JLabel(" Add New Book");
        panelTitle.setFont(Config.DEFUALT_FONT);
        panelTitle.setForeground(Util.DARK_BLUE);
        allBookIdsPanel.add(panelTitle , BorderLayout.NORTH);

        JPanel addFormPanel = createAddBookForm();
        allBookIdsPanel.add(addFormPanel , BorderLayout.CENTER);

        // add add button
        JButton addBBookBtn = new JButton("Add Book");
        addBBookBtn.addActionListener(new addBookListiner());
        JPanel addBookBtnPanel = new JPanel(new BorderLayout());
        addBookBtnPanel.add(addBBookBtn, BorderLayout.CENTER);

        // add to book Panel at the bottom
        allBookIdsPanel.add(addBookBtnPanel, BorderLayout.SOUTH);

    }

    public  JPanel getAllBookIdsPanel(){ return allBookIdsPanel; }

    private JPanel getElementWithLabelBook(String labelName, int jtextFieldIndex) {

        JLabel label = new JLabel(" " + labelName);
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.add(label, BorderLayout.NORTH);

        bookFields[jtextFieldIndex] = new JTextField(20);
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.add(bookFields[jtextFieldIndex], BorderLayout.NORTH);

        JPanel nameForm = new JPanel(new BorderLayout());
        nameForm.add(labelPanel, BorderLayout.NORTH);
        nameForm.add(formPanel, BorderLayout.CENTER);

        return nameForm;
    }

    public  JTable getBookList() {
        return this.myTable;
    }

    private JPanel createAddBookForm() {

        JPanel bookFormPanel = new JPanel(new GridLayout(bookAttributes.length, 0));
        for (int i = 0; i < bookFields.length; i++) {
            bookFormPanel.add(getElementWithLabelBook(bookAttributes[i], i));
        }
        return bookFormPanel;
    }

    private class addBookListiner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) throws NumberFormatException {

            try {

                String title = bookFields[0].getText().trim();
                System.out.println("Title ="+title);
                String isbn = bookFields[1].getText().trim();

                int maxBorrowDays = Integer.parseInt(bookFields[2].getText());
                Author aregawi = new Author("Aregawi", "Halefom", "12212" , new Address("1000 N. 4th st.", "Fairfield", "IA", "52557"), "Student");

                List<Author> authors = new ArrayList<Author>();
                authors.add(aregawi);
                ci.addBook(isbn, title, maxBorrowDays, (ArrayList<Author>) authors);

                // Now display success message
                new Messages.InnerFrame().showMessage("New book added successfully","Info");
                DefaultTableModel model = (DefaultTableModel) UIController.INSTANCE.admin.bookListJTable.getModel();
                model.addRow(new  Object[]{ isbn, title, authors.toString(), maxBorrowDays, 1});

            } catch (BookCopyException ex) {
                new Messages.InnerFrame().showMessage(ex.getMessage(), "Error");
            }catch (NumberFormatException ex){
                new Messages.InnerFrame().showMessage("Input for Max days should be a number", "Error");
            }

        }
    }

}
