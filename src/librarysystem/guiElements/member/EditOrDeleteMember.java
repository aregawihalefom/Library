package librarysystem.guiElements.member;

import business.*;
import business.exceptions.LibraryMemberException;
import librarysystem.*;
import librarysystem.ruleSet.EditOrDeleteMemberRuleSet;
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

public class EditOrDeleteMember extends JPanel{

    private final String[] memeberAttributes ;
    private final JTextField[] memberFields ;

    private final ControllerInterface ci = new SystemController();
    public static EditOrDeleteMember INSTANCE = new EditOrDeleteMember();


    private EditOrDeleteMember() {
        memeberAttributes = new String[] {"Member ID", "First Name", "Last Name", "Phone Number", "Street", "City", "State", "Zip"};
        memberFields = new JTextField[memeberAttributes.length];
        addMemberForm();
    }

    public JTextField[] getMemberFields() {
        return memberFields;
    }
    public JPanel getAddMemberPanel() {
        return this;
    }

    private void addMemberForm() {

        JLabel panelTitle = new JLabel("Edit or Delete Member");
        panelTitle.setFont(Config.DEFUALT_FONT);
        panelTitle.setForeground(Util.DARK_BLUE);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.NORTH);
        titlePanel.add(panelTitle, BorderLayout.CENTER);
        titlePanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.SOUTH);

        // Middle form
        JPanel memberFormPanel = createMemberForm();

        JButton editMemberBtn = new JButton("Edit");
        editMemberBtn.addActionListener(new EditMember());

        JButton searchMemberBtn = new JButton("Search");
        searchMemberBtn.addActionListener(new SearchMember());

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setForeground(Color.red);
        deleteBtn.addActionListener(new DeleteMember());

        JPanel btnSPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnSPanel.add(searchMemberBtn);
        btnSPanel.add(editMemberBtn);
        btnSPanel.add(deleteBtn);


        // top panel
        JPanel combine = new JPanel(new BorderLayout());
        combine.add(titlePanel, BorderLayout.NORTH);
        combine.add(memberFormPanel, BorderLayout.CENTER);
        combine.add(btnSPanel, BorderLayout.SOUTH);

        this.add(combine);
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
        labelPanel.add(label, BorderLayout.CENTER);

        memberFields[jtextFieldIndex] = new JTextField(20);
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.add(memberFields[jtextFieldIndex], BorderLayout.CENTER);

        JPanel nameForm = new JPanel(new BorderLayout());
        nameForm.add(labelPanel, BorderLayout.WEST);
        nameForm.add(formPanel, BorderLayout.EAST);

        return nameForm;
    }


    public void clearFormFields(){
        for(JTextField field : memberFields){
            field.setText("");
        }

    }

    private class EditMember implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            RuleSet searchMemberRuleSet = RuleSetFactory.getRuleSet(EditOrDeleteMember.this);
            try {
                searchMemberRuleSet.applyRules(EditOrDeleteMember.this);

                if(!ci.getMembers().containsKey(memberFields[0].getText().trim()))
                    throw new RuleException();


                Address add = ci.addAddress(memberFields[4].getText(), memberFields[5].getText().trim(), memberFields[6].getText().trim(), memberFields[7].getText().trim());
                LibraryMember member = ci.addLibraryMember(memberFields[0].getText(), memberFields[1].getText(),
                        memberFields[2].getText(), memberFields[3].getText(), add);

                // Add New instance
                ci.saveLibraryMember(member);

                new Messages.InnerFrame().showMessage("Member Info updated successfully", "Info");

            } catch (RuleException | LibraryMemberException ex) {
                new Messages.InnerFrame().showMessage(ex.getMessage(), "Error");
            }

        }
    }

    private class DeleteMember implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {

               if(memberFields[0].getText().trim().isEmpty())
                  throw new RuleException("Enter Member ID and search for Member before Deleting/Editing");
                if(!ci.getMembers().containsKey(memberFields[0].getText().trim())){
                    new Messages.InnerFrame().showMessage("No member found with ID = "+ memberFields[0].getText().trim(), "Error");
                }else{

                    LibraryMember member = ci.getMembers().get(memberFields[0].getText().trim());
                    memberFields[1].setText(member.getFirstName());
                    memberFields[2].setText(member.getLastName());
                    memberFields[3].setText(member.getTelephone());
                    memberFields[4].setText(member.getAddress().getStreet());
                    memberFields[5].setText(member.getAddress().getCity());
                    memberFields[6].setText(member.getAddress().getState());
                    memberFields[7].setText(member.getAddress().getZip());


                    String id = memberFields[0].getText().trim();
                    ci.deleteMember(member);

                    removeRowFromTable(id);

                    clearFormFields();
                    throw new RuleException(" Member with  ID = " + id + " deleted");
                }


                } catch (RuleException ex) {
                    new Messages.InnerFrame().showMessage(ex.getMessage(), "Error");
                }
        }

        private void removeRowFromTable(String id) {

            DefaultTableModel model = (DefaultTableModel) UIController.INSTANCE.admin.memberListJTable.getModel();

            for (int i = 0; i < model.getRowCount(); i++) {
                if (((String)model.getValueAt(i, 0)).equals(id)) {
                    model.removeRow(i);
                }
            }
        }
    }

    private class SearchMember implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            try {

                if(memberFields[0].getText().trim().isEmpty())
                    throw new RuleException("Enter Member ID and search for Member before Deleting/Editing");

                    if(!ci.getMembers().containsKey(memberFields[0].getText().trim())){
                    new Messages.InnerFrame().showMessage("No member found with ID = "+ memberFields[0].getText().trim(), "Error");
                }else{

                    LibraryMember member = ci.getMembers().get(memberFields[0].getText().trim());
                    memberFields[1].setText(member.getFirstName());
                    memberFields[2].setText(member.getLastName());
                    memberFields[3].setText(member.getTelephone());
                    memberFields[4].setText(member.getAddress().getStreet());
                    memberFields[5].setText(member.getAddress().getCity());
                    memberFields[6].setText(member.getAddress().getState());
                    memberFields[7].setText(member.getAddress().getZip());

                }
                } catch (RuleException ex) {
                    new Messages.InnerFrame().showMessage(ex.getMessage(), "Error");
                }

        }
    }
}
