package librarysystem;
import librarysystem.guiElements.BookGui;
import librarysystem.guiElements.MemberUI;

public enum UIController {
    INSTANCE;
    MemberUI memberGui;
    LoginScreen loginScreen;
    BookGui bookGui;
    AdministratorsDashboard admin;

}
