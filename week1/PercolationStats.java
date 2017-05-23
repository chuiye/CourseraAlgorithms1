import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] threshold;
    private int trials;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials must be positive");
        }
        this.trials = trials;
        threshold = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            int row = StdRandom.uniform(1, n+1);
            int col = StdRandom.uniform(1, n+1);
            while (!p.percolates()) {
                while (p.isOpen(row, col)) {
                    row = StdRandom.uniform(1, n+1);
                    col = StdRandom.uniform(1, n+1);
                }
                p.open(row, col);
            }
            threshold[i] = 1.0 * p.numberOfOpenSites() / (n*n);
        }
    }    

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(threshold);
    }  

    // sample standard deviation of percolation threshold            
    public double stddev() {
        return StdStats.stddev(threshold);
    }                       

    // low  endpoint of 95% confidence interval
    // high endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean()-1.96*this.stddev()/Math.sqrt(this.trials);
    }                 
    public double confidenceHi() {
        return this.mean()+1.96*this.stddev()/Math.sqrt(this.trials);
    }                 

    // test client (described below)
    public static void main(String[] args) {
        PercolationStats experiments = new PercolationStats(-3, 100000);
        System.out.printf("mean                    = %f \n", experiments.mean());
        System.out.printf("stddev                  = %f \n", experiments.stddev());
        System.out.printf("95%% confidence interval = [%f, %f] \n", experiments.confidenceLo(), experiments.confidenceHi());
    }     
}