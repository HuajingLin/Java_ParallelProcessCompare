/*
 * For Unit 9 - Parallel Processing in Java of CSCI 112
 * Author: Huajing Lin
 * Last edited: 4/08/2017
 */
package parallelprocessingproject;

public class SumOfThreads {

    //this method create multi-thread to sum array
    public static long sumArray(long[] array, int processes) throws InterruptedException {
        //create a long type variable for result
        long sum = 0;

        //Divide the array into multiple subarray
        int iSize = Globals.dataSize / processes;
        
        //create a long type array for results of subarray.
        long[] result = new long[processes];
        
        //create a worker thread for each subarray
        WorkerThread[] workers = new WorkerThread[processes];
        
        //create some variable for computing of dividing array
        int newSize, iRest, low, high;
        
        // instantiate and start threads of processes variable
        for (int i = 0; i < processes; i++) {
            if (processes < 32) {
                workers[i] = new WorkerThread(array, iSize * i, iSize * (i + 1), result, i);
            }
            /*
            When the number of threads is 32 and 64, because the array can not 
            equally distributed to each of the threads, I do some calculations 
            to let them as equal as possible.
            */
            else{
                newSize = iSize + 1;
                if (i < 16) {
                    workers[i] = new WorkerThread(array, newSize * i, newSize * (i + 1), result, i);
                } else {
                    iRest = newSize * 16;
                    low = iRest + iSize * (i - 16);
                    high = iRest + iSize * (i - 15);
                    //System.out.printf("test: i:%d, restdata:%d, d1:%d, d2:%d\n",i, restData,low,high);
                    workers[i] = new WorkerThread(array, low, high, result, i);
                }
            }
            workers[i].start();
        }

        //waiting for all threads finish
        for (int i = 0; i < processes; i++) {
            workers[i].join();
        }

        //sum all result of subarray
        for (int i = 0; i < processes; i++) {
            sum += result[i];
        }

        return sum;
    } // end start()

}// end class
