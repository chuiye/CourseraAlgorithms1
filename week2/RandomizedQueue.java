import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int n;

    public RandomizedQueue() {                // construct an empty randomized queue
        q = (Item[]) new Object[2];
        n = 0;
    }

    public boolean isEmpty() {                // is the queue empty?
        return n == 0;
    }

    public int size() {                       // return the number of items on the queue
        return n;
    }

    public void enqueue(Item item) {          // add the item
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        if (n == q.length) {
            resize(2 * q.length);
        }
        q[n++] = item;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = q[i];
        }
        q = copy;
    }

    public Item dequeue() {                   // remove and return a random item
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int index = StdRandom.uniform(n);
        Item item = q[index];
        if (index != n-1) {
            q[index] = q[n-1];
        }
        q[n-1] = null;
        n--;

        if (n > 0 && n <= q.length/4) {
            resize(q.length/2);
        }
        return item;
    }

    public Item sample() {                    // return (but do not remove) a random item
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        return q[StdRandom.uniform(n)];
    }

    public Iterator<Item> iterator() {        // return an independent iterator over items in random order
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int[] permu = StdRandom.permutation(n);
        private int current = 0;

        public boolean hasNext() { return current < n; }
        
        public void remove() { throw new java.lang.UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            Item item = q[permu[current]];
            current++;
            return item;
        }
    }

    public static void main(String[] args) {  // unit testing (optional)
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        System.out.printf("Is empty: %b \n", rq.isEmpty());
        System.out.printf("size: %d \n", rq.size());

        System.out.println("Adding...");
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        for (int a: rq) {
            System.out.printf("%d ", a);
        }
        System.out.println();
        for (int a: rq) {
            System.out.printf("%d ", a);
        }
        System.out.println();
        for (int a: rq) {
            System.out.printf("%d ", a);
        }
        System.out.println();

        System.out.printf("Is empty: %b \n", rq.isEmpty());
        System.out.printf("size: %d \n", rq.size());
        System.out.println();
        
        System.out.println("Removing...");
        rq.dequeue();
        for (int a: rq) {
            System.out.printf("%d ", a);
        }
        System.out.println();

        rq.dequeue();
        for (int a: rq) {
            System.out.printf("%d ", a);
        }
        System.out.println();

        rq.dequeue();
        for (int a: rq) {
            System.out.printf("%d ", a);
        }
        System.out.println();

        rq.dequeue();
        for (int a: rq) {
            System.out.printf("%d ", a);
        } 
        System.out.println();
    }
}