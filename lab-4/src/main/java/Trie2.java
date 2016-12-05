import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;

public class Trie2 implements Iterable<Entry<String,Integer>>
{
    private Node root = new Node(' ', null, 0);

    public void put (String key)
    {
        Node currentNode = root;

        for (int i = 0; i < key.length(); i++)
        {
            char currentChar = key.charAt(i);
            Node nextNode = currentNode.getChild(currentChar);

            // At least one Node in the path does not exist -- create it
            if (nextNode == null)
            {
                nextNode = new Node(currentChar, key.substring(0, i + 1), 0);
                currentNode.addChild(nextNode);
            }

            currentNode = nextNode;
        }

        currentNode.value = currentNode.value + 1;
    }

    public int get(String key)
    {
        return getNodeFromPrefix(key).value;
    }

    public int count(String prefix)
    {
        return countSum(getNodeFromPrefix(prefix));
    }

    public int distinct(String prefix)
    {
        return distinctSum(getNodeFromPrefix(prefix));
    }

    private int countSum(Node focusChild)
    {
        int sum = focusChild.value;

        for (Node child : focusChild.children)
        {
            sum += countSum(child);
        }

        return sum;
    }

    private int distinctSum(Node focusChild)
    {
        int sum = focusChild.value == 0 ? 0 : 1;

        for (Node child : focusChild.children)
        {
            sum += distinctSum(child);
        }

        return sum;
    }

    private Node getNodeFromPrefix(String prefix)
    {
        Node currentNode = root;

        for (int i = 0; i < prefix.length(); i++)
        {
            Node nextNode = currentNode.getChild(prefix.charAt(i));

            // Node not found
            if (nextNode == null) { return null; }

            currentNode = nextNode;
        }

        return currentNode;
    }

    public Iterator<Entry<String,Integer>> iterator()
    {
        return new TrieIterator("");
    }

    public Iterator<Entry<String,Integer>> iterator(String prefix)
    {
        return new TrieIterator(prefix);
    }

    /* As you might see, the iterator is not very optimized at all, since it goes through
     * all nodes from the root node, /every time/ either next() or hasNext() is called. It
     * does, however, not use any array.
     */
    private class TrieIterator implements Iterator<Entry<String,Integer>>
    {
        int indexStop = 0;
        int indexIterate = 0;
        Node startNode = root;

        public TrieIterator(String prefix)
        {
            startNode = getNodeFromPrefix(prefix);
        }

        public boolean hasNext()
        {
            // Simulate a call for next()
            Entry<String,Integer> next = next();

            // Reset the index variable that we modified in our "simulation"
            indexStop--;

            // Did the next value, i.e. the simulated next() return something useful?
            return next != null;
        }

        // Get the next Entry value in our iteration
        public Entry<String,Integer> next()
        {
            // We want to go one node further than last time
            indexStop++;

            // Reset temporary index
            indexIterate = -1;

            // Go!
            return getNext(startNode);
        }

        // Used to walk though nodes and children until we have done so indexStop number of times
        private Entry<String,Integer> getNext(Node currentNode)
        {
            indexIterate++;

            // We've reached the node we were looking for
            if (indexIterate == indexStop)
            {
                // Convert the node's path ("name") and value to an Entry
                return new AbstractMap.SimpleEntry<String,Integer>(currentNode.path, currentNode.value);
            }

            // Go through our children
            for (Node child : currentNode.children)
            {
                // For each child, call getNext() recursively
                Entry<String,Integer> next = getNext(child);

                if (next != null) { return next; }
            }

            // We've reached the End of Everything
            return null;
        }
    }

    private class Node
    {
        private char name;
        private String path;
        private int value;
        private ArrayList<Node> children = new ArrayList<Node>();

        public Node(char name, String path, int value)
        {
            this.name = name;
            this.path = path;
            this.value = value;
        }

        public void addChild(Node child)
        {
            // We wish to have our children sorted alphabetically
            for (int i = 0; i < children.size(); i++)
            {
                if (children.get(i).name > child.name)
                {
                    children.add(i, child);
                    return;
                }
            }

            children.add(child);
        }

        public Node getChild(char name)
        {
            for (Node child : children)
            {
                if (child.name == name) { return child; }
            }

            return null;
        }
    }
}