package librarysystem.ruleSet;

import librarysystem.LoginScreen;
import librarysystem.guiElements.member.SearchMemberPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class SearchMemberRuleSet implements RuleSet{

    private SearchMemberPanel searchMemberPanel;

    @Override
    public void applyRules(Component ob) throws RuleException {
        searchMemberPanel = (SearchMemberPanel) ob;
        nonemptyRule();
    }

    private void nonemptyRule() throws RuleException {
        if(searchMemberPanel.getMemberFields()[0].getText().trim().isEmpty())
            throw new RuleException("Search field should be non-empty");
    }
}
