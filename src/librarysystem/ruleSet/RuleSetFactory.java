package librarysystem.ruleSet;

import librarysystem.LoginScreen;
import librarysystem.guiElements.book.AddBookCopyPanel;
import librarysystem.guiElements.book.BookGui;
import librarysystem.guiElements.member.EditOrDeleteMember;
import librarysystem.guiElements.member.MemberUI;
import librarysystem.guiElements.book.SearchBookPanel;
import librarysystem.guiElements.checkOut.CheckOutBookPanel;
import librarysystem.guiElements.checkOut.OverDuePanel;
import librarysystem.guiElements.checkOut.PrintMemberCheckOut;
import librarysystem.guiElements.member.SearchMemberPanel;

import java.awt.*;
import java.util.HashMap;


final public class RuleSetFactory {
	private RuleSetFactory(){}
	static HashMap<Class<? extends Component>, RuleSet> map = new HashMap<>();
	static {
		map.put(BookGui.class, new BookRuleSet());
		map.put(MemberUI.class, new MemberRuleSet());
		map.put(LoginScreen.class, new LoginRuleSet());
		map.put(CheckOutBookPanel.class, new CheckOutRuleSet());
		map.put(SearchMemberPanel.class, new SearchMemberRuleSet());
		map.put(SearchBookPanel.class, new SearchBookRuleSet());
		map.put(OverDuePanel.class, new OverDueRuleSet());
		map.put(PrintMemberCheckOut.class, new PrintMemberCheckOutRuleSet());
		map.put(AddBookCopyPanel.class, new BookCopyRuleSet());
		map.put(EditOrDeleteMember.class,  new EditOrDeleteMemberRuleSet());
	}
	public static RuleSet getRuleSet(Component c) {
		Class<? extends Component> cl = c.getClass();
		if(!map.containsKey(cl)) {
			throw new IllegalArgumentException(
					"No RuleSet found for this Component");
		}
		return map.get(cl);
	}
}
