package librarysystem.guiElements;

import business.Book;
import business.ControllerInterface;
import business.SystemController;
import business.exceptions.BookCopyException;
import librarysystem.Config;
import librarysystem.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BookGui {

    private String[] bookAttributes = {"Title", "ISBN", "Max days" , "Authors" };
    private JTextField[] bookFields = new JTextField[bookAttributes.length];
    private JPanel addBookPanel;

    public static final BookGui INSTANCE = new BookGui();


    BookGui() {  addBookForm();}

    private ControllerInterface ci = new SystemController();

    private void addBookForm() {

        addBookPanel = new JPanel(new BorderLayout());
        JLabel panelTitle = new JLabel(" Add New Book Book");

        JPanel panelTitlePanel = new JPanel(new BorderLayout());
        panelTitlePanel.add(panelTitle);
        panelTitle.setFont(Config.DEFUALT_FONT);
        addBookPanel.add(panelTitle, BorderLayout.NORTH);

        JPanel bookFormPanel = createAddBookForm();

        addBookPanel.add(bookFormPanel, BorderLayout.SOUTH);

        // add add button
        JButton addBookBtn = new JButton("Add Book");
        addBookBtn.addActionListener(new addBookListiner());

        addBookPanel.add(addBookBtn, BorderLayout.SOUTH);

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
    public  JScrollPane createBookListPanel() {
        String data[][]={ {"100","Amit","670000", "1212"},
                {"102","Jai","780000", "1212312"},
                {"101","Sachin","700000", "2345314"}};
        String column[]={"Member ID","First Name","Last Name", "Phone Number"};
        JTable jt=new JTable(data,column);
        JScrollPane sp=new JScrollPane(jt);

        return sp;
    }

    private JPanel createAddBookForm() {

        JPanel bookFormPanel = new JPanel(new FlowLayout());
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
