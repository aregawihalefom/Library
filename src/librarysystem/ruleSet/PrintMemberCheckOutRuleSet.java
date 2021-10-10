package librarysystem.ruleSet;

import librarysystem.guiElements.checkOut.OverDuePanel;
import librarysystem.guiElements.checkOut.PrintMemberCheckOut;

import java.awt.*;

public class PrintMemberCheckOutRuleSet implements RuleSet{

    private PrintMemberCheckOut printMemberCheckOut;

    @Override
    public void applyRules(Component ob) throws RuleException {
        printMemberCheckOut = (PrintMemberCheckOut) ob;
        nonemptyRule();
    }

    private void nonemptyRule() throws RuleException {
        if(printMemberCheckOut.getprintMemberFields()[0].getText().trim().isEmpty())
            throw new RuleException("Search field should be non-empty");
    }
}
