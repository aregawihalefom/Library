package librarysystem;

import business.ControllerInterface;
import business.SystemController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AdminstratorDashboard extends JFrame implements LibWindow {

    private static final long serialVersionUID = 1L;
    public static final AdminstratorDashboard INSTANCE = new AdminstratorDashboard();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;

    private JPanel addBookPanel;
    private JPanel addMemberPanel;
    private JPanel addCopyPanel;
    private JPanel mainPanel;

    JList<ListItem> linkList;
    JPanel cards;

    private String[] bookAttributes = {"Title", "ISBN", "Max days" , "Authors"};
    private String[] memeberAttributes = {"Member Number", "First Name", "Last Name", "Phone Number"};
    private String[] copyAttributes = {"Copy Number", "Book ISBN"};

    private JTextField[] bookFields = new JTextField[bookAttributes.length];
    private JTextField[] memberFields = new JTextField[memeberAttributes.length];
    private JTextField[] copyFields = new JTextField[copyAttributes.length];

    public static  String[] sideBarItems;

    // list items which will be added to the ListModel for linkList
    ListItem item1, item2 , item3;

    //Singleton class
    private AdminstratorDashboard() {

        setSize(Config.APP_WIDTH, Config.APP_WIDTH);
        sideBarItems = new String[]{"Add New Member", "Add New Book", "Add Copy"};

        // list items which will be added to the ListModel for linkList
        item1 = new ListItem(sideBarItems[0], true);
        item2 = new ListItem(sideBarItems[1], true);
        item3 = new ListItem(sideBarItems[2], true);

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

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, linkList, cards);
        splitPane.setDividerLocation(200);

        mainPanel = new JPanel(new FlowLayout());
        mainPanel.add(splitPane);

        add(mainPanel);
        isInitialized = true;


    }

    public void createMainPanels() {

        // Add member panel
        addMemberForm();

        // Add panel
        addBookForm();

        // Add copy panel
        addBookCopyForm();

        cards = new JPanel(new CardLayout());
        cards.add(addMemberPanel, item1.getItemName());
        cards.add(addBookPanel, item2.getItemName());
        cards.add(addCopyPanel, item3.getItemName());

    }

    private void addBookForm() {

        addBookPanel = new JPanel(new BorderLayout());
        JLabel panelTitle = new JLabel(" Add Book");

        panelTitle.setFont(Config.DEFUALT_FONT);

        panelTitle.setForeground(Util.LINK_AVAILABLE);

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
        JLabel panelTitle = new JLabel(" Add Book");
        panelTitle.setFont(Config.DEFUALT_FONT);
        panelTitle.setForeground(Util.LINK_AVAILABLE);

        addMemberPanel.add(panelTitle , BorderLayout.NORTH);

        JPanel memberFormPanel = createAddBookForm();

        addMemberPanel.add(memberFormPanel , BorderLayout.CENTER);

        // add add button
        JButton addBookBtn = new JButton("Add Book");
        addBookBtn.addActionListener(new addBookListiner());
        JPanel addBookBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addBookBtnPanel.add(addBookBtn);

        // add to book Panel at the bottom
        memberFormPanel.add(addBookBtnPanel, BorderLayout.SOUTH);
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

    public void addBookPanel(){

        addBookPanel = new JPanel(new BorderLayout());
        JLabel panelTitle = new JLabel(" Add Book");
        panelTitle.setFont(Config.DEFUALT_FONT);
        panelTitle.setForeground(Util.LINK_AVAILABLE);

        addBookPanel.add(panelTitle , BorderLayout.NORTH);

        JPanel bookFormPanel = createAddBookForm();

        addBookPanel.add(bookFormPanel , BorderLayout.CENTER);

        // add add button
        JButton addBookBtn = new JButton("Add Book");
        addBookBtn.addActionListener(new addBookListiner());
        JPanel addBookBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addBookBtnPanel.add(addBookBtn);

        // add to book Panel at the bottom
        addBookPanel.add(addBookBtnPanel, BorderLayout.SOUTH);
    }

    private JPanel getElementWithLabel(String labelName, JTextField[] fields, int jtextFieldIndex) {

        JLabel label = new JLabel(" " + labelName);
        fields[jtextFieldIndex] = new JTextField(20);
        JPanel nameForm = new JPanel(new BorderLayout());
        nameForm.add(label, BorderLayout.NORTH);
        nameForm.add(fields[jtextFieldIndex], BorderLayout.CENTER);

        return nameForm;

    }

    private JPanel createAddBookForm() {

        JPanel bookFormPanel = new JPanel(new GridLayout(bookAttributes.length, 0));

        for (int i = 0; i < bookFields.length; i++) {
            bookFormPanel.add(getElementWithLabel(bookAttributes[i], bookFields, i));
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
           AdminstratorDashboard.INSTANCE.setTitle(Config.APP_NAME);
            AdminstratorDashboard.INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            AdminstratorDashboard.INSTANCE.init();
            centerFrameOnDesktop(AdminstratorDashboard.INSTANCE);
            AdminstratorDashboard.INSTANCE.setVisible(true);
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

    private class addBookListiner implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String firstName = bookFields[0].getText().trim();
            String lastName = bookFields[1].getText().trim();
            String bookTitle = bookFields[2].getText().trim();

//            if(firstName.isEmpty() || lastName.isEmpty() || bookTitle.isEmpty()){
//                statusBar.setText("All fields are required");
//                statusBar.setForeground(Color.red);
//            }else{
//                Data.addBookTitle(bookTitle);
//                viewTitlesPanel.add(new JList<String>(Data.bookTitles.toArray(new String[Data.bookTitles.size()])));
//                System.out.println(Data.bookTitles.toString());
//                statusBar.setText("The book  has been added to the collection!");
//                statusBar.setForeground(Color.green);
//            }

        }
    }


}
