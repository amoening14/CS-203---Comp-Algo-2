//Alex Moening
//CS203 - Project 1
//Giusseppe Turini
//10-31-2023

//Main Executable
package n_queens_amoening;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public final class SolverNQueens {
    private static int [] globalBoard;
    private static int globalN;  //assigned value when board is created, used for all other creation methods
    private static Random rnd = new Random();
    private static int [] boardLdiag;
    private static int [] boardRdiag;
    private static int resetCounter;
    private static int swapCounter;
    private static int loopCounter;
    private static int collisionCounter;
    private static int doubleLoopCounter;

    public static void main (String [] args){
        //Runtime performed here
        Scanner scan = new Scanner (System.in);
        System.out.println("-------- N Queens Puzzle Solver --------");
//Debug
        initialSetup(getInput(scan));
        //initialSetup(6);
        //globalBoard = new int [] {2, 5, 3, 4, 0, 1};
        //boardLdiag = new int [] {0, 2, 0, 0, 0, 0, 2, 1, 0, 1, 0};
        //boardRdiag = new int [] {0, 0, 1, 0, 1, 1, 2, 1, 0, 0, 0};
//End debug
        System.out.println("Initial Values:" + Arrays.toString(globalBoard));
        System.out.println("L Diag " + Arrays.toString(boardLdiag));
        System.out.println("R Diag " + Arrays.toString(boardRdiag));
        System.out.println("Number of Collisions: " + collisionReport(boardLdiag, boardRdiag) + "\nInitial Board:");
        printBoard(globalBoard);
        solveIterativeRepair();
        System.out.println("Final Values:" + Arrays.toString(globalBoard));
        System.out.println("L Diag " + Arrays.toString(boardLdiag));
        System.out.println("R Diag " + Arrays.toString(boardRdiag));
        System.out.println("Number of Collisions: " + collisionReport(boardLdiag, boardRdiag) + "\nFinal Board:");
        printBoard(globalBoard);
        System.out.println("Counters: " + "\nSwap Counter: " + swapCounter + "\nLoop Counter: " + loopCounter +
                "\nDouble Loop Counter: " + doubleLoopCounter + "\nCollision Counter: " + collisionCounter + 
                "\nReset Counter: " + resetCounter);
    }

    public static int getInput(Scanner scan){
        System.out.println("Please enter Number of Queens:");
        int temp = 0;
        while(temp < 4){
            try{
                temp = scan.nextInt();
                if(temp < 4){
                    throw new Exception ("Input size too small");
                } else {
                    return temp;
                }
            } catch (InputMismatchException m){
                System.out.println("Input type incorrect. Please enter an integer.");
                scan.nextLine();
            }catch (Exception e){
                System.out.println("Input size too small. Please enter size 4 or larger.");
            }
        }
        return temp;
    }

    //Creates an array of n length to represent the board, represented globally
    //board size must be greater than 3
    private static void initialSetup(int n){
        //create blobal board and global size
        globalN = n;
        globalBoard = new int[n];
        //fill the array with row values
        for (int i = 0; i < n; i++){
            globalBoard[i] = i;
        }
        //Utilize Durstenfeld Shuffle to randomize initial board state
        int index;
        int temp;
        for (int i = n; i > 1; i--){
            index = rnd.nextInt(i);
            temp = globalBoard[i - 1];
            globalBoard[i - 1] = globalBoard[index];
            globalBoard[index] = temp;
        }

        //Initialize diagonal boards 
        boardLdiag = new int [2*n - 1];
        boardRdiag =  new int [2*n - 1];

        //fill diagonal boards with num queens for each diagonal
        for (int i = 0; i < n; i++){
            boardLdiag[calcLdiag(globalBoard, i)]++;
            boardRdiag[calcRdiag(globalBoard, i)]++;
        }
        
    }

    //Prints the input board representing empty spaces as '.' and filled spaces with 'Q'
    public static void printBoard(int [] board){
        String [] tempArray = new String[globalN];
        Arrays.fill(tempArray, " .");
        for (int i = 0; i < globalN; i++){
            tempArray[board[i]] = " Q";
            System.out.println(Arrays.toString(tempArray));
            tempArray[board[i]] = " .";
        }
    }

    public static int calcLdiag(int [] board, int index){
        //column position + ((n-1) - row position); 0 starts at row n column 0
        return board[index] + ((globalN - 1) - index);
    }
    public static int calcRdiag(int [] board, int index){
        //column position +  row position; 0 starts at row 0 column 0
        return board[index] + index;
    }
    
    //Calculates the total number of collisions for both diagonal arrays
    public static int collisionReport(int [] lDiagArr, int [] rDiagArr){
        int lSum = 0;
        int rSum = 0;
        //loops through each value and calculates number of collisions as queens - 1 for all diagonals containing more than 1 queen
        for (int i = 0; i < 2*globalN - 1; i++){
            if(lDiagArr[i] > 1){
                lSum += lDiagArr[i] - 1;
            }
            if (rDiagArr[i] > 1){
                rSum += (rDiagArr[i] - 1);
            }
        }
        return lSum + rSum;
    }
    
    //checks if a given queen at index has diagonal collisions along global ldiags and rdiags for global board
    //Utilizes the board and diagonals the queen is on, as well as the board row index of the given queen
    public static int diagCollisions(int index){
        int lCollisions = boardLdiag[calcLdiag(globalBoard, index)] - 1; //calculates and uses diagonal indicies to find the number of collisions
        int rCollisions = boardRdiag[calcRdiag(globalBoard, index)] - 1; 
        return lCollisions + rCollisions;
    }

    //swaps the queen in row i with the queen in row j
    public static int [] swap(int [] board, int i, int j){
        int temp1 = board[i];
        int temp2 = board[j];
        board[i] = temp2;
        board[j] = temp1;
        return board;
    }
    //move diagonal values to update new queen positions
    public static int [] swapLdiag(int [] lDiag, int [] tempBoard, int i, int j){
        lDiag[calcLdiag(globalBoard, i)]--; //decrement current diagonal position of i and j
        lDiag[calcLdiag(globalBoard, j)]--;
        lDiag[calcLdiag(tempBoard, i)]++; //increment swapped diagonal position of i and j
        lDiag[calcLdiag(tempBoard, j)]++;
        return lDiag;
    }
    public static int [] swapRdiag(int [] rDiag, int [] tempBoard, int i, int j){
        rDiag[calcRdiag(globalBoard, i)]--; //decrement current diagonal position of i and j
        rDiag[calcRdiag(globalBoard, j)]--;
        rDiag[calcRdiag(tempBoard, i)]++; //increment swapped diagonal position of i and j
        rDiag[calcRdiag(tempBoard, j)]++;
        return rDiag;
    }


//if/when it gets stuck on a local minimum, can simply reset the board and rerun. If that is the method, need to acknowledge in the 
//analysis that the worst case could potentially run infinitely since there would be the possibility of it generating the same
//board over and over and running into the same conflicting point. Also, don't forget to include information about the average case
    
    //solves the board using iterative repair method
    //If it gets stuck with no more possible moves but still has collisions, resets the board and recursively solves the new board
    public static void solveIterativeRepair(){
        if (collisionReport(boardLdiag, boardRdiag) != 0){ //ensure that solver needs to run
            int swapPerformed;
            int diagColi;
            int diagColj;
            int afterSwap;
            int beforeSwap;
            int [] temp;
            int [] tempLdiag;
            int [] tempRdiag;
            boolean repeat = true;
            //Do while loop ensures all possible collision minimization moves have been performed
            //continues looping until double for loop cannot perform any more swaps
            do {
                swapPerformed = 0;

                //iterates through every row and for every row, checks it against the subsequent rows
                for(int i = 0; i < globalN - 1; i++){  
                    loopCounter++; //Used for empirical analysis
                   
                    for(int j = i + 1; j < globalN; j++){ 
                        diagColi = diagCollisions(i);
                        diagColj = diagCollisions(j);
                        doubleLoopCounter++; //Used for empirical analysis
                        if (diagColi != 0 || diagColj != 0){ //checks that at least one of the queens has a collision worth resolving
                            //create temporary values used to evaluate swap
                            temp = globalBoard.clone();
                            tempLdiag = boardLdiag.clone();
                            tempRdiag = boardRdiag.clone();
                            temp = swap(temp, i, j);
                            tempLdiag = swapLdiag(tempLdiag, temp, i, j);
                            tempRdiag = swapRdiag(tempRdiag, temp, i, j);
                            afterSwap = collisionReport(tempLdiag, tempRdiag);
                            beforeSwap = collisionReport(boardLdiag, boardRdiag);
                            collisionCounter++; //Used for empirical analysis
                            if (afterSwap < beforeSwap){  //if the swapped temp board is better, assign it to board
                                globalBoard = temp.clone(); //perform swap on board and diagonals
                                boardLdiag = tempLdiag.clone();
                                boardRdiag = tempRdiag.clone();
                                swapPerformed++;  //record swap count
                                swapCounter++; //Used for empirical analysis
                            }
                        }

                    }
                }
                //checks that all possible moves have been performed, and resets the board and repeats if collisions still exist,
                //or exits while loop if no more collisions
                //If no swaps can be performed and collisions still exist, resets board and repeats solver
                if(collisionReport(boardLdiag, boardRdiag) > 0 && swapPerformed == 0){
                    initialSetup(globalN);
                    System.out.println("Resetting...");
                    resetCounter++; //Used for empirical analysis
                //If no collisions exist, exits loop
                } else if (collisionReport(boardLdiag, boardRdiag) == 0){
                    repeat = false;
                } //else repeat to perform all possible moves for the current board permutation
            } while (repeat);  //if no swaps have been performed, then there are no more possible moves and the loop ends
        }   
    }
}