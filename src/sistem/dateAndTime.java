/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JLabel;

/**
 *
 * @author thowie
 */
public class dateAndTime {

    private boolean isRunning = false;

    public void startClock(JLabel labelType, String dateOrTime) {
        isRunning = true;
        if (dateOrTime.toLowerCase().contains("date")) {
            Thread thread = new Thread(() -> {
                while (isRunning) {
                    LocalDateTime currentDateTime = LocalDateTime.now();
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMM dd, yyyy");
                    String formattedDate = currentDateTime.format(dateFormatter);
                    
                    labelType.setText(formattedDate);
                    
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            });
            thread.setDaemon(true);
            thread.start();
        } else {
            Thread thread = new Thread(() -> {
                while (isRunning) {
                    LocalDateTime currentDateTime = LocalDateTime.now();
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    String formattedTime = currentDateTime.format(timeFormatter);
                    
                    labelType.setText(formattedTime);
                    
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            });
            thread.setDaemon(true);
            thread.start();
        }
    }

    public void stopClock() {
        isRunning = false;
    }
}
