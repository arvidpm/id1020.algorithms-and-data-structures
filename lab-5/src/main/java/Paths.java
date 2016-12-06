import se.kth.id1020.Graph;
import se.kth.id1020.DataSource;
/**
 * Created by arvid on 2016-12-05.
 */

public class Paths {
    public static void main(String[] args) {
        Graph g = DataSource.load();

        System.out.println(g.numberOfEdges());
        System.out.print(g.numberOfVertices());

        System.out.print(g.toString());
    }
}