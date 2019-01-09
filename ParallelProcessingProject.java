/*
 * For Unit 9 - Parallel Processing in Java of CSCI 112
 * Author: Huajing Lin
 * Last edited: 4/08/2017
 */
package parallelprocessingproject;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ParallelProcessingProject {

    //Thread run method
    public static void StartCompare() {
                
        // variable to hold the sum of the values in the array
        long sum = 0;
        int processes = 0;
        //variable to calculate the time of sum
        long startTime = 0;
        long endTime = 0;
        long duration = 0;
        double spendTime = 0;
        
        // declare a long array and load it with random values
        long[] myArray = new long[Globals.dataSize];
        for (int i = 0; i < myArray.length; ++i) 
                myArray[i] = (long) (Math.random() * 100 + 1);
                
        /*/loop three paralel Processings
         * 0: Fork Join Pool
         * 1: Thread Pool
         * 2: General multi-thread
         */
        for (int i = 0; i < 3; i++) {
            switch (i) {
                case 0:
                    System.out.println("================ Fork Join Pool ======================");
                    break;
                case 1:
                    System.out.println("================ Thread Pool =========================");
                    break;
                 case 2:
                    System.out.println("================ General Multi-Thread ================");
                    break;
                default:
                    break;
            }// end switch
            
            //loop different thread number: 2,4,8,16,32,64
            for (int pow = 1; pow < 7; pow++) {

                //use Math method to get thread number save to processes variable
                processes = (int) Math.pow(2, pow);
                System.out.printf("No.%d - The number of thread: %d\n", i, processes);

                // get the start time in nanoseconds
                startTime = System.nanoTime();

                // sum the array
                switch (i) {
                    case 0:     //Fork Join Pool
                        sum = SumOfForkJoinPool.sumArray(myArray, processes);
                        break;
                    case 1:     //Thread Pool
                        sum = SumOfThreadPool.sumArray(myArray, processes);
                        break;
                    case 2: {   //general multi-thread
                        try {
                            sum = SumOfThreads.sumArray(myArray, processes);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ParallelProcessingProject.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                    default:
                        break;
                }// end switch                

                // get the end time in nanoseconds
                endTime = System.nanoTime();

                // calculate elapsed time in nanoseconds
                duration = endTime - startTime;

                // print the sum of the array
                System.out.printf("No.%d - The sum of the values in the array is: %-,12d%n",i, sum);

                // print the elapsed time in seconds   (nanaoseconds/ 1 billion)
                spendTime = (double) duration / 1.0e+09;
                System.out.printf("No.%d - The algorithm took %12.8f seconds.%n%n",i, spendTime);
            }
        }
        System.out.println("The comparison is complete.");
    }// end run()
    
    public static void main(String[] args) {
        StartCompare();
    }// end main()

}// end class
