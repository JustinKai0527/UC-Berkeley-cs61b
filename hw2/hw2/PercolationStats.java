package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    public double time;
    public double[] arr;
    public PercolationStats(int N, int T, PercolationFactory pf){   // perform T independent experiments on an N-by-N grid
        // if(N <= 0 || T <= 0)  throw new java.lang.IllegalArgumentException();
        
        time = T;
        arr = new double[N];

        for(int i=0; i<T; i++){

            Percolation test = pf.make(N);
            
            while(!test.percolates()){
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                test.open(row,col);
            }
            
            arr[i] = test.numberOfOpenSites()/N*N;
        }
    }

    public double mean(){                                           // sample mean of percolation threshold
        return StdStats.mean(arr);
    }

    public double stddev(){                                         // sample standard deviation of percolation threshold
       return StdStats.stddev();
    }
    
    public double confidenceLow(){                                  // low endpoint of 95% confidence interval
      return mean() - 1.96 * stddev() / Math.sqrt(time);
    }
    
    public double confidenceHigh(){                                 // high endpoint of 95% confidence interval
        return mean() + 1.96 * stddev() / Math.sqrt(time);
    }
}
