package librarysystem;

import business.*;
import business.exceptions.BookCopyException;
import business.exceptions.LibraryMemberException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AdministratorsDashboard extends JFrame implements LibWindow {

    private static final long serialVersionUID = 1L;
    public static final AdministratorsDashboard INSTANCE = new AdministratorsDashboard();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;

    private JPanel addBookPanel;
    private JPanel addMemberPanel;
    private JPanel addCopyPanel;
    private JPanel adminDashobardPanel;
    private JPanel mainPanel;


    JList<ListItem> linkList;
    JPanel cards;

    private String[] bookAttributes = {"Title", "ISBN", "Max days" , "Authors" };
    private String[] memeberAttributes = {"Member Number", "First Name", "Last Name", "Phone Number"};
    private String[] copyAttributes = {"Copy Number", "Book ISBN"};

    private JTextField[] bookFields = new JTextField[bookAttributes.length];
    private JTextField[] memberFields = new JTextField[memeberAttributes.length];
    private JTextField[] copyFields = new JTextField[copyAttributes.length];

    public static  String[] sideBarItems;

    // list items which will be added to the ListModel for linkList
    ListItem item1, item2 , item3, item4;

    //Singleton class
    private AdministratorsDashboard() {

        setSize(Config.APP_WIDTH, Config.APP_WIDTH);
        sideBarItems = new String[]{"Admin Dashboard", "Add New Member", "Add New Book", "Add Copy"};

        // list items which will be added to the ListModel for linkList
        item1 = new ListItem(sideBarItems[0], true);
        item2 = new ListItem(sideBarItems[1], true);
        item3 = new ListItem(sideBarItems[2], true);
        item4 = new ListItem(sideBarItems[3], true);

    }

    public void init() {

        // Create sidebar
        createLinkLabels();

        // create main panels
        createMainPanels();

        // link my sidebar
        linkList.addListSelectionListener(event -> {
            String value = linkList.getSelectedValue().getItemName();
            boolean allowed = linkList.getSelectedValue().highlight();
            CardLayout cl = (CardLayout) (cards.getLayout());

            if (!allowed) {
                value = item1.getItemName();
                linkList.setSelectedIndex(0);
            }
            cl.show(cards, value);
        });
        linkList.setBackground(new java.awt.Color(204, 204, 255));
        linkList.setVisibleRowCount(4);
        linkList.setFixedCellHeight(40);
        linkList.setSelectionForeground(Color.BLACK);


        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, linkList, cards);
        splitPane.setDividerLocation(Config.DIVIDER);

        mainPanel = new JPanel(new FlowLayout());
        mainPanel.add(splitPane);

        add(splitPane);
        isInitialized = true;


    }

    public void createMainPanels() {

        // create addmin panel
        addAdmindDashBoardPanel();

        // Add member panel
        addMemberForm();

        // Add panel
        addBookForm();

        // Add copy panel
        addBookCopyForm();

        cards = new JPanel(new CardLayout());
        cards.add(adminDashobardPanel, item1.getItemName());
        cards.add(addMemberPanel, item2.getItemName());
        cards.add(addBookPanel, item3.getItemName());
        cards.add(addCopyPanel, item4.getItemName());

    }

    private void addAdmindDashBoardPanel() {

        adminDashobardPanel = new JPanel(new BorderLayout());
        adminDashobardPanel.add(new JLabel("Admin dashboard"), BorderLayout.NORTH);
        JScrollPane memberListPanel = createMemberListPanel();
        JScrollPane bookListPanel = createBookListPanel();


        JPanel p3=new JPanel();
        JLabel bookList =new JLabel("Book  copy List");
        p3.add(bookList);

        JTabbedPane tp=new JTabbedPane();
        tp.setPreferredSize(new Dimension(Config.APP_WIDTH - Config.DIVIDER, Config.APP_HEIGHT));
        tp.add("Members",memberListPanel);
        tp.add("Books",bookListPanel);
        tp.add("Book Copies",p3);
        tp.setFont(Config.DEFUALT_FONT);
        tp.setForeground(Util.LINK_AVAILABLE);
        tp.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        adminDashobardPanel.add(tp , BorderLayout.CENTER);

    }

    private JScrollPane createBookListPanel() {

        String data[][]={ {"100","Amit","670000", "1212"},
                {"102","Jai","780000", "1212312"},
                {"101","Sachin","700000", "2345314"}};
        String column[]={"Member ID","First Name","Last Name", "Phone Number"};
        JTable jt=new JTable(data,column);
        //jt.setBounds(30,40,200,300);
        JScrollPane sp=new JScrollPane(jt);

        return sp;
    }

    private void addBookForm() {

        addBookPanel = new JPanel(new BorderLayout());
        JLabel panelTitle = new JLabel(" Add Book");

        JPanel panelTitlePanel = new JPanel(new BorderLayout());
        panelTitlePanel.add(panelTitle);

        panelTitle.setFont(Config.DEFUALT_FONT);


        addBookPanel.add(panelTitle, BorderLayout.NORTH);

        JPanel bookFormPanel = createAddBookForm();

        addBookPanel.add(bookFormPanel, BorderLayout.SOUTH);

        // add add button
        JButton addBookBtn = new JButton("Add Book");
        addBookBtn.addActionListener(new addBookListiner());

//        JPanel addBookBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        addBookBtnPanel.add(addBookBtn);

        // add to book Panel at the bottom
        addBookPanel.add(addBookBtn, BorderLayout.SOUTH);

    }

    private void addMemberForm() {

        addMemberPanel = new JPanel(new BorderLayout());
        JLabel panelTitle = new JLabel(" Add Member");
        panelTitle.setFont(Config.DEFUALT_FONT);
        panelTitle.setForeground(Util.DARK_BLUE);

        addMemberPanel.add(panelTitle , BorderLayout.NORTH);

        JPanel memberFormPanel = createMemberForm();

        addMemberPanel.add(memberFormPanel , BorderLayout.CENTER);

        // add add button
        JButton addBMemberBtn = new JButton("Add Member");
        addBMemberBtn.addActionListener(new addMemberListiner());
        JPanel addBMemberBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addBMemberBtnPanel.add(addBMemberBtn);

        // add to book Panel at the bottom
        memberFormPanel.add(addBMemberBtn, BorderLayout.SOUTH);
    }

    private JScrollPane createMemberListPanel(){

        String column[]={"MEMBER NO.","FULL NAME","PHONE NO.", "Address"};
        HashMap<String , LibraryMember> memberHashMap = ci.getMembers();
        System.out.println(memberHashMap.size());
        String memberData [][] = new String[memberHashMap.size()][4];

        List<String> memberID = ci.allMemberIds();

        for(int i = 0 ; i < memberHashMap.size(); i++){


            LibraryMember member = memberHashMap.get(memberID.get(i));
            System.out.println(member.toString());
            memberData[i][0] = member.getMemberId();
            memberData[i][1] = member.getFirstName() + " " + member.getLastName();
            memberData[i][2] = member.getTelephone();
            memberData[i][3] = member.getAddress() != null ? member.getAddress().toString() : "";
        }

        JTable jt=new JTable(memberData,column);
        JScrollPane sp=new JScrollPane(jt);
        return sp;
    }

    private JPanel createMemberForm() {

        JPanel memberFormPanel = new JPanel(new FlowLayout());
        for (int i = 0; i < memberFields.length; i++) {
            memberFormPanel.add(getElementWithLabelMember(memeberAttributes[i], i));
        }
        return memberFormPanel;
    }

    private void addBookCopyForm() {

        addCopyPanel = new JPanel(new BorderLayout());
        JLabel panelTitle = new JLabel(" Add Book");
        panelTitle.setFont(Config.DEFUALT_FONT);
        panelTitle.setForeground(Util.LINK_AVAILABLE);

        addCopyPanel.add(panelTitle , BorderLayout.NORTH);

        JPanel memberFormPanel = createAddBookForm();

        addCopyPanel.add(memberFormPanel , BorderLayout.CENTER);

        // add add button
        JButton addBookBtn = new JButton("Add Book");
        addBookBtn.addActionListener(new addBookListiner());
        JPanel addBookBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addBookBtnPanel.add(addBookBtn);

        // add to book Panel at the bottom
        addCopyPanel.add(addBookBtnPanel, BorderLayout.SOUTH);
    }


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

    private JPanel getElementWithLabelMember(String labelName, int jtextFieldIndex) {

        JLabel label = new JLabel(" " + labelName);
        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.add(label, BorderLayout.NORTH);

        memberFields[jtextFieldIndex] = new JTextField(20);
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.add(memberFields[jtextFieldIndex], BorderLayout.NORTH);

        JPanel nameForm = new JPanel(new BorderLayout());
        nameForm.add(labelPanel, BorderLayout.NORTH);
        nameForm.add(formPanel, BorderLayout.CENTER);

        return nameForm;
    }

    private JPanel createAddBookForm() {

        JPanel bookFormPanel = new JPanel(new FlowLayout());
        for (int i = 0; i < bookFields.length; i++) {
            bookFormPanel.add(getElementWithLabelBook(bookAttributes[i], i));
        }
        return bookFormPanel;
    }


    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public void isInitialized(boolean val) {
        isInitialized = val;

    }

    @SuppressWarnings("serial")
    public void createLinkLabels() {

        DefaultListModel<ListItem> model = new DefaultListModel<>();
        model.addElement(item1);
        model.addElement(item2);
        model.addElement(item3);
        model.addElement(item4);

        linkList = new JList<ListItem>(model);
        linkList.setCellRenderer(new DefaultListCellRenderer() {

            @SuppressWarnings("rawtypes")
            @Override
            public Component getListCellRendererComponent(JList list,
                                                          Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {

                Component c = super.getListCellRendererComponent(list,
                        value, index, isSelected, cellHasFocus);
                if (value instanceof ListItem) {
                    ListItem nextItem = (ListItem) value;
                    setText(nextItem.getItemName());
                    if (nextItem.highlight()) {
                        setForeground(Util.LINK_AVAILABLE);
                    } else {
                        setForeground(Util.LINK_NOT_AVAILABLE);
                    }
                    if (isSelected) {
                        setForeground(Color.BLACK);
                        setBackground(new Color(236,243,245));
                    }
                    setFont(Config.DEFUALT_FONT);
                } else {
                    setText("illegal item");
                }
                return c;
            }

        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() ->
        {
           AdministratorsDashboard.INSTANCE.setTitle(Config.APP_NAME);
            AdministratorsDashboard.INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            AdministratorsDashboard.INSTANCE.init();
            centerFrameOnDesktop(AdministratorsDashboard.INSTANCE);
            AdministratorsDashboard.INSTANCE.setVisible(true);
        });
    }

    public static void centerFrameOnDesktop(Component f) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int height = toolkit.getScreenSize().height;
        int width = toolkit.getScreenSize().width;
        int frameHeight = f.getSize().height;
        int frameWidth = f.getSize().width;
        f.setLocation(((width - frameWidth) / 2), (height - frameHeight) / 3);
    }

    private class addBookListiner implements ActionListener  {
        @Override
        public void actionPerformed(ActionEvent e) throws NumberFormatException {

//            Author author = null;
//            for(int i = 0 ; i < bookFields.length; i++){
//
//                if(isEmptyString(bookFields[i].getText())){
//                    new Messages.InnerFrame().showMessage("All fields should be nonempty", "Error");
//                    System.out.println("This is not working");
//                    return;
//                }
//            }
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

    private class addMemberListiner implements  ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            for(JTextField input : memberFields){
                if(isEmptyString(input.getText())){
                    new Messages.InnerFrame().showMessage("All fields should be nonempty", "Error");
                    break;
                }
            }

            try {
                LibraryMember member = new LibraryMember(memberFields[0].getText(), memberFields[1].getText(),
                        memberFields[2].getText(), memberFields[3].getText(), null);
                System.out.println(ci.allMemberIds().toString());
                boolean status = ci.addMember(member);
                if(!status) throw new LibraryMemberException("Member Id is already taken");
                new Messages.InnerFrame().showMessage("Member added Successfully", "Info");
                System.out.println(ci.allMemberIds().toString());
            } catch (LibraryMemberException ex) {
                new Messages.InnerFrame().showMessage(ex.getMessage(), "Error");
            }
        }
    }

    public boolean isEmptyString(String str){

        return str.equals("");
    }



}
