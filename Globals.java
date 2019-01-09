/*
 * For Unit 9 - Parallel Processing in Java of CSCI 112
 * Author: Huajing Lin
 * Last edited: 4/08/2017
 */
package parallelprocessingproject;

public class Globals {
    // the size of data which need to sum
    static int dataSize = 10000;   
    
    //set constant to switch to iterative sequential processes at n = 100
    static int sequentalThreshold = 100;
    
    //sleep for 1 milliseconds in each task
    static int sleepTime = 1;
}
