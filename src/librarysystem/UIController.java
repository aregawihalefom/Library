package librarysystem;
import librarysystem.guiElements.BookGui;
import librarysystem.guiElements.MemberUI;

public enum UIController {
    INSTANCE;
    public MemberUI memberGui;
    public LoginScreen loginScreen;
    public BookGui bookGui;
    public AdministratorsDashboard admin;

}
