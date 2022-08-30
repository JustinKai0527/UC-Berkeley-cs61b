package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    public int size;
    public boolean[][] arr;
    public int count = 0;
    WeightedQuickUnionUF uf;
    
    public Percolation(int N){                // create N-by-N grid, with all sites initially blocked
        if(N <= 0)  throw new java.lang.IllegalArgumentException("Not correct argument");
        size = N;
        arr = new boolean[size][size];
        uf = new WeightedQuickUnionUF(N*N+2);
        for(int i=1; i<N; i++){
            uf.union(0, i);
            uf.union(N*N+1, (N*N+1)-i);
        }
    }
    
    public void open(int row, int col){       // open the site (row, col) if it is not open already
        if(row < 0 || row >= size || col < 0 || col >= size)  throw new java.lang.IndexOutOfBoundsException();
        count++;
        arr[col][row] = true;
        if(col + 1 < size && isOpen(row, col+1)){
            uf.union(xyTo1d(row, col), xyTo1d(row, col+1));
        }  
        if(col - 1 >= 0 && isOpen(row, col-1)){
            uf.union(xyTo1d(row, col), xyTo1d(row, col-1));
        }
        if(row + 1 < size && isOpen(row+1, col)){
            uf.union(xyTo1d(row, col), xyTo1d(row+1, col));
        }
        if(row - 1 >= 0 && isOpen(row-1, col)){
            uf.union(xyTo1d(row, col), xyTo1d(row-1, col));
        }
    }    
    
    public boolean isOpen(int row, int col){  // is the site (row, col) open?
        if(row < 0 || row >= size || col < 0 || col >= size)  throw new java.lang.IndexOutOfBoundsException();
        return arr[col][row];
    }
    
    public boolean isFull(int row, int col){  // is the site (row, col) full?
        return uf.connect(0,xyTo1d(row,col));
    }

    public int numberOfOpenSites(){           // number of open sites
        return count;
    }

    public boolean percolates(){             // does the system percolate?
        return uf.connect(0,size*size+1);
    }
    
    public static void main(String[] args){}   // use for unit testing (not required, but keep this here for the autograder)
    
    private int xyTo1d(int row, int col){
        return size * col + row + 1;
    }
}
