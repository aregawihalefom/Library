package librarysystem.ruleSet;

import librarysystem.guiElements.book.SearchBookPanel;
import librarysystem.guiElements.checkOut.OverDuePanel;

import java.awt.*;

public class OverDueRuleSet implements RuleSet{

    private OverDuePanel overDuePanel;

    @Override
    public void applyRules(Component ob) throws RuleException {
        overDuePanel = (OverDuePanel) ob;
        nonemptyRule();
    }

    private void nonemptyRule() throws RuleException {
        if(overDuePanel.getOverDueFields()[0].getText().trim().isEmpty())
            throw new RuleException("Search field should be non-empty");
    }
}
