package ca.cmpt213.a4;

import ca.cmpt213.a4.view.MainUI;

import javax.swing.*;

/**
 * A class that is used to start the GUI in the class MainUI.
 *
 * @author Daven Chohan
 */

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainUI();
            }
        });
    }
}
