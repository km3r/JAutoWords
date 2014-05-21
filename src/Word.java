import java.io.Serializable;

/**
 * Created by kyle.rosenthal14 on 5/14/14.
 */

/**
 * each word has a string and a usage count, plus the word can be saved to a file
 */
public class Word implements Serializable {
    private static final long serialVersionUID = 123L;
    String word;
    int usages;

    Word(String word){
        this.word = word;
        usages = 1;
    }

}
