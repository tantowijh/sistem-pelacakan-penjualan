/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistem;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.JPanel;

/**
 *
 * @author thowie
 */
public class panelLoader {

    public void loadPanel(JPanel mainPanel, Component panelToLoad) {
        mainPanel.removeAll();  // Menghapus semua komponen dari panel utama

        mainPanel.setLayout(new BorderLayout());  // Mengatur tata letak panel utama

        mainPanel.add(panelToLoad, BorderLayout.CENTER);  // Menambahkan panel yang akan dimuat ke panel utama

        mainPanel.repaint();  // Menggambar ulang panel utama
        mainPanel.revalidate();  // Memvalidasi ulang panel utama
    }
}
