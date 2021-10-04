package librarysystem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import business.ControllerInterface;
import business.SystemController;


public class LibrarianDashboard extends JFrame implements LibWindow {

    private static final long serialVersionUID = 1L;
    public static final LibrarianDashboard INSTANCE = new LibrarianDashboard();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;
    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;
    private TextArea textArea;
    JList<ListItem> linkList;
    JPanel cards;


    public static  String[] sideBarItems;

    // list items which will be added to the ListModel for linkList
    ListItem item1, item2 ;

    //Singleton class
    private LibrarianDashboard() {

        setSize(Config.APP_WIDTH, Config.APP_WIDTH);
        sideBarItems = new String[]{"Checkout", "Print Checkout"};

        // list items which will be added to the ListModel for linkList
        item1 = new ListItem(sideBarItems[0], true);
        item2 = new ListItem(sideBarItems[1], true);

    }

    public void init() {

        // Create sidebar
        createLinkLabels();

        // create main panels
        createMainPanels();

        // True
        linkList.addListSelectionListener(event -> {
            String value = linkList.getSelectedValue().getItemName();
            boolean allowed = linkList.getSelectedValue().highlight();
            System.out.println(value + " " + allowed);

            //System.out.println("selected = " + value);
            CardLayout cl = (CardLayout) (cards.getLayout());

            if (!allowed) {
                value = item1.getItemName();
                linkList.setSelectedIndex(0);
            }
            cl.show(cards, value);
        });

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                linkList, cards);
        splitPane.setDividerLocation(100);
        add(splitPane, BorderLayout.CENTER);

        isInitialized = true;

    }

    public void createMainPanels() {
        // item1 panel

        JPanel panel1 = new JPanel();
        JLabel label = new JLabel("Checkout ");
        panel1.add(label);
        // item2 panel

        JPanel panel2 = new JPanel();
        JLabel label2 = new JLabel("print");
        panel2.add(label2);

        cards = new JPanel(new CardLayout());
        cards.add(panel1, item1.getItemName());
        cards.add(panel2, item2.getItemName());

    }

    class BackToMainListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        }
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
