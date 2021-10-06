package librarysystem.guiElements;

import librarysystem.*;

public class UtilGui {

    private static LibWindow[] allWindows = {
            AdministratorsDashboard.INSTANCE,
            LoginScreen.INSTANCE,
            LibrarianDashboard.INSTANCE,
    };

    public static void hideAllWindows() {
        for(LibWindow frame: allWindows) {
            frame.setVisible(false);
        }
    }

}
