package sistem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class RestartApp {

    public static void shutAndUp() {
        // Wait for 5 seconds before restarting the app
        int delay = 5000;

        Timer timer = new Timer(delay, (ActionEvent e) -> {
            // Restart the application
            restartApplication();
        });

        // Start the timer
        timer.setRepeats(false);
        timer.start();

        // Show a message dialog to inform the user that the application will be restarted
        JOptionPane.showMessageDialog(null, "The application will be restarted.");
    }

    private static void restartApplication() {
        try {
            // Get the current java command and arguments
            String java = System.getProperty("java.home") + "/bin/java";
            String[] args = System.getProperty("sun.java.command").split(" ");

            // Build the command to restart the application
            String[] command = new String[args.length + 1];
            command[0] = java;
            System.arraycopy(args, 0, command, 1, args.length);

            System.out.println("Restarting application: " + String.join(" ", command));

            // Restart the application
            Runtime.getRuntime().exec(command);
            System.exit(0);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
