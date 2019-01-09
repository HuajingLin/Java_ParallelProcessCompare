/*
 * For Unit 9 - Parallel Processing in Java of CSCI 112
 * Author: Huajing Lin
 * Last edited: 4/08/2017
 */
package parallelprocessingproject;

public class WorkerThread implements Runnable {

    private int low;        // low (left) end of dataset
    private int high;       // high (right end of dataset
    private long[] array;   // need to summulate
    private long[] result;  // save result of summing
    private int index;      // save the index of threads
    private Thread t;       // The thread variable
        
    WorkerThread(long[] arr, int lo, int hi, long[] re, int ind) {
        array = arr;
        low = lo;
        high = hi;
        result = re;
        index = ind;
        t = null;
    } // end SumOfForkJoinPool constructor

    @Override
    public void run() {
        long sum = 0;
        // sum the array from low to high
        for (int i = low; i < high; i++) {
            sum = sum + array[i];
            // sleep for Globals.sleepTime milliseconds to delay operation
            try {
                Thread.sleep(Globals.sleepTime);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            } // end try catch

        }  //end for
        result[index] = sum;
    }// end run   
    
    //to start a thread
    public void start() {
        t = new Thread(this);
        t.start();
    } // end start()
    
    //to wait the thread finish
    public void join() throws InterruptedException{
        t.join();
    }// end joing
    
}// end class
