import org.omg.CORBA.INTERNAL;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Kyle Rosenthal
 * 4/28/14
 */
public class AutoComTree implements Serializable{
    private static final long serialVersionUID = 1L;
    Node[] root = new Node[26];
    public void setup(){
        try {
            BufferedReader br1 = new BufferedReader(new FileReader("res/words.txt"));
            String line = br1.readLine();
            while (line != null){
                addWord(line);
                line = br1.readLine();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    public void addWord(String str){

        //TODO: handle ' (treat as letter) and . (remove)
        //str = str.replaceAll(".","");

        if (str.length() == 0) return;
        int nodeNum = str.substring(0,1).toLowerCase().codePointAt(0) - "a".codePointAt(0);
        if (nodeNum > 26 || nodeNum < 0) return;
        if (root[nodeNum] == null) root[nodeNum] = new Node((char)nodeNum);
        addWordHelper(str,1,root[nodeNum]);
    }
    public void addWordHelper(String str, int pos, Node n){
        n.addWord(str);
        if (pos == str.length()) {

            return;
        }
        int nodeNum = str.substring(pos,pos+1).toLowerCase().codePointAt(0) - "a".codePointAt(0);
        if (nodeNum > 26 || nodeNum < 0) return;
        if (n.children[nodeNum] == null) n.children[nodeNum] = new Node((char)nodeNum);
        addWordHelper(str,pos+1,n.children[nodeNum]);
    }


    public String str = "";
    public String getRestWord(String cur){
        if (cur.length() < 1) return "";
        //cur = cur.replaceAll(".","");
        str = "";
        if (cur.length() == 0) return null;
        int nodeNum = cur.substring(0,1).toLowerCase().codePointAt(0) - "a".codePointAt(0);
        if (nodeNum > 26 || nodeNum < 0) return "";
        if (root[nodeNum] == null) return "";
        getRestWordHelp(cur,1,root[nodeNum]);
        if (str == null || str.length() < 1) return "";
        return str.substring(cur.length());
    }
    private void getRestWordHelp(String str, int pos, Node n){
        if (pos == str.length()) {
            this.str = n.getSug();
            return;
        }
        int nodeNum = str.substring(pos,pos+1).toLowerCase().codePointAt(0) - "a".codePointAt(0);
        if (n.children[nodeNum] == null) return;
        getRestWordHelp(str,pos+1,n.children[nodeNum]);
    }




}
class Node implements Serializable{
    private static final long serialVersionUID = 21L;
    char letter;

    HashMap<String,Word> map;
    Node[] children = new Node[26];

    Node(char c){
        letter = c;
        map = new HashMap<String, Word>();
    }
    public void addWord(String str){
        if (map.containsKey(str)){
            map.get(str).usages++;
        } else {
            map.put(str,new Word(str));
        }
    }

    public String getSug(){
        Word max = new Word("");
        max.usages--;
        int mVal = Integer.MIN_VALUE;
        int testVal;
        for (String s: map.keySet()){
            testVal = map.get(s).usages * 2 - map.get(s).word.length();
            if (map.get(s).word.length() <= 4) testVal -= 8;
            if ( testVal > mVal)
            {
                mVal = testVal;
                max = map.get(s);
            }



        }
        if ( max.usages == 0) return null;
        return max.word;
    }
}
