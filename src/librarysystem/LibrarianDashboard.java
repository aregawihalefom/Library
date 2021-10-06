package librarysystem;

import business.*;
import business.exceptions.LibraryMemberException;
import librarysystem.guiElements.BookGui;
import librarysystem.guiElements.CheckOutGui;
import librarysystem.guiElements.MemberGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;


public class LibrarianDashboard extends JFrame implements LibWindow {

    private static final long serialVersionUID = 1L;
    public static final LibrarianDashboard INSTANCE = new LibrarianDashboard();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;

    private JPanel addBookPanel;
    private JPanel addMemberPanel;
    private JPanel addCopyPanel;
    private JPanel librarianDashobardPanel;
    private JPanel mainPanel;
    private JPanel checkOutPanel;

    JList<ListItem> linkList;
    JPanel cards;

    private String[] copyAttributes = {"Copy Number", "Book ISBN"};
    private JTextField[] copyFields = new JTextField[copyAttributes.length];


    public static  String[] sideBarItems;

    // list items which will be added to the ListModel for linkList
    ListItem item1, item2 , item3, item4;

    //Singleton class
    private LibrarianDashboard() {

        setSize(Config.APP_WIDTH, Config.APP_WIDTH);
        sideBarItems = new String[]{"Dashboard", "Checkout", "Logout"};

        // list items which will be added to the ListModel for linkList
        item1 = new ListItem(sideBarItems[0], true);
        item2 = new ListItem(sideBarItems[1], true);


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

        // create admin panel
        addLibrarianDashBoardPanel();

        // add checkout panel
        checkOutPanel = CheckOutGui.INSTANCE.getCheckOutPanel();

        cards = new JPanel(new CardLayout());
        cards.add(librarianDashobardPanel, item1.getItemName());
        cards.add(checkOutPanel, item2.getItemName());

    }

    private void addLibrarianDashBoardPanel() {

        librarianDashobardPanel = new JPanel(new BorderLayout());
        librarianDashobardPanel.add(new JLabel("Librarian Dashboard"), BorderLayout.NORTH);

        JScrollPane memberListPanel = CheckOutGui.INSTANCE.getCheckOutList();
        JPanel checkoutTalbe = new JPanel(new BorderLayout());
        checkoutTalbe.setPreferredSize(new Dimension(Config.APP_WIDTH - Config.DIVIDER, Config.APP_HEIGHT));
        checkoutTalbe.add(memberListPanel, BorderLayout.CENTER);

        checkoutTalbe.setFont(Config.DEFUALT_FONT);
        checkoutTalbe.setForeground(Util.LINK_AVAILABLE);
        librarianDashobardPanel.add(checkoutTalbe , BorderLayout.SOUTH);
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
            LibrarianDashboard.INSTANCE.setTitle(Config.APP_NAME);
            LibrarianDashboard.INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            LibrarianDashboard.INSTANCE.init();
            centerFrameOnDesktop(LibrarianDashboard.INSTANCE);
            LibrarianDashboard.INSTANCE.setVisible(true);
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

}
