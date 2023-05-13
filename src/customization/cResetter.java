/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package customization;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author thowie
 */
public class cResetter {

    // method untuk mengosongkan text field
    public void resetFill(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText(null);
        }
    }

    // method untuk membuat text Capitalized
    public String setCapitalized(String str) {
        String capitalizedStr = str.substring(0, 1).toUpperCase() + str.substring(1);
        return capitalizedStr;
    }

    // method untuk mencegah spasi pada text field
    public void setUsername(JTextField[] fields) {
        for (JTextField field : fields) {
            field.setDocument(new UsernameDocument());
        }
    }

    // pengaturan untuk method setUsername
    class UsernameDocument extends PlainDocument {

        @Override
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null) {
                return;
            }

            char[] charArray = str.toCharArray();
            boolean containsSpace = false;

            for (char c : charArray) {
                if (Character.isWhitespace(c)) {
                    containsSpace = true;
                    break;
                }
            }

            if (!containsSpace) {
                super.insertString(offset, str, attr);
            }
        }
    }

    public void setPhoneAddress(JTextField[] fields) {
        for (JTextField field : fields) {
            field.setDocument(new PhoneAddressDocument());
        }
    }

    class PhoneAddressDocument extends PlainDocument {

        @Override
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null) {
                return;
            }

            try {
                Double.valueOf(str);
            } catch (NumberFormatException e) {
                return;
            }

            super.insertString(offset, str, attr);
        }
    }

    public void setQuantity(JTextField field) {
        field.setDocument(new cNumberInteger());
    }

    // Membuat document hanya MemuatAngka Integer
    class cNumberInteger extends PlainDocument {

        @Override
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null) {
                return;
            }

            try {
                Double.valueOf(str);
            } catch (NumberFormatException e) {
                return;
            }

            super.insertString(offset, str, attr);
        }
    }

    public void setBlockedButton(JButton[] buttons) {
        // Set the permission settings
        if (!koneksi.loginSession.getRole().equals("owner") && !koneksi.loginSession.getRole().equals("admin")) {
            for (var button : buttons) {
                button.setEnabled(false);
                button.setToolTipText("Anda tidak mempunyai akses untuk " + button.getText() + "!");
            }
        }
    }

}
