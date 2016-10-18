/******************************************************************************
 *  Author:           leo chan
 *  Wtirren:          14/10/2016
 *  Last updated:     14/10/2016
 * 
 *  Compliation:      javac Percolation.java
 *  Execution:        java Percolation
 *
 *  complete the assignment 1 in algs course
 *
 ******************************************************************************/

import java.awt.Font;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation{
    
    private WeightedQuickUnionUF my_uf_full;
    private WeightedQuickUnionUF my_uf_per;
    private boolean[] arraystats;         //record the open state on position(i,j)
    private int size; 
    private int top_node;
    private int bottom_node;
    
    public Percolation(int n){
        size = n;
        if(n > 0){
            my_uf_full = new WeightedQuickUnionUF(n*n + 1);
            my_uf_per = new WeightedQuickUnionUF(n*n + 2);
            arraystats = new boolean[n*n + 1];
            top_node = 0;
            bottom_node = n*n + 1;
            
            for(int j = 1; j <= size; ++j){              
                my_uf_per.union(j,top_node);
                my_uf_per.union((size-1)*size + j,bottom_node);
            }
        }       

    }
    public void open(int i, int j){
        
        if(i > size || i < 0 || j > size || j < 0)
            throw new IndexOutOfBoundsException("parameter wrong" + i + " " + j);       
        
        if(isOpen(i, j)) return;
        else arraystats[(i-1)*size + j] = true;
        
        if( (i-1)>0 && isOpen(i-1, j)){
            my_uf_full.union((i-1)*size + j, (i-2)*size + j);
            my_uf_per.union((i-1)*size + j, (i-2)*size + j);
        }

        
        if( (i+1)<=size && isOpen(i+1,j)){
            my_uf_full.union((i-1)*size + j, (i)*size + j);
            my_uf_per.union((i-1)*size + j, (i)*size + j);            
        }

        
        if( (j-1)>0 && isOpen(i,j-1)){
            my_uf_full.union((i-1)*size + j, (i-1)*size + j-1);
            my_uf_per.union((i-1)*size + j, (i-1)*size + j-1);
        }

        
        if( (j+1)<=size && isOpen(i,j+1)){
            my_uf_full.union((i-1)*size + j, (i-1)*size + j+1); 
            my_uf_per.union((i-1)*size + j, (i-1)*size + j+1);         
        }

    }
    
    public boolean isOpen(int i, int j){
        if(i > size || i < 0 || j > size || j < 0)
            throw new IndexOutOfBoundsException("parameter wrong" + i + " " + j);
        return arraystats[(i-1)*size + j];
    }
    
    public boolean isFull(int i, int j){
        if(i > size || i < 0 || j > size || j < 0)
            throw new IndexOutOfBoundsException("parameter wrong" + i + " " + j);
        
        for(int ii = 1; ii <= size; ++ii){
            if(!isOpen(1,ii)) continue;
            if(my_uf_full.connected(ii, (i-1)*size + j)) return true;
        }
        return false;
    }
    
    public boolean percolates(){      
        return my_uf_per.connected(top_node,bottom_node);
    }  
   
}