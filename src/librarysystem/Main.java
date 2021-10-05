package librarysystem;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;


public class Main {

	public static void main(String[] args) {
	      EventQueue.invokeLater(() -> 
	         {
	            LoginScreen.INSTANCE.setTitle(Config.APP_NAME);
				LoginScreen.INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				 LoginScreen.INSTANCE.init();
	            centerFrameOnDesktop(LoginScreen.INSTANCE);
				 LoginScreen.INSTANCE.setVisible(true);
	         });
	   }
	   public static void centerFrameOnDesktop(Component f) {
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			int height = toolkit.getScreenSize().height;
			int width = toolkit.getScreenSize().width;
			int frameHeight = f.getSize().height;
			int frameWidth = f.getSize().width;
			f.setLocation(((width - frameWidth) / 2), (height - frameHeight) / 3);
		}
}
