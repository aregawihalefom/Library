package librarysystem.guiElements;

import librarysystem.*;
import librarysystem.LibrarianDashboard;

import java.awt.*;

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

    public static final Dimension BTN_DIMENSION = new Dimension(150, 30);
    public static final Dimension PANEL_DIMENSION =new Dimension(Config.APP_WIDTH/2 + 100 , Config.APP_HEIGHT/2);

}
