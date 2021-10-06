package librarysystem.ruleSet;

import librarysystem.LoginScreen;
import librarysystem.guiElements.CheckOutGui;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class CheckOutRuleSet implements RuleSet{

    private CheckOutGui checkOutGui;

    @Override
    public void applyRules(Component ob) throws RuleException {
        checkOutGui = (CheckOutGui) ob;
        nonemptyRule();
    }

    private void nonemptyRule() throws RuleException {
        if(checkOutGui.getCheckOutFields()[0].getText().isEmpty() || checkOutGui.getCheckOutFields()[0].getText().isEmpty())
            throw new RuleException("All fields must be non-empty");
    }
}
