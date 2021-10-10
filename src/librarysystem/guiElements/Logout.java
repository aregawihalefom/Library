package librarysystem.guiElements;

import jdk.jshell.execution.Util;
import librarysystem.UIController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Logout {

    public static Logout INSTANCE = new Logout();
    private JPanel logoutPanel  = new JPanel();

    private Logout() {

        JButton logoutBtn = new JButton("Log out");

        logoutBtn.setPreferredSize(new Dimension(20, 30));
        logoutBtn.addActionListener(new LogoutListener());
        JLabel prompt  = new JLabel("Are you sure you want to logout ?");
        JPanel btnPanel = new JPanel(new BorderLayout());
        btnPanel.add(logoutBtn, BorderLayout.NORTH);

        logoutPanel = new JPanel(new BorderLayout());
        logoutPanel.add(prompt , BorderLayout.NORTH);
        logoutPanel.add(new JSeparator(JSeparator.HORIZONTAL));
        logoutPanel.add(btnPanel, BorderLayout.CENTER);
    }

    public JPanel getLoginPanel(){return logoutPanel;};

    private class LogoutListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
          UtilGui.hideAllWindows();
            UIController.INSTANCE.loginScreen.setVisible(true);
        }
    }
}
