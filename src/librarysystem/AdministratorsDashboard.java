package librarysystem;

import business.*;
import librarysystem.guiElements.BookGui;
import librarysystem.guiElements.MemberGui;

import javax.swing.*;
import java.awt.*;


public class AdministratorsDashboard extends JFrame implements LibWindow {

    private static final long serialVersionUID = 1L;
    public static  AdministratorsDashboard INSTANCE = new AdministratorsDashboard();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;

    private  JPanel addBookPanel;
    private  JPanel addMemberPanel;
    private  JPanel addCopyPanel;

    private static JScrollPane memberListPanel;
    private static JScrollPane bookListPanel;


    private  JPanel adminDashobardPanel;
    private  JPanel mainPanel;
    private static MemberGui memberGui = MemberGui.INSTANCE;

    JList<ListItem> linkList;
    JPanel cards;

    private String[] copyAttributes = {"Copy Number", "Book ISBN"};
    private JTextField[] copyFields = new JTextField[copyAttributes.length];
    public static  String[] sideBarItems;

    private JTable jTable;

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
        addMemberPanel = memberGui.getAddMemberPanel();

        // Add paaddBookForm()
        addBookPanel = BookGui.INSTANCE.getAddBookPanel();

        // Add copy panel
        addBookCopyForm();

        cards = new JPanel(new CardLayout());
        cards.add(adminDashobardPanel, item1.getItemName());
        cards.add(addMemberPanel, item2.getItemName());
        cards.add(addBookPanel, item3.getItemName());
        cards.add(addCopyPanel, item4.getItemName());

    }

    public void addAdmindDashBoardPanel() {

        adminDashobardPanel = new JPanel(new BorderLayout());
        adminDashobardPanel.add(new JLabel("Admin dashboard"), BorderLayout.NORTH);

        memberListPanel = MemberGui.INSTANCE.getMemberList();
        bookListPanel = BookGui.INSTANCE.getBookList();;

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

    private void addBookCopyForm() {

       addCopyPanel = new JPanel(new BorderLayout());

        // add add button
        JButton addBookBtn = new JButton("Add Book");
       // addBookBtn.addActionListener(new addBookListiner());
        JPanel addBookBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addBookBtnPanel.add(addBookBtn);

        // add to book Panel at the bottom
        addCopyPanel.add(addBookBtnPanel, BorderLayout.SOUTH);
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

    public static  void updateMemberList(){
        memberListPanel = MemberGui.INSTANCE.getMemberList();

    }
}
