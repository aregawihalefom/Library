package librarysystem.guiElements.member;

import business.Address;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import business.exceptions.LibraryMemberException;
import librarysystem.*;
import librarysystem.ruleSet.RuleException;
import librarysystem.ruleSet.RuleSet;
import librarysystem.ruleSet.RuleSetFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.FlowView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SearchMemberPanel extends JPanel{

    private final String[] memberAttributes ;
    private final JTextField[] memberFields ;
    private final ControllerInterface ci = new SystemController();
    public static SearchMemberPanel INSTANCE = new SearchMemberPanel();

    private JTable myTable;
    private JPanel searchMemberPanel;

    private SearchMemberPanel() {
        memberAttributes = new String[] {"Member ID:"};
        memberFields = new JTextField[memberAttributes.length];
        searchMemberForm();
        myTable = loadDataToTable();
        searchMemberPanel.add(new JScrollPane(myTable) , BorderLayout.AFTER_LAST_LINE);

    }

    public JTextField[] getMemberFields() {
        return memberFields;
    }
    public JPanel getsearchMemberPanel() {
        return searchMemberPanel;
    }

    private void searchMemberForm() {

        searchMemberPanel = new JPanel(new BorderLayout());

        JLabel panelTitle = new JLabel(" Search Library Member By ID ");
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.add(new JSeparator(JSeparator.HORIZONTAL));
        titlePanel.add(panelTitle);
        panelTitle.setFont(Config.DEFUALT_FONT);
        panelTitle.setForeground(Util.DARK_BLUE);
        searchMemberPanel.add(titlePanel , BorderLayout.NORTH);


        JPanel memberFormPanel = createMemberForm();

        // add add button
        JButton addBMemberBtn = new JButton("Search");
        addBMemberBtn.setBackground(Color.LIGHT_GRAY);
        addBMemberBtn.addActionListener(new searchMemberListener());

        // add to book Panel at the bottom
        memberFormPanel.add(addBMemberBtn);
        searchMemberPanel.add(memberFormPanel , BorderLayout.CENTER);

    }

    public JTable getMemberList(){
        return myTable;
    }

    private JTable loadDataToTable(){

        String[] column ={"MEMBER NO.","FULL NAME","PHONE NO.", "Address"};
        DefaultTableModel model = new DefaultTableModel(null, column);

        return new JTable(model);

//        {
//            @Override
//            public Dimension getPreferredScrollableViewportSize() {
//            return new Dimension(super.getPreferredSize().width,
//                    getRowHeight() * getRowCount());
//        }
//        }

    }

    private void addRowToJTable(LibraryMember libraryMember){

        DefaultTableModel model = (DefaultTableModel) myTable.getModel();

        if(model.getRowCount() > 0)
            model.setRowCount(0);
        model.addRow(new  Object[]{ libraryMember.getMemberId(), libraryMember.getFirstName() + " " + libraryMember.getLastName(),
                libraryMember.getTelephone() , libraryMember.getAddress().toString()});

    }

    private JPanel createMemberForm() {

        JPanel memberFormPanel = new JPanel();
        for (int i = 0; i < memberFields.length; i++) {
            memberFormPanel.add(getElementWithLabelMember(memberAttributes[i], i));
        }
        return memberFormPanel;
    }

    private JPanel getElementWithLabelMember(String labelName, int jtextFieldIndex) {

        JLabel label = new JLabel(" " + labelName);
        memberFields[jtextFieldIndex] = new JTextField(20);


        JPanel nameForm = new JPanel();
        nameForm.add(label);
        nameForm.add(memberFields[jtextFieldIndex]);


        return nameForm;
    }
    private class searchMemberListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            try {

                // Using Factory Ruleset
                RuleSet memberRule = RuleSetFactory.getRuleSet(SearchMemberPanel.this);
                memberRule.applyRules(SearchMemberPanel.this);

                // check if member already exists
                if(!ci.checkMemberId(memberFields[0].getText().trim()))
                    throw new LibraryMemberException("Member with Member ID = " + memberFields[0].getText().trim() + "not found");


                LibraryMember member = ci.getMembers().get(memberFields[0].getText().trim());

                if(member == null)
                    throw new LibraryMemberException("Unable to load member details");
                addRowToJTable(member);
                new Messages.InnerFrame().showMessage("1 Results found ", "Info");
                clearFormFields();

            } catch (LibraryMemberException | RuleException ex) {
                new Messages.InnerFrame().showMessage(ex.getMessage(), "Error");
            }
        }
    }

    public void clearFormFields(){
        for(JTextField field : memberFields){
            field.setText("");
        }

    }

}
