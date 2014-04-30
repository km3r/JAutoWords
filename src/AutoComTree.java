import java.io.Serializable;
import java.util.ArrayList;

/**
 * Kyle Rosenthal
 * 4/28/14
 */
public class AutoComTree implements Serializable{
    Node[] root = new Node[26];
    public void setup(){

    }
    public void addWord(String str){
        if (str.length() == 0) return;
        int nodeNum = str.substring(0,1).toLowerCase().codePointAt(0) - "a".codePointAt(0);
        if (root[nodeNum] == null) root[nodeNum] = new Node((char)nodeNum);
        addWordHelper(str,1,root[nodeNum]);
    }
    public void addWordHelper(String str, int pos, Node n){
        if (pos == str.length()) {
            n.word = str;
            return;
        }
        int nodeNum = str.substring(pos,pos+1).toLowerCase().codePointAt(0) - "a".codePointAt(0);
        if (n.children[nodeNum] == null) n.children[nodeNum] = new Node((char)nodeNum);
        addWordHelper(str,pos+1,n.children[nodeNum]);
    }

}
class Node{
    char letter;

    String word;
    //ArrayList<Word> arr;
    Node[] children = new Node[26];

    Node(char c){
        letter = c;
    }
}
class Word{
    String word;
    int usages;
}