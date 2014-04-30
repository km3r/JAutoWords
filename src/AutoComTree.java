import java.io.Serializable;
import java.util.ArrayList;

/**
 * Kyle Rosenthal
 * 4/28/14
 */
public class AutoComTree implements Serializable{
    Node root;
}
class Node{
    char letter;
    ArrayList<Word> arr;
}
class Word{
    String word;
    int usages;
}