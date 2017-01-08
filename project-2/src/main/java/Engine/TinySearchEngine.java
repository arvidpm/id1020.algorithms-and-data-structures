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
            String nQuery = "";

            if (queries[queries.length - 2].equalsIgnoreCase("relevance")) sortProp = 0;
            else if (queries[queries.length - 2].equalsIgnoreCase("popularity")) sortProp = 1;
            else System.out.println("Unknown sorting criteria.");

            if (queries[queries.length - 1].equalsIgnoreCase("asc")) sortOrder = 0;
            else if (queries[queries.length - 1].equalsIgnoreCase("desc")) sortOrder = 1;
            else System.out.println("Unknown ordering criteria.");

            sorting = " ORDERBY "
                    + (sortProp == 0 ? "RELEVANCE" : "POPULARITY")
                    + " " + (sortOrder == 0 ? "ASC" : "DESC");

            for (int i = 0; i < queries.length - 3; i++) {
                nQuery += " " + queries[i];
            }

            /* Discard first space */
            query = nQuery.substring(1);
        }

        /* Converts prefix search query to infix, removes parenthesis and calls infixSearch.
        * Stores results in resUnsorted
        */
        Map<Document, Double> resUnsorted = infixSearch(removeParenthesis(preToInf(query)));
        List<Document> results;

        /* Sorts result ascending and reverts to descending if sortOrder == 1 */
        if (sort) {
            results = sortResults(resUnsorted);
            if (sortOrder == 1) Collections.reverse(results);
        } else results = new ArrayList<Document>(resUnsorted.keySet());

        return results;
    }

    /* Sort results on selected property */
    private List<Document> sortResults(Map<Document, Double> documents)
    {
        List<Document> results = new ArrayList<Document>();

        while (!documents.isEmpty())
        {
            double smallestValue = 0;
            Document smallestDocument = null;

            for (Document document : documents.keySet())
            {
                if (smallestDocument == null || (sortProp == 0 && documents.get(document) < smallestValue) || (sortProp == 1 && document.popularity < smallestValue))
                {
                    smallestValue = sortProp == 0 ? documents.get(document) : document.popularity;
                    smallestDocument = document;
                }
            }

            results.add(smallestDocument);
            System.out.println("Added " + smallestDocument + " with " + smallestValue);
            documents.remove(smallestDocument);
        }

        return results;
    }

    private String preToInf (String query) {

        query = query.trim();
        query = new StringBuilder(query).reverse().toString();

        Stack<Character> stack = new Stack<Character>();

        for (char c : query.toCharArray()) {
            stack.push(c);
        }
        return query = preToInf(stack);
    }

    private String preToInf (Stack<Character> stack) {

        String res = "";

        if (stack.size() == 0) { return ""; }

        char c  = stack.pop();

        if (c == '+' || c == '-' || c == '|') {

            res += "(";
            res += preToInf(stack);
            res += c;
            res += preToInf(stack);
            res += ")";
        }
        else if (c == ' ') {
            res += preToInf(stack);
        } else {

            res += c;
            if (stack.size() != 0 && stack.peek() != ' ') {
                res += preToInf(stack);
            }
        }
        return res;
    }


    private Map<Document, Double> infixSearch (String query) {

        Map<Document, Double> res = new HashMap<Document, Double>();
        String str1, str2;
        char c;
        int depth = 0;

        for (int i = 0; i < query.length(); i++) {

            char current = query.charAt(i);

            if (depth == 0 && current == '+' || current == '-' || current == '|') {

                str1 = removeParenthesis(query.substring(0, i));
                str2 = removeParenthesis(query.substring(i + 1));
                c = query.charAt(i);

                Map<Document, Double> doc1 = isSingleWord(str1) ? searchOneWord(str1) : infixSearch(str1);
                Map<Document, Double> doc2 = isSingleWord(str1) ? searchOneWord(str2) : infixSearch(str2);

                if      (c == '+') res = union(res, intersection(doc1, doc2));
                else if (c == '-') res = union(res, difference(doc1, doc2));
                else if (c == '|') res = union(res, union(doc1, doc2));
            }

            if (current == '(' ) depth++;
            else if (current == ')' ) depth--;
        }

        String[] splitQuery = query.split(" ");

        if (splitQuery.length == 1) {
            Map<Document, Double> oneWordQuery = searchOneWord(query);
            res = union(res, oneWordQuery);
        }

        return res;
    }

    private Map<Document, Double> searchOneWord (String word) {

        Map<Document, Double> res = new HashMap<Document, Double>();

        for (Document document : index.keySet()) {
            for (Words words : index.get(document)) {
                if (words.word.equals(word)) {

                    /* Calculate term frequency */
                    double termFreq = ((double) words.attr.size() / (double) count.get(document));
                    res.put(document, termFreq);
                    break;
                }
            }
        }

        if (res.size() != 0) {

            /* Calculate inverse document frequency */
            double inverseDocFreq = Math.log10(index.keySet().size() / res.size());

            for (Document document : res.keySet()) {
                res.put(document, res.get(document) * inverseDocFreq);
            }
        }
        return res;
    }

    private Map<Document, Double> intersection(Map<Document, Double> list1, Map<Document, Double> list2)
    {
        Map<Document, Double> list = new HashMap<Document, Double>();

        for (Document document : list1.keySet())
        {
            if (list2.containsKey(document)) { list.put(document, list1.get(document) + list2.get(document)); }
        }

        return list;
    }

    private Map<Document, Double> difference(Map<Document, Double> list1, Map<Document, Double> list2)
    {
        Map<Document, Double> list = new HashMap<Document, Double>();

        for (Document document : list1.keySet())
        {
            if (!list2.containsKey(document)) { list.put(document, list1.get(document)); }
        }

        return list;
    }

    private Map<Document, Double> union(Map<Document, Double> list1, Map<Document, Double> list2)
    {
        Map<Document, Double> list = new HashMap<Document, Double>(list1);

        for (Document document : list2.keySet())
        {
            if (list.containsKey(document)) { list.put(document, list.get(document) + list2.get(document)); }
            else { list.put(document, list2.get(document)); }
        }

        return list;
    }

    private boolean isSingleWord (String str) {
        return str.length() - str.replaceAll("[\\+\\-\\|]", "").length() == 0;
    }

    private String removeParenthesis(String string) {
        return string.replaceAll("\\)$|^\\(", "");
    }

    public String infix(String query) {
        return preToInf(query) + sorting;
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