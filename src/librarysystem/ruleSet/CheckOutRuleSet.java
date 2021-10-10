package librarysystem.ruleSet;

import librarysystem.guiElements.checkOut.CheckOutBookPanel;
import librarysystem.guiElements.checkOut.OverDuePanel;

import javax.swing.*;
import java.awt.*;

public class CheckOutRuleSet implements RuleSet{

    private CheckOutBookPanel checkOutGui;

    @Override
    public void applyRules(Component ob) throws RuleException {
        checkOutGui = (CheckOutBookPanel) ob;
        nonemptyRule();
    }

    private void nonemptyRule() throws RuleException {

        for(JTextField jTextField : checkOutGui.getCheckOutFields()){
            if(jTextField.getText().isEmpty())
                throw new RuleException("All fields must be non-empty");
        }
    }
}
