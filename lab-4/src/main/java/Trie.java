/**
 * Created by Damian on 28/11/2016.
 */
public class Trie {
    //char character;
    Trie[] children;
    boolean complete;
    int value;
    //int getValue;

    public Trie() {
        //this.character = 0;
        this.children = new Trie[26];
        this.complete = false;
        this.value = 0;
    }

    public void put(String word) {
        if (word.isEmpty()) {
            this.complete = true;
            this.value++;
            //System.out.print("End value= " + this.value + "\n");
            return;
        }

        char letter = word.charAt(0);
        int index = letter - 'a';

        if (this.children[index] == null) {
            this.children[index] = new Trie();
            this.children[index].value = this.value;
            //System.out.print("Current word= " + word + " current value= " + this.value + "\n");
        }

        this.children[index].put(word.substring(1));
    }


    public int get(String word) {
        int getValue = 0;
        if (word.length()>1) {
            char letter = word.charAt(0);
            int index = letter - 'a';
            System.out.println(letter);

            if (this.children[index] != null) {
                this.children[index].get(word.substring(1));
            }
        } else {
            //JAG VILL ATT DET HÄR SKA HÄNDA MEN DET HÄNDER INTE!!!!!!!!!!!!!!!!!!!!!
            return this.value;
        }
        return getValue;
    }



   /* public void count(String word){

        char letter = word.charAt(0);
        int index = letter - 'a';

        if(this.children[index] != null){
            this.children[index].get(word.substring(1));
        }

        if(this.children[index] == null){
            int total = this.value;
            if (word.isEmpty()){


            }
            if (word.isEmpty() == false){
                System.out.print("Key does not exist.");
            }

        }
    }*/


    public static void main(String args[]) {
        Trie k = new Trie();
        k.put("abc");
        k.put("abd");
        k.put("abdd");
        System.out.print(k.get("abdd"));

    }

}