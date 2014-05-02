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
        //System.out.println(str);
        if (str.length() == 0) return;
        int nodeNum = str.substring(0,1).toLowerCase().codePointAt(0) - "a".codePointAt(0);
        if (root[nodeNum] == null) root[nodeNum] = new Node((char)nodeNum);
        addWordHelper(str,1,root[nodeNum]);
    }
    public void addWordHelper(String str, int pos, Node n){
        n.addWord(str);
        if (pos == str.length()) {

            return;
        }
        int nodeNum = str.substring(pos,pos+1).toLowerCase().codePointAt(0) - "a".codePointAt(0);
        if (n.children[nodeNum] == null) n.children[nodeNum] = new Node((char)nodeNum);
        addWordHelper(str,pos+1,n.children[nodeNum]);
    }


    String str = "";
    public String getRestWord(String cur){
        if (cur.length() < 1) return "";
        str = "";
        if (cur.length() == 0) return null;
        int nodeNum = cur.substring(0,1).toLowerCase().codePointAt(0) - "a".codePointAt(0);
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

    //String word;
    //ArrayList<Word> arr;
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
        for (String s: map.keySet()){
            if (map.get(s).usages > max.usages)
                max = map.get(s);
            else if (map.get(s).word.length() > 4
                    && map.get(s).word.length() < max.word.length())
            {
                max = map.get(s);
            }
        }
        if ( max.usages == 0) return null;
        return max.word;
    }
}
class Word implements Serializable{
    private static final long serialVersionUID = 123L;
    String word;
    int usages;

    Word(String word){
        this.word = word;
        usages = 1;
    }

}