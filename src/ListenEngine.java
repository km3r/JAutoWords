import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Kyle Rosenthal
 * 4/28/14
 */
public class ListenEngine implements KeyListener {

    private JTextField box;
    private int offset = 0;

    public ListenEngine(JTextField box) {
        this.box = box;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        //System.out.println(e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

        String str = box.getText();
        box.setText(str +"q");
        box.setSelectionColor(Color.BLACK);
        box.setSelectedTextColor(Color.WHITE);
        box.setCaretPosition(box.getCaretPosition()-1);
        box.setSelectionStart(str.length());
        box.setSelectionEnd(str.length()+1);

        /**
        if (e.getKeyChar() == 8){
            offset--;
            try {
                box.setText(box.getText(0,box.getText().length()-1));

                box.setCaretPosition(box.getCaretPosition()-offset);
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }
            return;
        }

        box.setText(box.getText() +"q");
        offset++;
        box.setCaretPosition(box.getCaretPosition()-offset);

        //*/

    }

    public String getLastWord(){
        return null;
    }
    public String getCurWord(){
        String str = box.getText();
        return str.substring(str.lastIndexOf(" "));
    }

}
