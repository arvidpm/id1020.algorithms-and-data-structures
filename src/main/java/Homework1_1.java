/**
 * Created by arvid on 2016-11-01.
 */
public class Homework1_1 {

    private static boolean assess(int a[], int cand) {

        int i, count = 0;

        for (i = 0; i < a.length; i++) {

            if (a[i] == cand)
                count++;
        }

        System.out.println("For cand: "+cand+" count is: "+count);
        return  (count > a.length / 2);

    }

    public static void main(String args[]) {

        int[] arr = new int[]{1, 2, 3, 1, 2, 3, 1};

        boolean result1 = assess(arr, 1);
        boolean result2 = assess(arr, 2);
        boolean result3 = assess(arr, 3);

        System.out.println("Result 1: "+result1);
        System.out.println("Result 2: "+result2);
        System.out.println("Result 3: "+result3);

    }

}
