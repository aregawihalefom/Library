package librarysystem.ruleSet;

import librarysystem.LoginScreen;
import librarysystem.guiElements.BookGui;
import librarysystem.guiElements.CheckOutGui;
import librarysystem.guiElements.MemberUI;

import java.awt.*;
import java.util.HashMap;


final public class RuleSetFactory {
	private RuleSetFactory(){}
	static HashMap<Class<? extends Component>, RuleSet> map = new HashMap<>();
	static {
		map.put(BookGui.class, new BookRuleSet());
		map.put(MemberUI.class, new MemberRuleSet());
		map.put(LoginScreen.class, new LoginRuleSet());
		map.put(CheckOutGui.class, new CheckOutRuleSet());
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
