package librarysystem.ruleSet;

import librarysystem.LoginScreen;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class LoginRuleSet implements RuleSet{

    private LoginScreen loginScreen;

    @Override
    public void applyRules(Component ob) throws RuleException {
        loginScreen = (LoginScreen) ob;
        nonemptyRule();
    }

    private void nonemptyRule() throws RuleException {
        if(loginScreen.getjTFPassword().getPassword().length ==0 || loginScreen.getjTFUserName().getText().trim().isEmpty())
            throw new RuleException("All fields must be non-empty");
    }
}
