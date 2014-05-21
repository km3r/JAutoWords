import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kyle.rosenthal14 on 5/8/14.
 */
public class NextWordChain implements Serializable{
    private static final long serialVersionUID = 1713233L;
    HashMap<String, ArrayList<Word>> data;

    NextWordChain(){
        data = new HashMap<String, ArrayList<Word>>();
    }

    /**
     *  addes the word to the array or up the usages
     * @param before the word before the word that was just inputted
     * @param after the word after the word before
     */
    public void add(String before, String after){
        ArrayList<Word> arr = data.get(before);
        if (arr == null){
            data.put(before,new ArrayList<Word>());
            arr = data.get(before);
        }
        int i;
        for (i = 0; i <= arr.size(); i++){
            if (i == arr.size()){
                i = -1;
                break;
            }
            if (arr.get(i).word.equals(after)){
                break;
            }

        }
        if (i < 0){
            arr.add(new Word(after));
        } else {
            arr.get(i).usages++;
        }

    }

    /**
     *
     * @param before the word just inputted
     * @return the most commonly used next word
     */
    public String get(String before){
        ArrayList<Word> arr = data.get(before);
        if (arr == null) {
            data.put(before,new ArrayList<Word>());
            return "";
        }
        Word max = arr.get(0);
        for (int i = 1; i < arr.size(); i++){
            if(arr.get(i).usages>max.usages){
                max = arr.get(i);
            }
        }
        return max.word;
    }

}
