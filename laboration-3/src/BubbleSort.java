/**
 * Created by arvid on 2016-11-21.
 */
public class BubbleSort {

    private ListNode first = null;
    private ListNode last = null;
    private int size = 0;

    private static class ListNode {
        private int data;
        private ListNode next;

        private ListNode(int data) {
            this.data = data;
            this.next = null;
        }
    }

    private void bubbleSort() {

        if (this.size == 0) {
            System.out.println("Can't perform BubbleSort on empty list.");
        } else {

            int R = this.size - 2;
            System.out.println(R);
            boolean swapped = true;
            ListNode current;

            while (R >= 0 && swapped) {

                swapped = false;
                current = first;

                for (int i = 0; i <= R; i++) {
                    if (current.data > current.next.data) {

                        System.out.println(current.data);
                        System.out.println(current.next.data);

                        swapped = true;

                        int swapable = current.data;
                        current.data = current.next.data;
                        current.next.data = swapable;

                        System.out.println(current.data);
                        System.out.println(current.next.data);

                    }
                }
                R--;
            }
        }
    }

    private void addNode(int e) {
        ListNode node = new ListNode(e);
        if (first == null) {
            first = node;
        } else {
            last.next = node;
        }
        last = node;
        size += 1;
    }


    public static void main(String[] args) {

        BubbleSort list = new BubbleSort();

        list.addNode(23);
        list.addNode(43);
        list.addNode(8);
        list.addNode(100);
        list.addNode(1);
        list.addNode(0);

        list.bubbleSort();

        /*

        System.out.println(list.first.data);
        System.out.println(list.first.next.data);
        System.out.println(list.last.data);
        System.out.println(list.size);

        */



    }

}
