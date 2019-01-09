/*
 * For Unit 9 - Parallel Processing in Java of CSCI 112
 * Author: Huajing Lin
 * Last edited: 4/08/2017
 */
package parallelprocessingproject;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class SumOfForkJoinPool extends RecursiveTask<Long> {
    private int low;        // low (left) end of dataset
    private int high;       // high (right end of dataset
    private long[] array;   // need to summulate
            
    private static ForkJoinPool fjPool; // ForkJoinPool object variable
    
    SumOfForkJoinPool(long[] arr, int lo, int hi) {
        array = arr;
        low = lo;
        high = hi;
    } // end SumOfForkJoinPool constructor
    
    // the compute method is the hybrid summation algorithm
    @Override
    protected Long compute() {

        // if below threshold, computer iterative sum 
        if (high - low < Globals.sequentalThreshold) {
            long sum = 0;
            // place add a random value to the array and add it to the sum
            for (int i = low; i < high; ++i) {
                sum = sum + array[i];
                // sleep for 10 milliseconds to delay operation
                try {
                    Thread.sleep(Globals.sleepTime);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                } // end try catch
                
            }  //end for
            return sum;
        } // end if
        
        // else perform recursion 
        else {
            
            // find midpoint
            int mid = low + (high - low) / 2;
            // find sum of left half
            SumOfForkJoinPool left = new SumOfForkJoinPool(array, low, mid);
            // find sum of left half
            SumOfForkJoinPool right = new SumOfForkJoinPool(array, mid, high);
            
            //separate into different processes, then join results
            left.fork();
            long rightAns = right.compute();
            long leftAns = left.join();
            return leftAns + rightAns;
        } // end else
    } // end  compute()
    
    // the sumArray method invokes processes from the pool of processes
    public static long sumArray(long[] array, int processes) {
        //create fjPool by using the maximum number of processes;
        fjPool = new ForkJoinPool(processes);
        return fjPool.invoke(new SumOfForkJoinPool(array, 0, array.length));
    }  // end sumArray()
}//end class
