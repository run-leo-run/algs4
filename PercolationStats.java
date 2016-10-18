/******************************************************************************
 *  Author:           leo chan
 *  Wtirren:          14/10/2016
 *  Last updated:     14/10/2016
 * 
 *  Compliation:      javac PercolationStats.java
 *  Execution:        java PercolationStats n tirals
 *
 *  complete the assignment 1 in algs course
 *
 ******************************************************************************/

import java.awt.Font;
import java.lang.Math;
import java.lang.Integer;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
public class PercolationStats{
    
    private double percolation_opensite[];
    private int siz;
    private int tri;
    private double mean;
    private double stddev;
    
    public PercolationStats(int n, int trials){
        if(n <= 0 || trials <=0) 
            throw new IllegalArgumentException("parameter wrong n & trials" + n + " " + trials); 
        
        siz = n;
        tri = trials;
        percolation_opensite = new double[trials];
        for(int i = 0; i < trials; ++i){
            Percolation perc = new Percolation(n);
            int opensite = 0;
            //StdOut.println("on trials " + i);
            while(!perc.percolates()){
                int x = StdRandom.uniform(n);
                int y = StdRandom.uniform(n);
                if(perc.isOpen(x + 1,y + 1)) ;
                else{
                    perc.open(x + 1,y + 1);
                    opensite++;
                    //StdOut.println("on opensite position " + x + " " + y + " " + opensite);                
                }                
            }
            percolation_opensite[i] = (double)opensite/(double)(n*n);
        }
        
    }
    public double mean(){
        mean = StdStats.mean(percolation_opensite);
        return mean;
    }
    public double stddev(){
        stddev = StdStats.stddev(percolation_opensite);
        return stddev;
    }
    public double confidenceLo(){
        return mean - (1.96*(stddev))/Math.sqrt(tri);
    }
    public double confidenceHi(){
        return mean + (1.96*(stddev))/Math.sqrt(tri);        
    }  
    
        
    public static void main(String[] args){      
       
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats perc_stat = new PercolationStats(n, trials);
        StdOut.println("mean                    =" + perc_stat.mean());
        StdOut.println("stddev                  =" + perc_stat.stddev());
        StdOut.println("95% confidence interval = " + perc_stat.confidenceLo() +", " + perc_stat.confidenceHi());
    }
}