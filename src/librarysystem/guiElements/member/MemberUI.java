package librarysystem.guiElements.member;

import business.*;
import business.exceptions.LibraryMemberException;
import librarysystem.*;
import librarysystem.ruleSet.RuleException;
import librarysystem.ruleSet.RuleSet;
import librarysystem.ruleSet.RuleSetFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

public class MemberUI extends JPanel{

    private final String[] memeberAttributes ;
    private final JTextField[] memberFields ;
    private final ControllerInterface ci = new SystemController();
    public static MemberUI INSTANCE = new MemberUI();

    private JTable myTable;
    private JPanel addMemberPanel;

    private MemberUI() {
        memeberAttributes = new String[] {"Member ID", "First Name", "Last Name", "Phone Number", "Street", "City", "State", "Zip"};
        memberFields = new JTextField[memeberAttributes.length];
        addMemberForm();
        myTable = loadDataToTable();
        myTable.setShowGrid(true);
    }

    public JTextField[] getMemberFields() {
        return memberFields;
    }
    public JPanel getAddMemberPanel() {
        return addMemberPanel;
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
        addBMemberBtn.addActionListener(new addMemberListener());
        JPanel addBMemberBtnPanel = new JPanel(new BorderLayout());
        addBMemberBtnPanel.add(addBMemberBtn, BorderLayout.CENTER);


        // add to book Panel at the bottom
        memberFormPanel.add(addBMemberBtnPanel, BorderLayout.SOUTH);
    }

    public JTable getMemberList(){
        return myTable;
    }

    private JTable loadDataToTable(){

        String[] column ={"MEMBER NO.","FULL NAME","PHONE NO.", "Address"};
        HashMap<String , LibraryMember> memberHashMap = ci.getMembers();
        String[][] memberData = new String[memberHashMap.size()][column.length];
        List<String> memberID = ci.allMemberIds();

        for(int i = 0 ; i < memberHashMap.size(); i++){

            LibraryMember member = memberHashMap.get(memberID.get(i));
            memberData[i][0] = member.getMemberId();
            memberData[i][1] = member.getFirstName() + " " + member.getLastName();
            memberData[i][2] = member.getTelephone();
            memberData[i][3] = member.getAddress() != null ? member.getAddress().toString() : "";
        }

        DefaultTableModel model = new DefaultTableModel(memberData, column);

        return new JTable(model);

    }
    private JPanel createMemberForm() {

        JPanel memberFormPanel = new JPanel(new GridLayout(memberFields.length, 0));
        for (int i = 0; i < memberFields.length; i++) {
            memberFormPanel.add(getElementWithLabelMember(memeberAttributes[i], i));
        }
        return memberFormPanel;
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
    private class addMemberListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            try {

                // Using Factory Ruleset
                RuleSet memberRule = RuleSetFactory.getRuleSet(MemberUI.this);
                memberRule.applyRules(MemberUI.this);

                // check if member already exists
                if(ci.checkMemberId(memberFields[0].getText().trim()))
                    throw new LibraryMemberException("Member with Member Number = " + memberFields[0].getText().trim() + " already exists");

                Address add = ci.addAddress(memberFields[4].getText(), memberFields[5].getText().trim(), memberFields[6].getText().trim(), memberFields[7].getText().trim());
                LibraryMember member = ci.addLibraryMember(memberFields[0].getText(), memberFields[1].getText(),
                        memberFields[2].getText(), memberFields[3].getText(), add);

                // Add New instance
                ci.saveLibraryMember(member);
                new Messages.InnerFrame().showMessage("Member added successfully", "Info");
                addRowToJTable(member);
                clearFormFields();

            } catch (LibraryMemberException | RuleException ex) {
                new Messages.InnerFrame().showMessage(ex.getMessage(), "Error");
            }
        }
    }

    private void addRowToJTable(LibraryMember member){

        DefaultTableModel model = (DefaultTableModel) myTable.getModel();
        model.insertRow(0, new  Object[]{member.getMemberId() , member.getFirstName() +" " +member.getLastName(),
                member.getTelephone(), member.getAddress().toString()});
    }

    public void clearFormFields(){
        for(JTextField field : memberFields){
            field.setText("");
        }

    }

}
