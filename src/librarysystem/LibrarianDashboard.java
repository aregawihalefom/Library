package librarysystem;

import business.*;
import librarysystem.guiElements.CheckOutGui;
import librarysystem.guiElements.checkOut.CheckOutBookPanel;
import librarysystem.guiElements.checkOut.ChekOutStatusPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class LibrarianDashboard extends JFrame implements LibWindow {

    private static final long serialVersionUID = 1L;

    public static final LibrarianDashboard INSTANCE = new LibrarianDashboard();
    ControllerInterface ci = new SystemController();

    private boolean isInitialized = false;

    private JPanel librarianDashobardPanel;
    private JPanel mainPanel;

    // member related panels
    private JPanel searchMemberPanel, allMemberIdsPanel;

    // book related panels
    private JPanel searBookPanel , allBookIdsPanel;

    // checkoutRelated Panels
    private JPanel checkOutBookPanel , searchMemberCheckOutPanel, checkOutStatusPanel;

    // logout panel
    private JPanel logoutPanel;


    public static final String[] LIBRARIAN_MENU = {
            "Search member",
            "Search book",
            "Checkout book",
            "Checkout status",
            "Search member checkouts",
            "All member ids",
            "All book ids",
            "Logout",
    };


    JList<ListItem> linkList;
    JPanel cards;

    private String[] copyAttributes = {"Copy Number", "Book ISBN"};
    private JTextField[] copyFields = new JTextField[copyAttributes.length];

    List<ListItem> itemList = new ArrayList<>();

    //Singleton class
    private LibrarianDashboard() {
        setSize(Config.APP_WIDTH, Config.APP_HEIGHT);
    }

    public void constructSideBarMenu()
    {
        for(String item : Config.LIBRARIAN_MENU){
            itemList.add(new ListItem(item, true));
        }

    }
    public void init() {


        // Construct sideBarMenu ListItems
        constructSideBarMenu();

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
                value = itemList.get(0).getItemName();
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
        this.setResizable(false);

    }

    public void createMainPanels() {

        // create admin panel
        addLibrarianDashBoardPanel();

        // Assign crossponding panels to crsossponding Cards
        setCards();

    }

    public void setCards(){

        // add checkout panel
        checkOutBookPanel = CheckOutBookPanel.INSTANCE.getCheckOutPanel();

        cards = new JPanel(new CardLayout());
        cards.add(librarianDashobardPanel, itemList.get(0).getItemName());
        cards.add(checkOutBookPanel, itemList.get(0).getItemName());

    }

    private void addLibrarianDashBoardPanel() {

        librarianDashobardPanel = new JPanel(new BorderLayout());
        librarianDashobardPanel.add(new JLabel("Librarian Dashboard"), BorderLayout.NORTH);
        JScrollPane memberListPanel = CheckOutGui.INSTANCE.getCheckOutList();
        librarianDashobardPanel.add(memberListPanel ,BorderLayout.CENTER);
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

        for(ListItem item : itemList){
            model.addElement(item);
        }

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


}
