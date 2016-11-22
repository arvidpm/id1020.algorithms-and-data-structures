/**
 * Created by arvid on 2016-11-21.
 */
public class BubbleSort {

    /*
    * BubbleSort constructor
    * */
    private ListNode first = null;
    private ListNode last = null;
    private int size = 0;

    /*
    * ListNode constructor
    * */
    private class ListNode {
        private int data;
        private ListNode next;

        private ListNode(int data) {
            this.data = data;
        }
    }

    /*
    * Sort a linked list using Bubble Sort
    * */
    private void bubbleSort() {

        if (this.size == 0) {
            System.out.println("Can't perform BubbleSort on empty list.");
        } else {


            int R = this.size - 2;
            boolean swapped = true;
            int swapable;
            int swaps = 0;
            ListNode pointer;

            while (R >= 0 && swapped) {

                swapped = false;
                pointer = first;

                for (int i = 0; i <= R; i++) {
                    if (pointer.data > pointer.next.data) {

                        swapped = true;
                        swapable = pointer.data;
                        pointer.data = pointer.next.data;
                        pointer.next.data = swapable;
                        swaps++;

                    }
                    // Increment pointer
                    pointer = pointer.next;

                }
                // Decrement list items to sort
                R--;
            }
            System.out.println("\n" + swaps + " swaps.");
        }
    }

    /*
    * Count number of performed inversions without altering list
    * */
    private void countInversions() {
        ListNode pointer = this.first;
        ListNode nextpointer;
        int inversions = 0;

        for (int i = 0; i <= this.size - 2; i++) {

            nextpointer = pointer.next;
            for (int j = i + 1; j < this.size; j++) {
                if (pointer.data > nextpointer.data) {
                    System.out.println("Inverted " + pointer.data + " with " + nextpointer.data);
                    inversions++;
                }
                nextpointer = nextpointer.next;
            }
            pointer = pointer.next;
        }
        System.out.println(inversions + " inversions.\n");
    }

    /*
    * Add node to BubbleSort list.
    * @param value the node data
    * */
    private void addNode(int value) {
        ListNode node = new ListNode(value);
        if (first == null) {
            first = node;
        } else {
            last.next = node;
        }
        last = node;
        size += 1;
    }

    /*
    * Print linked list
    * */
    private void printList() {

        ListNode pointer = this.first;

        for (int i = 0; i < this.size; i++) {
            System.out.print(pointer.data + " ");
            pointer = pointer.next;
        }
        System.out.println();
    }


    public static void main(String[] args) {

        BubbleSort list = new BubbleSort();

        list.addNode(43);
        list.addNode(23);
        list.addNode(8);
        list.addNode(8);
        list.addNode(100);
        list.addNode(1);
        list.addNode(0);

        System.out.println("Counting inversions:");
        list.countInversions();

        System.out.println("Before BubbleSort:");
        list.printList();

        list.bubbleSort();

        System.out.println();
        System.out.println("After BubbleSort:");
        list.printList();
    }
}
