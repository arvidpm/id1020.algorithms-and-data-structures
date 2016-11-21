/**
 * Created by arvid on 2016-11-21.
 */
public class BubbleSort {

    private ListNode first = null;
    private ListNode last = null;
    int size = 0;

    private static class ListNode {
        private int data;
        private ListNode next;

        private ListNode(int data) {
            this.data = data;
            this.next = null;
        }
    }

    public void add(int e) {
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

        ListNode sortedlist = new ListNode();

        sortedlist.add(3);

        System.out.print(sorted1.next);

    }

}
