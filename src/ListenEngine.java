
import javax.swing.*;

import java.awt.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

/**
 * Kyle Rosenthal
 * 4/28/14
 */
public class ListenEngine implements KeyListener {

    AutoComTree act;

    private JTextField box;

    public ListenEngine(JTextField box) {
        this.box = box;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    String currSel = "";

    @Override
    public void keyReleased(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE &&
                box.getText().length() < 1) {
            return;
        }

        if (e.getKeyChar() == 8 && currSel.length() > 0){
            box.setText(box.getText().substring(0,box.getText().length() -1));
        }
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            finishWord(" ");
            return;
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            currSel = "";
            finishWord("");
            return;
        }
        if ((e.getKeyChar()+ "").toLowerCase().equals((e.getKeyChar()+ "").toUpperCase())){
            return;
        }
        String str = box.getText();
        String high = act.getRestWord(getCurWord());
        currSel = "";
        if (high.length() > 0) {
            currSel = high;
        }
        box.setText(str + high);
        box.setSelectionColor(Color.BLACK);
        box.setSelectedTextColor(Color.WHITE);
        box.setCaretPosition(box.getCaretPosition()-high.length());
        box.setSelectionStart(str.length());
        box.setSelectionEnd(str.length()+high.length());

    }

    private void finishWord(String s) {


        String str =box.getText().substring(0,box.getText().length()-currSel.length()) + currSel + s;
        box.setText(str);
        currSel ="";

        act.addWord(getLastWord());


        //TODO:next word prediction
    }


    public String getLastWord(){
        String[] arr = box.getText().split(" ");
        if (arr.length < 1) return "";
        if (arr.length > 0 && box.getText().endsWith(" ")){
            return arr[arr.length-1];
        }
        return arr[arr.length-2];
    }
    public String getCurWord(){
        String[] arr = box.getText().split(" ");
        if (arr.length < 1 || box.getText().endsWith(" ")) return "";
        return arr[arr.length-1];
    }
    private void helpMe(ObjectInputStream ois){
        try {
            act = (AutoComTree) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //System.out.println("wtf");
        try {
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setupWC() {
        try {
            FileInputStream fis = new FileInputStream(new File("res/act.dat"));
            ObjectInputStream ois = new ObjectInputStream(fis);
            helpMe(ois);
            fis.close();
            act.addWord("I");

        } catch (Exception e) {//*/
            e.printStackTrace();
            act = new AutoComTree();
            act.setup();//**
        }
        if (act == null) System.out.print("hey");
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                FileOutputStream fos;
                try {
                    File f = new File("res/act.dat");
                    fos = new FileOutputStream(f);


                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(act);
                    oos.close();
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                super.run();

            }
        });
    }
}
