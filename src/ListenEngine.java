
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
    NextWordChain nwc;

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


    String prev;

    /**
     * finishes a word and updates usages across the databases, plus uses that NWC to guess the next word
     * @param s
     */
    private void finishWord(String s) {



        String str =box.getText().substring(0,box.getText().length()-currSel.length()) + currSel + s;
        box.setText(str);
        currSel ="";

        act.addWord(getLastWord());

        if (prev != null){
            nwc.add(prev,getLastWord());
        }

        //TODO:next word prediction
        String high = nwc.get(getLastWord());
        if (high.equals("")){
            prev = getLastWord();
            return;
        }
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
        prev = getLastWord();

    }


    /**
     * gets the last entered word
     * @return
     */
    public String getLastWord(){
        String[] arr = box.getText().split(" ");
        if (arr.length < 1) return "";
        if (arr.length > 0 && box.getText().endsWith(" ")){
            return arr[arr.length-1];
        }
        return arr[arr.length-2];
    }

    /*
    gets the currently being worked on word
     */
    public String getCurWord(){
        String[] arr = box.getText().split(" ");
        if (arr.length < 1 || box.getText().endsWith(" ")) return "";
        return arr[arr.length-1];
    }

    /**
     * helper methods for loading
     * @param ois
     */
    private void helpMe(ObjectInputStream ois){
        try {
            act = (AutoComTree) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void helpMe2(ObjectInputStream ois){
        try {
            nwc = (NextWordChain) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * called to set up the class
     */
    public void setupWC() {
        System.getProperty("user.home");
        try {
            FileInputStream fis = new FileInputStream(new File("./act.dat"));
            ObjectInputStream ois = new ObjectInputStream(fis);
            helpMe(ois);
            fis.close();
            act.addWord("I");

        } catch (Exception e) {

            act = new AutoComTree();
            act.setup();
        }
        try {
            FileInputStream fis2 = new FileInputStream(new File("./nwc.dat"));
            ObjectInputStream ois2 = new ObjectInputStream(fis2);
            helpMe2(ois2);
            fis2.close();


        } catch (Exception e) {
            nwc = new NextWordChain();

        }
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                FileOutputStream fos;
                try {
                    File f = new File("./act.dat");
                    fos = new FileOutputStream(f);


                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(act);
                    oos.close();
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                FileOutputStream fos2;
                try {
                    File f2 = new File("./nwc.dat");
                    fos2 = new FileOutputStream(f2);


                    ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
                    oos2.writeObject(nwc);
                    oos2.close();
                    fos2.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                super.run();

            }
        });
    }
}
