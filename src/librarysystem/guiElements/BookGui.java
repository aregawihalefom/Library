package librarysystem.guiElements;

import business.Book;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import business.exceptions.BookCopyException;
import librarysystem.Config;
import librarysystem.Messages;
import librarysystem.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookGui extends JPanel{

    private String[] bookAttributes = {"Title", "ISBN", "Max days" , "Authors" };
    private JTextField[] bookFields = new JTextField[bookAttributes.length];
    private JPanel addBookPanel;
    public static final BookGui INSTANCE = new BookGui();

    BookGui() {  addBookForm();}

    private ControllerInterface ci = new SystemController();

    public JTextField[] getBookFields() {
        return bookFields;
    }

    private void addBookForm() {

        addBookPanel = new JPanel(new BorderLayout());
        JLabel panelTitle = new JLabel(" Add New Book");
        panelTitle.setFont(Config.DEFUALT_FONT);
        panelTitle.setForeground(Util.DARK_BLUE);
        addBookPanel.add(panelTitle , BorderLayout.NORTH);

        JPanel addFormPanel = createAddBookForm();
        addBookPanel.add(addFormPanel , BorderLayout.CENTER);

        // add add button
        JButton addBBookBtn = new JButton("Add Member");
        addBBookBtn.addActionListener(new addBookListiner());
        JPanel addBookBtnPanel = new JPanel(new BorderLayout());
        addBookBtnPanel.add(addBBookBtn, BorderLayout.CENTER);

        // add to book Panel at the bottom
        addBookPanel.add(addBookBtnPanel, BorderLayout.SOUTH);

    }

    public  JPanel getAddBookPanel(){ return addBookPanel; }

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

    public  JScrollPane getBookList() {
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

                Book book = new Book(isbn, title, maxBorrowDays, new ArrayList<>());
                boolean status = ci.addBook(book);
                new Messages.InnerFrame().showMessage("New book added successfully","Info");
                System.out.println(ci.allBookIds().toString());

            } catch (BookCopyException ex) {
                new Messages.InnerFrame().showMessage(ex.getMessage(), "Error");
            }catch (NumberFormatException ex){
                new Messages.InnerFrame().showMessage("Input for Max days should be a number", "Error");
            }

        }
    }

}
