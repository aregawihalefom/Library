package librarysystem.ruleSet;

import business.exceptions.LibraryMemberException;

import java.awt.*;

public interface RuleSet {
	public void applyRules(Component ob) throws RuleException;
}
