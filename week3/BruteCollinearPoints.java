import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {    // finds all line segments containing 4 points
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
        for (int i = 0; i < clone.length - 3; i++) {
            for (int j = i+1; j < clone.length - 2; j++) {
                for (int k = j+1; k < clone.length - 1; k++) {
                    for (int m = k+1; m < clone.length; m++) {
                        if (clone[i].slopeTo(clone[j]) == clone[i].slopeTo(clone[k]) && 
                            clone[i].slopeTo(clone[j]) == clone[i].slopeTo(clone[m])) {
                            res.add(new LineSegment(clone[i], clone[m]));
                        }
                    }
                }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}