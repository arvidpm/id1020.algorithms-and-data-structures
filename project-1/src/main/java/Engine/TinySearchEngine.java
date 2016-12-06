package Engine;

import se.kth.id1020.Driver;
import se.kth.id1020.TinySearchEngineBase;
import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Document;
import se.kth.id1020.util.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arvid on 2016-12-01.
 */

class TinySearchEngine implements TinySearchEngineBase {

    private ArrayList<ArrayList<Words>> documentWords = new ArrayList<ArrayList<Words>>();
    private ArrayList<Document> docs = new ArrayList<Document>();

    public static void main(String[] args) throws Exception {

        TinySearchEngineBase searchEngine = new TinySearchEngine();
        Driver.run(searchEngine);

    }

    public void insert(Word word, Attributes attr) {

        ArrayList<Words> docWords;

        /* Existing document */
        if (docs.contains(attr.document)) {

            int index = docs.indexOf(attr.document);
            docWords = documentWords.get(index);

        } else {
            /* New document */
            docs.add(attr.document);
            docWords = new ArrayList<Words>();
            documentWords.add(docWords);
        }

        /*
        *  Add the word to the collection by using Java compareTo method
        *  Checks for duplicates and if so adds attributes to the index.
        *  If the word is bigger we insert at the given index, thus sorting.
        * */
        for (int index = 0; index <= docWords.size(); index++) {

            if (docWords.size() == index) {
                docWords.add(new Words(word, attr));
                break;
            }

            int cmp = docWords.get(index).word.compareTo(word.word);

            if (cmp == 0) {
                docWords.get(index).attr.add(attr);
                break;
            }

            if (cmp > 0) {
                docWords.add(index, new Words(word, attr));
                break;
            }
        }
    }

    public List<Document> search(String query) {

        /* For multiple query words */
        String[] queries = query.split(" ");

        boolean sort = false;
        int prop = 0;
        int order = 0;

        List<Docs> res = new ArrayList<Docs>();

        /*
        * If array > 3, and the first word after our query words is 'orderby', we run the body.
        * By setting 'orderby' element to null we break the for-loop when all
        * word queries have been searched for.
        * */
        if (queries.length > 3 && queries[queries.length - 3].equalsIgnoreCase("orderby")) {

            sort = true;

            if (queries[queries.length - 2].equalsIgnoreCase("count")) prop = 1;
            else if (queries[queries.length - 2].equalsIgnoreCase("popularity")) prop = 2;
            else if (queries[queries.length - 2].equalsIgnoreCase("occurrence")) prop = 3;
            else System.out.println("Unknown sorting criteria.");

            if (queries[queries.length - 1].equalsIgnoreCase("asc")) order = 1;
            else if (queries[queries.length - 1].equalsIgnoreCase("desc")) order = 2;
            else System.out.println("Unknown ordering criteria.");

            queries[queries.length - 3] = null;
        }

        for (String q : queries) {

            /* Here we break if a word previously was 'orderby', which was set to null */
            if (q == null) break;

            /* Searching all documents */
            for (int i = 0; i < documentWords.size(); i++) {

                /* Searching for word q in document i */
                int index = binarySearch(q, documentWords.get(i));

                /* If we got a hit */
                if (index != -1) {

                    /* If the current document already is in the resultset, we find the reference */
                    Docs docContainer = null;
                    for (Docs resItem : res) {

                        if (resItem.doc.equals(docs.get(i))) {
                            docContainer = resItem;
                            break;
                        }
                    }

                    /* if: we find a result in a document (first time)
                     * else: document already part of results
                     * */
                    if (docContainer == null) res.add(new Docs(docs.get(i), documentWords.get(i).get(index)));
                    else docContainer.word.add(documentWords.get(i).get(index));
                }
            }
        }

        /* Unimplemented sorting of results by properties in selected order */
        if (sort) {
            if (prop == 1) System.out.println("Should be sorted by count.");
            if (prop == 2) System.out.println("Should be sorted by popularity.");
            if (prop == 3) System.out.println("Should be sorted by occurrence.");
            if (order == 1) System.out.println("Should be ordered ascending.");
            if (order == 2) System.out.println("Should be ordered descending.");
        }

        /* Results to be returned */
        List<Document> results = new ArrayList<Document>();

        for (Docs documentContainer : res) {
            if (!results.contains(documentContainer.doc)) results.add(documentContainer.doc);
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


    /* Constructors */
    private class Words {
        private String word;
        private ArrayList<Attributes> attr = new ArrayList<Attributes>();

        private Words(Word word, Attributes attr) {
            this.word = word.word;
            this.attr.add(attr);
        }
    }

    private class Docs {
        private Document doc;
        private ArrayList<Words> word = new ArrayList<Words>();

        private Docs(Document doc, Words word) {
            this.doc = doc;
            this.word.add(word);
        }
    }
}