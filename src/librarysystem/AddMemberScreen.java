package librarysystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.*;

public class AddMemberScreen extends JFrame implements LibWindow {
    public static final AddMemberScreen INSTANCE = new AddMemberScreen();

    JPanel staffPanel;
    JPanel mainPanel;
    JPanel jPAddMember;
    JPanel jPViewDetail, jPViewDetail1, jPViewDetail2;
    JButton jBAddCustomer;
    JButton jBDeposit;
    JButton jBLogout;
    JButton jBSave;
    JButton jBBack,jBBack1,jBBack2;
    JButton jBDetail, jBView,jBQuery;

    JComboBox jCBAccountType;
    JLabel jLAccType;
    JLabel jLTitle,jLInterestD,jLOverdraftD,jLOverdraftV,jLInterestV,jLAccTypeD, jLAccTypeV, jLBalanceD, jLBalanceV, jLHead, jLNameD, jLAddressD, jLAddressV, jLEmailD, jLEmailV, jLDateV, jLDateD, jLPhoneD, jLPhoneV, jLDobD, jLDobV, jLUsernameD, jLUsernameV, jLPasswordD, jLPasswordV;
    JLabel jLDetail, jLAdd, jLName, jLAddress, jLPhone, jLEmail, jLDob, jLUsername, jLPassword, jLAccount, jLAccNo, jLNameV, jLAmount;
    JTextField jTFName, jTFAddress, jTFPhone, jTFEmail, jTFDob, jTFUsername, jTFPassword, jTFAccount, jTFAccNo, jTFDetail, jTFAmount;


    private boolean isInitialized = false;


    @Override
    public void init() {

        //creating frame
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(false);
        this.setLayout(null);
        this.setSize(600, 500);
        this.setResizable(false); // cannot be minimized
        this.setLocationRelativeTo(null);
        addCustomer();
        getContentPane().add(mainPanel);

    }
    public boolean isInitialized() {
        return isInitialized;
    }
    public void isInitialized(boolean val) {
        isInitialized = val;
    }

    private AddMemberScreen() {
    }

    private void staffGUI() {

        //creating objects of swing clases
        mainPanel = new JPanel();
        jPAddMember = new JPanel();
        jLHead = new JLabel();
        staffPanel = new JPanel();
        jBView = new JButton();
        jBLogout = new JButton();
        jBAddCustomer = new JButton();
        jBDeposit = new JButton();
        jBQuery = new JButton();

        //main panel
        mainPanel.setBackground(new Color(204,204,255));
        mainPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        mainPanel.setLayout(null);

        jPAddMember.setBackground(new Color(204,204,204));
        jPAddMember.setBorder(new   SoftBevelBorder(  BevelBorder.RAISED));
        jPAddMember.setLayout(null);

        //heading
        jLHead.setFont(new Font("Tahoma", 1, 18));
        jLHead.setText("  Bank Of Northampton: Staff Dashboard");
        mainPanel.add(jLHead);
        jLHead.setBounds(100, 10, 390, 40);

        staffPanel.setBackground(new Color(204,204,204));
        staffPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        staffPanel.setLayout(null);

        //buttons
        jBView.setFont(new Font("Tahoma", 1, 14));
        jBView.setText("View Detail");
        jBView.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        staffPanel.add(jBView);
        jBView.setBounds(12, 43, 214, 57);

        jBLogout.setText("Logout");
        jBLogout.setFont(new Font("Tahoma", 1, 14));
        jBLogout.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        staffPanel.add(jBLogout);
        jBLogout.setBounds(130, 240, 170, 50);



        jBQuery.setFont(new Font("Tahoma", 1, 14));
        jBQuery.setText("Query Account");
        jBQuery.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        staffPanel.add(jBQuery);
        jBQuery.setBounds(251, 147, 197, 57);

        jBAddCustomer.setFont(new Font("Tahoma", 1, 14));
        jBAddCustomer.setText("Add Customer");
        jBAddCustomer.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        staffPanel.add(jBAddCustomer);
        jBAddCustomer.setBounds(251, 43, 197, 57);

        jBDeposit.setFont(new Font("Tahoma", 1, 14));
        jBDeposit.setText("Deposit/Withdraw");
        jBDeposit.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        staffPanel.add(jBDeposit);
        jBDeposit.setBounds(12, 147, 214, 57);

        mainPanel.add(staffPanel);
        staffPanel.setBounds(60, 50, 460, 330);
        mainPanel.setBounds(0, 0, 600, 500);

        addCustomer();
        mainPanel.add(jPAddMember);
        jPAddMember.setVisible(false);

    }

    void addCustomer() {

        mainPanel = new JPanel();
        jPAddMember = new JPanel();

        //main panel
        mainPanel.setBackground(new Color(204,204,255));
        mainPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        mainPanel.setLayout(null);

        jPAddMember.setBackground(new Color(204,204,204));
        jPAddMember.setBorder(new   SoftBevelBorder(  BevelBorder.RAISED));
        jPAddMember.setLayout(null);

        //creating objects of swing classes
        jLHead = new JLabel();
        jTFName = new JTextField();
        jLName = new JLabel();
        jLAddress = new JLabel();
        jTFAddress = new JTextField();
        jLPhone = new JLabel();
        jTFPhone = new JTextField();
        jLEmail = new JLabel();
        jTFEmail = new JTextField();
        jLDob = new JLabel();
        jTFDob = new JTextField();
        jLUsername = new JLabel();
        jTFUsername = new JTextField();
        jLPassword = new JLabel();
        jTFPassword = new JTextField();
        jCBAccountType = new JComboBox();
        jLAccType = new JLabel();
        jTFAccNo = new JTextField();
        jLAmount = new JLabel();
        jTFAmount = new JTextField();
        jLAccNo = new JLabel();
        jBBack = new JButton();
        jBBack2= new JButton();
        jBSave = new JButton();

        //initializing font,text,bound for the objects
        jTFName.setFont(new Font("Tahoma", 0, 12));

        jPAddMember.add(jTFName);
        jTFName.setBounds(140, 10, 230, 30);

        jLName.setFont(new Font("Tahoma", 1, 14));
        jLName.setText("Name:");
        jPAddMember.add(jLName);
        jLName.setBounds(80, 10, 70, 20);

        jLAddress.setFont(new Font("Tahoma", 1, 14));
        jLAddress.setText("Address:");
        jPAddMember.add(jLAddress);
        jLAddress.setBounds(60, 50, 70, 20);

        jTFAddress.setFont(new Font("Tahoma", 0, 12));

        jPAddMember.add(jTFAddress);
        jTFAddress.setBounds(140, 50, 230, 30);

        jLPhone.setFont(new Font("Tahoma", 1, 14));
        jLPhone.setText("Phone:");
        jPAddMember.add(jLPhone);
        jLPhone.setBounds(70, 90, 70, 20);

        jTFPhone.setFont(new Font("Tahoma", 0, 12));

        jPAddMember.add(jTFPhone);
        jTFPhone.setBounds(140, 90, 230, 30);

        jLEmail.setFont(new Font("Tahoma", 1, 14));
        jLEmail.setText("Email:");
        jPAddMember.add(jLEmail);
        jLEmail.setBounds(80, 130, 70, 20);

        jTFEmail.setFont(new Font("Tahoma", 0, 12));

        jPAddMember.add(jTFEmail);
        jTFEmail.setBounds(140, 130, 230, 30);

        jLDob.setFont(new Font("Tahoma", 1, 14));
        jLDob.setText("Date of Birth:");
        jPAddMember.add(jLDob);
        jLDob.setBounds(30, 170, 100, 20);

        jTFDob.setFont(new Font("Tahoma", 0, 12));

        jPAddMember.add(jTFDob);
        jTFDob.setBounds(140, 170, 230, 30);

        jLUsername.setFont(new Font("Tahoma", 1, 14));
        jLUsername.setText("Username:");
        jPAddMember.add(jLUsername);
        jLUsername.setBounds(50, 210, 100, 20);

        jTFUsername.setFont(new Font("Tahoma", 0, 12));

        jPAddMember.add(jTFUsername);
        jTFUsername.setBounds(140, 210, 230, 30);

        jLPassword.setFont(new Font("Tahoma", 1, 14));
        jLPassword.setText("Password:");
        jPAddMember.add(jLPassword);
        jLPassword.setBounds(50, 250, 72, 20);

        jTFPassword.setFont(new Font("Tahoma", 0, 12));

        jPAddMember.add(jTFPassword);
        jTFPassword.setBounds(140, 250, 230, 30);

        jCBAccountType.setModel(new DefaultComboBoxModel(new String[]{"Current", "Deposit", "ISA"}));

        jPAddMember.add(jCBAccountType);
        jCBAccountType.setBounds(140, 290, 100, 30);

        jLAccType.setFont(new Font("Tahoma", 1, 14));
        jLAccType.setText("Account Type:");
        jPAddMember.add(jLAccType);
        jLAccType.setBounds(30, 290, 110, 20);

        jPAddMember.add(jTFAccNo);
        jTFAccNo.setBounds(140, 320, 230, 30);

        jLAmount.setFont(new Font("Tahoma", 1, 14));
        jLAmount.setText("Initial Amount:");
        jPAddMember.add(jLAmount);
        jLAmount.setBounds(240, 290, 120, 30);

        jPAddMember.add(jTFAmount);
        jTFAmount.setBounds(350, 290, 110, 30);
        jTFAmount.setText("0");

        jLAccNo.setFont(new Font("Tahoma", 1, 14));
        jLAccNo.setText("Account Number:");
        jPAddMember.add(jLAccNo);
        jLAccNo.setBounds(10, 320, 130, 20);

        jBBack.setText("Back");

        jPAddMember.add(jBBack);
        jBBack.setBounds(260, 350, 100, 30);

        jBSave.setText("Save");

        jPAddMember.add(jBSave);
        jBSave.setBounds(150, 350, 100, 30);

        // mainPanel.add(jPAddCustomer);
        jPAddMember.setBounds(60, 40, 470, 390);

        mainPanel.add(jPAddMember);
        //action listener for back button which takes user to the staff dashbaord
        jBBack.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                jPAddMember.setVisible(false);
                jPViewDetail1.setVisible(false);

                staffPanel.repaint();
                jPAddMember.repaint();

                staffPanel.setVisible(true);
            }
        });
    }



}
