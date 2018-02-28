import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] tr;
    public static final double CONFIDENCE_95 = 1.96;
    
    public PercolationStats(int n, int trials) {
        // int n = Integer.parseInt(sz);
        // int trials = Integer.parseInt(tries);

        if (n <= 0 || trials <= 0) throw new java.lang.IllegalArgumentException();

        tr = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);

            while (!p.percolates()) {
                p.open(StdRandom.uniform(1, n), StdRandom.uniform(1, n));
            }

            double opens = p.numberOfOpenSites();

            tr[i] = opens / (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(tr);
    }

    public double stddev() {
        return StdStats.stddev(tr);
    }

    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(tr.length));
    }

    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(tr.length));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats p = new PercolationStats(n, trials);

        System.out.printf("mean = %14f\n", p.mean());
        System.out.printf("standard deviation = %14f\n", p.stddev());
        System.out.printf("95%% confidence interval = [%14f", p.confidenceLo());
        System.out.printf("%14f]\n", p.confidenceHi());
    }
}