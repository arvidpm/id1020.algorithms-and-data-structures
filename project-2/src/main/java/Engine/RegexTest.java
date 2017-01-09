package Engine;

/**
 * Created by arvid on 2017-01-03.
 */
public class RegexTest {

    public static void regexTest() {

        String query = "+ + nightmare whale | nightmare dog orderby relevance asc ";

        String[] queries = query.split(" ");

        System.out.println(queries);

        String queriesNew = "";

        for (int i = 0; i < queries.length - 3; i++) {
            queriesNew += " " + queries[i];
        }

        query = queriesNew.substring(1);

        System.out.println(query);


        String test = query.replaceAll("([^ ]{1})([\\+\\-\\|]{1})([^ ]{1})", "$1 $2 $3");
        test = test.trim();

        query = new StringBuilder(test).reverse().toString();

        System.out.print(query);
    }
}
