/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package customization;

import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.extras.FlatSVGIcon.ColorFilter;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

/**
 *
 * @author thowie
 */
public class cSVG {

    public void menuIconSet(JLabel label, String svgFilePath) {
        // Get the size of the existing icon
        int iconWidth = label.getIcon().getIconWidth();
        int iconHeight = label.getIcon().getIconHeight();

        // Create a new FlatSVGIcon with the same size
        FlatSVGIcon svgIcon = new FlatSVGIcon(svgFilePath, iconWidth, iconHeight);

        // Set the icon of the JLabel to the SVG icon
        label.setIcon(new ImageIcon(svgIcon.getImage()));
    }

    public void btnIconSet(JButton button, String svgFilePath) {
        // Get the size of the existing icon
        int iconWidth = button.getIcon().getIconWidth();
        int iconHeight = button.getIcon().getIconHeight();

        // Create a new FlatSVGIcon with the same size
        FlatSVGIcon svgIcon = new FlatSVGIcon(svgFilePath, iconWidth, iconHeight);

        // Set the icon of the JLabel to the SVG icon
        button.setIcon(new ImageIcon(svgIcon.getImage()));
    }

    public void tabPanelIcon(JTabbedPane pane, int no, String svgFilePath) {
        // Get the size of the existing icon
        int iconWidth = pane.getIconAt(no).getIconWidth();
        int iconHeight = pane.getIconAt(no).getIconHeight();

        // Create a new FlatSVGIcon with the same size
        FlatSVGIcon svgIcon = new FlatSVGIcon(svgFilePath, iconWidth, iconHeight);

        // Set the icon of the JLabel to the SVG icon
        pane.setIconAt(no, new ImageIcon(svgIcon.getImage()));
    }

    public void frameIcon(JFrame frame, String svgFilePath) {
        FlatSVGIcon svgIcon = new FlatSVGIcon(svgFilePath);
        frame.setIconImage(svgIcon.getImage());
    }

    public void btnIconColorSet(JButton button, String svgFilePath, Color customColor) {
        // Get the size of the existing icon
        int iconWidth = button.getIcon().getIconWidth();
        int iconHeight = button.getIcon().getIconHeight();

        // Create a new FlatSVGIcon with the same size
        FlatSVGIcon svgIcon = new FlatSVGIcon(svgFilePath, iconWidth, iconHeight);

        // Set the color filter of the icon with the custom color (if it's not null)
        if (customColor != null) {
            svgIcon.setColorFilter(new ColorFilter() {
                @Override
                public Color filter(Color color) {
                    // Change the color to the custom color
                    return customColor;
                }
            });
        }

        // Set the icon of the JButton to the modified SVG icon
        button.setIcon(new ImageIcon(svgIcon.getImage()));
    }

    public void labelIconColorSet(JLabel label, String svgFilePath, Color customColor) {
        // Get the size of the existing icon
        int iconWidth = label.getIcon().getIconWidth();
        int iconHeight = label.getIcon().getIconHeight();

        // Create a new FlatSVGIcon with the same size
        FlatSVGIcon svgIcon = new FlatSVGIcon(svgFilePath, iconWidth, iconHeight);

        // Set the color filter of the icon with the custom color (if it's not null)
        if (customColor != null) {
            svgIcon.setColorFilter(new ColorFilter() {
                @Override
                public Color filter(Color color) {
                    // Change the color to the custom color
                    return customColor;
                }
            });
        }

        // Set the icon of the JButton to the modified SVG icon
        label.setIcon(new ImageIcon(svgIcon.getImage()));
    }

}
