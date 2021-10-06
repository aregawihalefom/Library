package librarysystem;
import librarysystem.guiElements.BookGui;
import librarysystem.guiElements.MemberGui;

public enum UIController {
    INSTANCE;
    MemberGui memberGui;
    LoginScreen loginScreen;
    BookGui bookGui;
    AdministratorsDashboard admin;

}
