package id1020.homework;

/**
 * Created by arvid on 2016-11-01.
 */
public class Homework1_2 {

    public static void calcArray(int a[]){

        for (int i = 0; i < 10; i++)
            a[i] = i * i;
            System.out.println(a);


    }

    public static void main(String args[]){

        int[] a = new int[9];
        calcArray(a);

    }
}
