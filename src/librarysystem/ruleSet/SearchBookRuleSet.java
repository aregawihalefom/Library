package librarysystem.ruleSet;

import librarysystem.LoginScreen;
import librarysystem.guiElements.book.SearchBookPanel;
import librarysystem.guiElements.member.SearchMemberPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class SearchBookRuleSet implements RuleSet{

    private SearchBookPanel searchBookPanel;

    @Override
    public void applyRules(Component ob) throws RuleException {
        searchBookPanel = (SearchBookPanel) ob;
        nonemptyRule();
    }

    private void nonemptyRule() throws RuleException {
        if(searchBookPanel.getBookFields()[0].getText().trim().isEmpty())
            throw new RuleException("Search field should be non-empty");
    }
}
