/*
 * For Unit 9 - Parallel Processing in Java of CSCI 112
 * Author: Huajing Lin
 * Last edited: 4/08/2017
 */
package parallelprocessingproject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SumOfThreadPool {

    // the method invokes thread pool to sum array
    public static long sumArray(long[] array, int processes) {
        //create a long type variable for result
        long sum = 0;

        //Divide the array into multiple tasks
        int tasks = Globals.dataSize / Globals.sequentalThreshold;

        //create a long type array for results of tasks.
        long[] result = new long[tasks];

        //creating a pool threads by parameter processes
        ExecutorService executor = Executors.newFixedThreadPool(processes);
        for (int i = 0; i < tasks; i++) {
            //create Worker for each task
            Runnable worker = new WorkerThread(array, Globals.sequentalThreshold * i,
                    Globals.sequentalThreshold * (i + 1), result, i);
            executor.execute(worker);//calling execute method of ExecutorService  
        }
        executor.shutdown();
        
        //Waiting all thread finish
        while (!executor.isTerminated()) {
        }
        
        //sum all of result of tasks
        for (int i = 0; i < tasks; i++) {
            sum += result[i];
        }
        return sum;
    }  // end sumArray()

}// end class
