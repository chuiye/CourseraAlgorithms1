import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        String[] input = StdIn.readAllStrings();
        for (String str: input) {
            rq.enqueue(str);
        }
        Iterator<String> iter = rq.iterator();
        int i = 0;
        while (iter.hasNext() && i < k) {
            String s = iter.next();
            StdOut.println(s);
            i++;
        }
    }
}