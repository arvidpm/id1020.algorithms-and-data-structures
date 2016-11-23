package testRecursion;

import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

/**
 * Created by arvid on 2016-11-02.
 */

public class Test {

    public static void main(String args[]){

        while (!StdIn.isEmpty()){

            StdOut.println("Got -> " + StdIn.readString());
        }
    }
}
