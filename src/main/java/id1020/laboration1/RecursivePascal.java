package id1020.laboration1;

/**
 * Created by arvid on 2016-11-02.
 */

public class RecursivePascal {

    //public RecursiveObject(){}

    public static void print(int rows) {
        for (int n = 0; n < rows; n++) {
            for (int k = 0; k <= n; k++) {
                System.out.print(binom(n, k) + " ");
            }
            System.out.println();
        }
    }

    public static int binom(int n, int k) {
        if (k == 0) {
            return 1;
        } else if (k == n) {
            return 1;
        } else {
            return binom(n - 1, k - 1) + binom(n - 1, k);
        }

    }

    public static void main(String[] args) {

        print(10);

    }
}