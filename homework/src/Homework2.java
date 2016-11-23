package id1020.homework;

import edu.princeton.cs.algs4.Stack;

/**
 * Created by arvid on 2016-11-10.
 */
public class Homework2 {

    public static void fun(char[] input) {
        Stack s = new Stack();
        for (int i = 0; i < input.length; i++) {
            if (input[i] == '+') {
                s.push(input[i]);
            } else if (input[i] == '*') {
                s.push(input[i]);
            } else if (input[i] == ')') {
                System.out.print(s.pop() + " ");
            } else if (input[i] == '(') {
                System.out.print("");
            } else {
                System.out.print(input[i] + " ");
            }
        }
        System.out.println();
    }

    public static int test ( int n)
    {
        if (n <= 2) return 1;
        else {
            System.out.println(n);
            return test(n-2) * test(n-2);
        }

    }

    public static int f(int n) {
        int s = 0;
        while(n > 1) {
            n = n/2;
            s++;
            System.out.println(n);
            System.out.println(s);
        }
        return s;
    }

    public static void main(String args[]){

        // converts: (2+((3+4)*(5*6))) into: 2 3 4 + 5 6 * * +
        char[] chars = new char[]{'(','2','+','(','(','3','+','4',')','*','(','5','*','6',')',')',')'};
        fun(chars);
        System.out.println();

        //test(60);

        f(8);
        
    }


}
