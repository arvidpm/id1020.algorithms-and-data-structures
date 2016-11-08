package id1020.laboration1;

/**
 * Created by arvid on 2016-11-02.
 */

public class RecursivePascal implements Pascal {

    // Recursively prints each row (@n) after calculating each element in the suggested direction(@b).
    public void printPascal(int n, boolean reverse) {

        if (!reverse && n > 0) {
            printPascal(n - 1, reverse);
        }

        for (int i = 0; i < n; i++) {
            System.out.print(binom(n - 1, i) + (n-1 > i ? " " : "\n"));
        }

        if (reverse && n > 0) {
            printPascal(n - 1, reverse);
        }
    }

    public int binom(int n, int k) {

        if (k == 0 || k == n) {
            return 1;
        }

        return binom(n - 1, k - 1) + binom(n - 1, k);
    }


    public void setPascalArray(int n, int[][] pascalArray) {

        // We call setPascalArray(n - 1, pascalArray) until all values are stored in pascalArray
        if (n > 0) setPascalArray(n - 1, pascalArray);

        // Save results to pascalArray
        for (int i = 0; i < n; i++)
        {
            pascalArray[n-1][i] = binomArray(n - 1, i, pascalArray);
        }
    }

    public int binomArray(int n, int k, int[][] pascalArray){

        if (k == 0 || k == n) {
            return 1;
        }
        return pascalArray[n-1][k-1] + pascalArray[n-1][k];
    }

    public void printPascalArray(int n, boolean reverse){

        // Initialize two dimensional array with n rows, n elements
        int[][] pascalArray = new int[n][n];

        // Sets results in pascalArray
        setPascalArray(n, pascalArray);

        // Print out, if reverse == true an upside down triangle is printed
        if (reverse) {
            for (int row = n-1; row < n; row-- ) {
                for (int column = 0; column <= row; column++) {
                    System.out.print(row > column ? pascalArray[row][column] + " " : pascalArray[row][column] + "\n");
                }
            }
        } else {
            for (int row = 0; row < n; row++ ) {
                for (int column = 0; column <= row; column++) {
                    System.out.print(row > column ? pascalArray[row][column] + " " : pascalArray[row][column] + "\n");
                }
            }
        }
    }

    public static void main(String[] args) {

        // RecursivePascal object
        RecursivePascal o = new RecursivePascal();

        /*
        * calcPascal calls setPascalArray which stores
        * result values to pascalArray before calling printPascalArray
        * */
        o.printPascalArray(33, false);

        System.out.println();

        /* Call printPascal with param (int, boolean)
         * @param int - number of rows
         * @param boolean - true for upside down triangle
         */
        o.printPascal(33, false);
    }
}