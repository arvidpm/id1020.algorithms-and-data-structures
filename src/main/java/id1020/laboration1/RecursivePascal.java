package id1020.laboration1;

/**
 * Created by arvid on 2016-11-02.
 */

public class RecursivePascal {

    public void calcPascal(int n, boolean reverse){

        // Initialize two dimensional array with n rows, n elements
        int[][] pascalArray = new int[n][n];

        printPascal(n, reverse, pascalArray);
        print(reverse, pascalArray);

    }

    public void printPascal(int n, boolean reverse, int[][] pascalArray) {

        // If !reverse and n > 0 we call printPascal(n - 1, reverse)
        if (!reverse && n > 0) {
            printPascal(n - 1, reverse, pascalArray);
        }

        // Triangle print out
        for (int i = 0; i < n; i++)
        {
            //System.out.print(binom(n - 1, i) + (n == i + 1 ? "\n" : " "));

            int result = binomArray(n - 1, i, pascalArray);
            pascalArray[n-1][i] = result;
            System.out.print(result + (n == i + 1 ? "\n" : " "));

        }

        if (reverse && n > 0) {
            printPascal(n - 1, reverse, pascalArray);
        }
    }

    public int binom(int n, int k) {

        if (k == 0 || k == n) {
            return 1;
        } else {
            return binom(n - 1, k - 1) + binom(n - 1, k);
        }

    }

    public int binomArray(int n, int k, int[][] pascalArray){

        if (k == 0 || k == n) {
            return 1;
        } else {
            return pascalArray[n-1][k-1] + pascalArray[n-1][k];
        }

    }

    public void print(boolean reverse, int[][] pascalArray){


    }

    public static void main(String[] args) {

        // RecursivePascal object
        RecursivePascal o = new RecursivePascal();

        /* Call printPascal with param (int, boolean)
         * @param int - number of rows
         * @param boolean - true for upside down triangle
         */
        o.calcPascal(30, true);



    }
}


        /*
        if (!b && n > 0) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j <= i; j++) {
                    System.out.print(binom(i, j) + " ");
                }

                System.out.println();
            }
        }
        */