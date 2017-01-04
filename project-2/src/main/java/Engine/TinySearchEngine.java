package Engine;

import se.kth.id1020.Driver;
import se.kth.id1020.TinySearchEngineBase;
import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Document;
import se.kth.id1020.util.Sentence;
import se.kth.id1020.util.Word;

import java.util.*;

/**
 * Created by arvid on 2017-01-02.
 */

class TinySearchEngine implements TinySearchEngineBase {

    private Map<Document, List<TinySearchEngine.Words>> index;
    private Map<Document, Integer> count;

    private ArrayList<ArrayList<Words>> documentWords = new ArrayList<ArrayList<Words>>();
    private ArrayList<Document> docs = new ArrayList<Document>();

    private byte sortProp, sortOrder;
    private boolean sort;
    private String sorting;


    public static void main(String[] args) throws Exception {

        TinySearchEngineBase searchEngine = new TinySearchEngine();
        Driver.run(searchEngine);

    }


    public void preInserts() {

        index = new HashMap<Document, List<Words>>();
        count = new HashMap<Document, Integer>();

    }

    public void insert(Sentence sentence, Attributes attr) {

        /* Every word in a sentence */
        for (Word word : sentence.getWords()) {

            List<Words> docWords = index.get(attr.document);

            if (docWords == null) {

                docWords = new ArrayList<Words>();
                index.put(attr.document, docWords);
            }

            Words words = null;

            for (Words tempWords : docWords) {
                if (tempWords.word.equals(word.word)) {
                    words = tempWords;
                    break;
                }
            }

            if (words == null) {
                words = new Words(word, attr);
                docWords.add(words);

            } else {
                words.attr.add(attr);
            }
        }
    }

    public void postInserts() {

        for (Document doc : index.keySet()) {
            int count = 0;

            for (Words words : index.get(doc)) {
                count += words.attr.size();
            }
            this.count.put(doc, count);
        }
        for (List<Words> words : index.values()) {
            Collections.sort(words);
        }
    }


    public List<Document> search(String query) {

        if (query.length() == 0) { return null; }

        /* For multiple query words */
        String[] queries = query.split(" ");

        sort = false;
        sorting = "";

        /*
        * If array > 3, and the first word after our query words is 'orderby', we run the body.
        * By setting 'orderby' element to null we break the for-loop when all
        * word queries have been searched for.
        * */
        if (queries.length > 3 && queries[queries.length - 3].equalsIgnoreCase("orderby")) {

            sort = true;

            if (queries[queries.length - 2].equalsIgnoreCase("relevance")) sortProp = 0;
            else if (queries[queries.length - 2].equalsIgnoreCase("popularity")) sortProp = 1;
            else System.out.println("Unknown sorting criteria.");

            if (queries[queries.length - 1].equalsIgnoreCase("asc")) sortOrder = 0;
            else if (queries[queries.length - 1].equalsIgnoreCase("desc")) sortOrder = 1;
            else System.out.println("Unknown ordering criteria.");

            sorting = " ORDERBY "
                    + (sortProp == 0 ? "RELEVANCE" : "POPULARITY")
                    + " " + (sortOrder == 0 ? "ASC" : "DESC");

        }


        /* Unimplemented sorting of results by properties in selected order */
        if (sort) {
            if (prop == 1) System.out.println("Should be sorted by count.");
            if (prop == 2) System.out.println("Should be sorted by popularity.");
            if (prop == 3) System.out.println("Should be sorted by occurrence.");
            if (order == 1) System.out.println("Should be ordered ascending.");
            if (order == 2) System.out.println("Should be ordered descending.");
        }

        return results;
    }

    /* A modified binarySearch from lab 1.
    * Comparision is done with compareTo, in the interval 0 - (ArrayList-1)
    * If (cmp > 0) we set hi to mid-1. If (cmp < 0) we set low to mid+1.
    * If word is found we return mid (index), else -1.
    * */
    private int binarySearch(String word, ArrayList<Words> Words) {

        if (Words.size() == 0) return -1;

        int lo = 0;
        int hi = Words.size() - 1;

        while (lo <= hi) {

            int mid = lo + (hi - lo) / 2;
            String midWord = Words.get(mid).word;

            int cmp = midWord.compareTo(word);

            if (cmp > 0) hi = mid - 1;
            else if (cmp < 0) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    public String infix(String query) {
        return null;

    }


    /* Constructors */
    private class Words implements Comparable<Words> {

        private String word;
        private ArrayList<Attributes> attr = new ArrayList<Attributes>();

        private Words(Word word, Attributes attr) {
            this.word = word.word;
            this.attr.add(attr);
        }

        public int compareTo(Words compareWord) {
            return word.compareTo(compareWord.word);
        }
    }
}