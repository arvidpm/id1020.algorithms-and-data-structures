package testRecursion;

/**
 * Created by arvid on 2016-11-02.
 */
public class TestRecursion {

    public static void main(String args[])
    {

        int fWithLoop = computeFactorialWithLoop(3);
        int fWithRecursion = findFactorialRecursion(3);
        int fibonacciCalculated = computeFibonacci(6);

        System.out.println(fWithLoop);
        System.out.println(fWithRecursion);
        System.out.println(fibonacciCalculated);

    }


    public static int computeFactorialWithLoop(int n)
    {
        int factorial = n;
        for (int i = n - 1; i >= 1; i--) {
            factorial = factorial * i;
        }
        return factorial;
    }


    public static int findFactorialRecursion(int n)
    {
        if ( n == 1 || n == 0) {
            return 1;
        } else {
            return (n * findFactorialRecursion(n-1));
        }
    }


    public static int computeFibonacci(int n){

        int fibonacci = n;

        if (n == 0 || n == 1) {
            return n;
        } else {

            return (fibonacci*(n - 1) + fibonacci*(n - 2));
        }

    }

}
