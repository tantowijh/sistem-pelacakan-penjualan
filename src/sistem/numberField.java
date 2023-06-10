/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistem;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author thowie
 */
public class numberField {

    // Membuat document hanya MemuatAngka Integer
    class cNumberInteger extends PlainDocument {

        @Override
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null) {
                return;
            }

            // check if the string contains only digits
            if (!str.matches("\\d+")) {
                return;
            }

            super.insertString(offset, str, attr);
        }
    }

    // Membuat document hanya MemuatAngka Double
    class cNumberDouble extends PlainDocument {

        @Override
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null) {
                return;
            }

            if (str.equals(".")) {
                super.insertString(offset, ".", attr);
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

    // Memuat textField untuk diformat number
    public void intNumber(JTextField[] fields) {
        for (JTextField field : fields) {
            field.setDocument(new cNumberInteger());
        }
    }

    // Memuat textField untuk diformat double
    public void doubleNumber(JTextField[] fields) {
        for (JTextField field : fields) {
            field.setDocument(new cNumberDouble());
        }
    }

}
