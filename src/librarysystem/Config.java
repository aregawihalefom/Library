package librarysystem;

import java.awt.*;

public class Config {

    public static final String APP_NAME = "OUR LIBRARY";
    public static final int APP_WIDTH = 900;
    public static final int APP_HEIGHT = 600;
    public static final int DIVIDER = 200;
    public static  final Font DEFUALT_FONT = new java.awt.Font("Tahoma", 1, 15);


    public static final String[] ALL_MENU = {
            "Add member",
            "Add book",
            "Add book copy",
            "Search member",
            "All book ids",
            "All member ids",
            "Checkout book",
            "Checkout status",
            "Search book",
            "Search member checkouts",
            "Update/Delete member",
            "Logout",
    };
    public static final String[] LIBRARIAN_MENU = {
            "Search member",
            "All book ids",
            "All member ids",
            "Checkout book",
            "Checkout status",
            "Search book",
            "Search member checkouts",
            "Logout",
    };

    public static final String[] ADMIN_MENU = {
            "Add member",
            "Add book",
            "Add book copy",
            "Search member",
            "All member ids",
            "Search book",
            "Update/Delete member",
            "Logout",
    };



}
