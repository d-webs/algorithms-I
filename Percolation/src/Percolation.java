import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] id;
    private final WeightedQuickUnionUF connections;
    private int open;

    public Percolation(int n) {
        if (n <= 0) throw new java.lang.IllegalArgumentException();

        id = new boolean[n][n];
        connections = new WeightedQuickUnionUF((n * n) + 2);
        open = 0;

        for (int i = 0; i < (n * n); i++) {
            int row = i / n;
            int col = i % n;
            id[row][col] = false;
        }	
    }

    public void open(int row, int col) {
        validateInput(row, col);

        if (isOpen(row, col)) return;

        open += 1; 

        id[row - 1][col - 1] = true;

        if (row - 1 == 0) {
            int p = col - 1;
            int q = id.length;
            connections.union(p, q);
        }

        if (row - 1 == id.length - 1) {
            int p = (row - 1) * id.length + col - 1;
            int q = id.length + 1;
            connections.union(p, q);
        }

        if (row - 1 > 0 && isOpen(row - 1, col)) {
            int p = (row - 1) * id.length + col - 1;
            int q = (row - 2) * id.length + col - 1;
            connections.union(p, q);
        }

        if (row - 1 < id.length - 1 && isOpen(row + 1, col)) {
            int p = (row - 1) * id.length + col - 1;
            int q = (row) * id.length + col - 1;
            connections.union(p, q);
        }

        if (col - 1 > 0 && isOpen(row, col - 1)) {
            int p = (row - 1) * id.length + col - 1;
            int q = (row - 1) * id.length + col - 2;
            connections.union(p, q);
        }

        if (col - 1 < id.length - 1 && isOpen(row, col + 1)) {
            int p = (row - 1) * id.length + col - 1;
            int q = (row - 1) * id.length + col - 1;
            connections.union(p, q);
        }
    }

    public boolean isOpen(int row, int col) {
        validateInput(row, col);

        return id[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        validateInput(row, col);
        int p = (row - 1) * id.length + col - 1;
        int q = id.length;
        return connections.connected(p, q);
    }

    public int numberOfOpenSites() {
        return open;
    }

    public boolean percolates() {
        return connections.connected(id.length, id.length + 1);
    }

    private void validateInput(int row, int col) {
        if (outOfRange(row, col)) throw new java.lang.IllegalArgumentException();
    }

    private boolean outOfRange(int row, int col) {
        return row <= 0 || row > id.length || col <= 0 || col > id.length;
    }
}