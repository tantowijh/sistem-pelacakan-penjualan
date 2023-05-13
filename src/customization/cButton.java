/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package customization;

/**
 *
 * @author thowie
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class cButton extends JButton {

    private boolean over;
    private Color customFill;
    private Color customLine;

    private Color customCurrentFill;
    private Color customHovering;
    private Color customPressedFill;
    private Color customLineOriginal;
    private Color customLineOver;
    private int customStrokeWidth;

    public cButton() {
        customCurrentFill = new Color(52, 152, 219);
        customHovering = new Color(41, 128, 185);
        customPressedFill = new Color(211, 84, 0);
        customLineOriginal = new Color(236, 240, 241);
        customLineOver = new Color(189, 195, 199);
        customStrokeWidth = 2;
        customFill = customCurrentFill;
        customLine = customLineOriginal;
        setOpaque(false);
        setBorder(null);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBackground(customCurrentFill);
        setForeground(Color.white);
        //tambahkan mouse event
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                customFill = customCurrentFill;
                customLine = customLineOriginal;
                over = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                customFill = customHovering;
                customLine = customLineOver;
                over = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (over) {
                    customFill = customHovering;
                    customLine = customLineOver;
                } else {
                    customFill = customCurrentFill;
                    customLine = customLineOriginal;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                customFill = customPressedFill;
            }

        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (contains(e.getPoint())) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else {
                    setCursor(Cursor.getDefaultCursor());
                }
            }
        });
    }

    public Color getCustomCurrentFill() {
        return customCurrentFill;
    }

    public void setCustomCurrentFill(Color customCurrentFill) {
        this.customCurrentFill = customCurrentFill;
        this.customFill = customCurrentFill;
    }

    public Color getCustomHovering() {
        return customHovering;
    }

    public void setCustomHovering(Color customHovering) {
        this.customHovering = customHovering;
    }

    public Color getCustomPressedFill() {
        return customPressedFill;
    }

    public void setCustomPressedFill(Color customPressedFill) {
        this.customPressedFill = customPressedFill;
    }

    public Color getCustomLineOriginal() {
        return customLineOriginal;
    }

    public void setCustomLineOriginal(Color customLineOriginal) {
        this.customLineOriginal = customLineOriginal;
        this.customLine = customLineOriginal;
    }

    public Color getCustomLineOver() {
        return customLineOver;
    }

    public void setCustomLineOver(Color customLineOver) {
        this.customLineOver = customLineOver;
    }

    public int getCustomStrokeWidth() {
        return customStrokeWidth;
    }

    public void setCustomStrokeWidth(int customStrokeWidth) {
        this.customStrokeWidth = customStrokeWidth;
    }

    
    
    @Override
    protected void paintComponent(Graphics g) {
        if (!isOpaque()) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int s = customStrokeWidth;
            int w = getWidth() - (2 * s);
            int h = getHeight() - (2 * s);
            //gambar background
            g2d.setColor(customFill);
            g2d.fillRoundRect(s, s, w, h, h, h);
            //gambar border
            g2d.setStroke(new BasicStroke(s));
            g2d.setColor(customLine);
            g2d.drawRoundRect(s, s, w, h, h, h);
        }
        super.paintComponent(g);
    }

}
