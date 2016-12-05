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

    private ArrayList<ArrayList<Words>> words = new ArrayList<ArrayList<Words>>();
    private ArrayList<Document> docs = new ArrayList<Document>();

    private class Words {
        private String word;
        private ArrayList<Attributes> attr = new ArrayList<Attributes>();

        private Words(Word word, Attributes attr) {
            this.word = word.word;
            this.attr.add(attr);
        }
    }

    // Used to hold search results while processing
    private class Docs {
        private Document doc;
        private ArrayList<Words> word = new ArrayList<Words>();

        public Docs(Document doc, Words word) {
            this.doc = doc;
            this.word.add(word);
        }
    }

    public void insert(Word word, Attributes attr) {

        ArrayList<Words> docWords;

        if (docs.contains(attr.document)) {

            int index = docs.indexOf(attr.document);
            docWords = words.get(index);

        } else {

            docs.add(attr.document);
            docWords = new ArrayList<Words>();
            words.add(docWords);
        }

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

        String[] queries = query.split(" ");

        boolean sort = false;
        int prop = 0;
        int order = 0;

        List<Docs> res = new ArrayList<Docs>();

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

            if (q == null) break;

            for (int i = 0; i < words.size(); i++) {

                int index = binarySearch(q, words.get(i));
                if (index != -1) {

                    Docs documentContainer = null;

                    for (Docs tmpDocumentContainer : res) {

                        if (tmpDocumentContainer.doc.equals(docs.get(i))) {
                            documentContainer = tmpDocumentContainer;
                            break;
                        }
                    }

                    if (documentContainer == null) {
                        res.add(new Docs(docs.get(i), words.get(i).get(index)));
                    } else {
                        documentContainer.word.add(words.get(i).get(index));
                    }
                }
            }
        }

        List<Document> results = new ArrayList<Document>();

        if (sort) {
            if (prop == 1) System.out.println("Should be sorted by count.");
            if (prop == 2) System.out.println("Should be sorted by popularity.");
            if (prop == 3) System.out.println("Should be sorted by occurrence.");
            if (order == 1) System.out.println("Should be ordered ascending.");
            if (order == 2) System.out.println("Should be ordered descending.");
        }

        for (Docs documentContainer : res) {
            if (!results.contains(documentContainer.doc)) {
                results.add(documentContainer.doc);
            }
        }
        return results;
    }


    private int binarySearch(String word, ArrayList<Words> Words) {
        if (Words.size() == 0) { return -1; }

        int lo = 0;
        int hi = Words.size() - 1;

        while (lo <= hi) {

            int mid = lo+(hi-lo)/2;
            String midWord = Words.get(mid).word;

            int cmp = midWord.compareTo(word);

            if (cmp > 0) { hi = mid - 1; }
            else if (cmp < 0) { lo = mid + 1; }
            else { return mid; }
        }
        return -1;
    }

    
    public static void main(String[] args) throws Exception {

        TinySearchEngineBase searchEngine = new TinySearchEngine();
        Driver.run(searchEngine);

    }
}