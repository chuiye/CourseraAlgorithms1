import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] opened;
    private WeightedQuickUnionUF uf;
    private int n;
    private int top;
    private int bottom;
    private int nOpen;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        this.opened = new boolean[n][n];  // include virtual top and virtual bottom
        this.uf = new WeightedQuickUnionUF(n * n + 2); 
        this.n = n;
        this.top = 0;
        this.bottom = n*n+1;
        this.nOpen = 0;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > n || col < 0 || col > n) {
            throw new IndexOutOfBoundsException("row or col value is out of bound");
        }
        if (!isOpen(row, col)) {
            opened[row-1][col-1] = true;
            if (row == 1) {
                uf.union(top, getUFIndex(row, col));
            } 
            if (row == n) {
                uf.union(bottom, getUFIndex(row, col));
            }
            if (row > 1 && isOpen(row-1, col)) {
                uf.union(getUFIndex(row-1, col), getUFIndex(row, col));
            }
            if (row < n && isOpen(row+1, col)) {
                uf.union(getUFIndex(row+1, col), getUFIndex(row, col));
            }
            if (col > 1 && isOpen(row, col-1)) {
                uf.union(getUFIndex(row, col-1), getUFIndex(row, col));
            }
            if (col < n && isOpen(row, col+1)) {
                uf.union(getUFIndex(row, col+1), getUFIndex(row, col));
            }
            nOpen++;
        }
    }

    private int getUFIndex(int row, int col) {
        return n * (row-1) + col;
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n || col < 0 || col > n) {
            throw new IndexOutOfBoundsException("row or col value is out of bound");
        }
        return opened[row-1][col-1];
    } 

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > n || col < 0 || col > n) {
            throw new IndexOutOfBoundsException("row or col value is out of bound");
        }
        return uf.connected(top, getUFIndex(row, col));
    }

    // number of open sites
    public int numberOfOpenSites() {
        return nOpen;
    }

    // does the system percolate?      
    public boolean percolates() {
        return uf.connected(top, bottom);
    }              

    // test client (optional)
    public static void main(String[] args) {
        int n = 3;
        Percolation p = new Percolation(n);

        System.out.printf("is Percolate: %b \n", p.percolates());
        System.out.println("open: ");
        p.open(1, 3);
        p.open(2, 3);
        p.open(3, 3);
        p.open(3, 1);
        p.open(3, 2);
        System.out.printf("is (3, 1) full: %b \n", p.isFull(3, 1));
        System.out.println("entry values: ");
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                System.out.printf("%b ", p.isOpen(i, j));
            }
            System.out.println();
        }
        System.out.printf("is Percolate: %b \n", p.percolates());
        System.out.printf("number of open sites: %d \n", p.numberOfOpenSites());
    }

}