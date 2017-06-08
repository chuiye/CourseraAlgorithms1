import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {    // finds all line segments containing 4 or more points
        if (points == null || points.length == 0) {
            throw new java.lang.NullPointerException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new java.lang.NullPointerException();
            }
        }
        
        Point[] clone = points.clone();
        Arrays.sort(clone);
        for (int i = 1; i < clone.length; i++) {
            if (clone[i-1].compareTo(clone[i]) == 0) {
                throw new java.lang.IllegalArgumentException();
            }
        }

        ArrayList<LineSegment> res = new ArrayList<LineSegment>();
        for (int i = 0; i < clone.length; i++) {
            Point origin = clone[i];
            Point[] temp = new Point[clone.length-(i+1)];
            for (int j = i+1; j < clone.length; j++) {
                temp[j-i-1] = clone[j];
            }
            Arrays.sort(temp, origin.slopeOrder());
            int j = 0, count = 0;
            while (j < temp.length - 1) {
                int start = j;
                while (j < temp.length-1 && origin.slopeTo(temp[j]) == origin.slopeTo(temp[j+1])) j++;
                if (j - start + 1 >= 3) {
                    res.add(new LineSegment(origin, temp[j]));               
                }
                j++;
            }
        }

        segments = res.toArray(new LineSegment[0]);
    }

    public int numberOfSegments() {        // the number of line segments
        return segments.length;
    }

    public LineSegment[] segments() {                // the line segments
        return segments.clone();
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}