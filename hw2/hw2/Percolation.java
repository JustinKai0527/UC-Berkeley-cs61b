package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

//example 1 : create another UF to prevent the backwash
public class Percolation {
    private int size;
    private boolean[][] arr;
    private int count = 0;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF check_back_wash;

    public Percolation(int N){                // create N-by-N grid, with all sites initially blocked

        if(N <= 0)  throw new java.lang.IllegalArgumentException("Not correct argument");

        size = N;
        arr = new boolean[size][size];
        uf = new WeightedQuickUnionUF(N*N+2);
        check_back_wash = new WeightedQuickUnionUF(N*N+1);
        count = 0;

        for(int i=1; i<=N; i++){
            uf.union(0, i);
            uf.union(N*N+1, (N*N+1)-i);
        //     check_back_wash.union(0,i);
        }                                       //don't first connect the top and the bottom
    }
    
    public void open(int row, int col){       // open the site (row, col) if it is not open already

        if(row < 0 || row >= size || col < 0 || col >= size)  throw new java.lang.IndexOutOfBoundsException();

        if(isOpen(row,col))  return;

        count++;
        arr[row][col] = true;

        if(col + 1 < size && isOpen(row, col+1)){

            uf.union(xyTo1d(row, col), xyTo1d(row, col+1));
            check_back_wash.union(xyTo1d(row, col), xyTo1d(row, col+1));
        }  
        if(col - 1 >= 0 && isOpen(row, col-1)){

            uf.union(xyTo1d(row, col), xyTo1d(row, col-1));
            check_back_wash.union(xyTo1d(row, col), xyTo1d(row, col-1));
        }
        if(row + 1 < size && isOpen(row+1, col)){

            uf.union(xyTo1d(row, col), xyTo1d(row+1, col));
            check_back_wash.union(xyTo1d(row, col), xyTo1d(row+1, col));
        }
        if(row - 1 >= 0 && isOpen(row-1, col)){

            uf.union(xyTo1d(row, col), xyTo1d(row-1, col));
            if(row - 1 == -1)  check_back_wash.union(xyTo1d(row, col), 0);
        }
    }    
    
    public boolean isOpen(int row, int col){  // is the site (row, col) open?
        if(row < 0 || row >= size || col < 0 || col >= size)  throw new java.lang.IndexOutOfBoundsException();
        return arr[row][col];
    }
    
    public boolean isFull(int row, int col){  // is the site (row, col) full?
        return check_back_wash.connected(0,xyTo1d(row,col)) && isOpen(row,col);
    }

    public int numberOfOpenSites(){           // number of open sites
        return count;
    }

    public boolean percolates(){             // does the system percolate?
        return uf.connected(0,size*size+1);
    }
    
    public static void main(String[] args){}   // use for unit testing (not required, but keep this here for the autograder)
    
    private int xyTo1d(int row, int col){
        return size * row + col + 1;
    }
}
