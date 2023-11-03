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
    private static int boardCounter;
    private static int lDiagCounter;
    private static int rDiagCounter;
    private static int doubleLoopCounter;

    public static void main (String [] args){
        //Runtime performed here
        Scanner scan = new Scanner (System.in);
        System.out.println("-------- N Queens Puzzle Solver --------");
//Debug
        initialSetup(getInput(scan));
        //initialSetup(4);
        //globalBoard = new int [] {0, 1, 2, 3};
        //boardLdiag = createLDiag(4,globalBoard);
        //boardRdiag = createRDiag(4,globalBoard);
//End debug
        System.out.println("Initial Values:" + Arrays.toString(globalBoard));
        //System.out.println("L Diag " + Arrays.toString(boardLdiag));
        //System.out.println("R Diag " + Arrays.toString(boardRdiag));
        //System.out.println("Number of Collisions: " + collisionReport(boardLdiag, boardRdiag) + "\nInitial Board:");
        //printBoard(globalBoard);
        solveIterativeRepair();
        //System.out.println("Final Values:" + Arrays.toString(globalBoard));
        //System.out.println("L Diag " + Arrays.toString(boardLdiag));
        //System.out.println("R Diag " + Arrays.toString(boardRdiag));
        //System.out.println("Number of Collisions: " + collisionReport(boardLdiag, boardRdiag) + "\nFinal Board:");
        //printBoard(globalBoard);
        System.out.println("Counters: " + "\nBoard Counter: " + boardCounter + "\nlDiag Counter: " + lDiagCounter + 
                "\nrDiag Counter: " + rDiagCounter + "\nSwap Counter: " + swapCounter + "\nLoop Counter: " + loopCounter +
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

    //function that creates initial board states, only affects global board. Only used for initialization and resets
    private static void initialSetup(int n){
        globalN = n;
        globalBoard = createBoard(n);
        boardLdiag = createLDiag(globalN, globalBoard);
        boardRdiag = createRDiag(globalN, globalBoard);
    }

    //Creates an array of n length to represent the board. Can be used for global board or any new objects
    //board size must be greater than 3
    public static int [] createBoard(int n){
        //Since there can only be one queen per row and column, 
        //an array is initialized with each index representing a row and each value representing a column
        //These together provide the coordinate position for each queen
        int [] board = new int[n];
        //fill the array with column values
        for (int i = 0; i < n; i++){
            board[i] = i;
            boardCounter++; //Used for empirical analysis
        }
        //Utilize Durstenfeld Shuffle to randomize initial board state
        int index;
        int temp;
        for (int i = n; i > 1; i--){
            index = rnd.nextInt(i);
            temp = board[i - 1];
            board[i - 1] = board[index];
            board[index] = temp;
            boardCounter++; //Used for empirical analysis
        }
        return board;
        
        
    }

    //Prints the input board representing empty spaces as '.' and filled spaces with 'Q'
    public static void printBoard(int [] board){
        char [] tempArray = new char[board.length];
        Arrays.fill(tempArray, 'n');
        for (int i = 0; i < board.length; i++){
            tempArray[board[i]] = 'Q';
            System.out.println(Arrays.toString(tempArray));
            tempArray[board[i]] = 'n';
        }
    }

    public static int calcLdiag(int [] board, int index){
        //column position + (n-1) - row position
        return board[index] + ((globalN - 1) - index);
    }
    public static int calcRdiag(int [] board, int index){
        //column position +  row position
        return board[index] + index;
    }

    //Create an array to represent the diagonal positions, one for rightward diags and one for leftward
    //each diagonal is represented by the corresponding index, labeled 0 - (2n - 1)
    //Diagonal 0 starts at row n col 0 (bottom left)
    //Can be used for global board or any new objects
    public static int[] createLDiag(int n, int [] board){
        int diagIndex;
        int [] lDiag = new int [2*n - 1];
        for (int i = 0; i < n; i++){
            diagIndex = calcLdiag(board, i); //determine which diag it belongs to by adding the opposite end row number and column number (since 0 diag starts at bottom left)
            lDiag[diagIndex]++;
            lDiagCounter++; //Used for empirical analysis
        }
        return lDiag;
    }
    //Diagonal 0 starts at row 0 col 0 (top left)
    public static int[] createRDiag(int n, int [] board){
        int [] rDiag = new int [2*n - 1];
        int diagIndex;
        for (int i = 0; i < n; i++){
            diagIndex = calcRdiag(board, i); //determine which diag it belongs to by adding row number and column number (0 diag starts at top left)
            rDiag[diagIndex]++;
            rDiagCounter++; //Used for empirical analysis
        }
        return rDiag;
    }
    
    

    //Calculates the number of collisions using two diagonal arrays as an input
    public static int collisionReport(int [] lDiagArr, int [] rDiagArr){
        int lSum = 0;
        int rSum = 0;
        //loops through each value and calculates number of queens - 1 for all diagonals containing more than 1 queen
        //in order to calculate number of collisions. Stores that value and continues.
        for (int i = 0; i < 2*globalN - 1; i++){
            if(lDiagArr[i] > 1){
                lSum = lSum + lDiagArr[i] - 1;
                rSum = rSum + rDiagArr[i] - 1;
            }
        }
        return lSum + rSum;
    }
    
    //checks if a given queen at index has diagonal collisions along global ldiags and rdiags for global board
    //Utilizes the board and diagonals the queen is on, as well as the board row index of the given queen
    public static int diagCollisions(int index){
        int lDiaIndex = calcLdiag(globalBoard, index); //calculates the left diagonal index of the queen
        int rDiaIndex = calcRdiag(globalBoard, index); //calculates the right diagonal index of the queen
        int lCollisions = boardLdiag[lDiaIndex] - 1; //Uses those indicies to find the number of collisions in each diagonal direction
        int rCollisions = boardRdiag[rDiaIndex] - 1; 
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
            //Do while loop ensures all possible collision minimization moves have been performed
            //continues looping until double for loop cannot perform any more swaps
            do {
                swapPerformed = 0;
                for(int i = 0; i < globalN - 1; i++){  //iterates through every row and for every row, checks it against the subsequent rows
                    loopCounter++; //Used for empirical analysis
                    for(int j = i + 1; j < globalN; j++){ //Prevents any duplicate checks
                        diagColi = diagCollisions(i);
                        diagColj = diagCollisions(j);
                        doubleLoopCounter++; //Used for empirical analysis
                        if (diagColi != 0 || diagColj != 0){ //checks that at least one of the queens has a collision worth resolving
                            temp = globalBoard.clone(); //ensure temp doesn't diverge from board
                            tempLdiag = boardLdiag.clone();
                            tempRdiag = boardRdiag.clone();
                            temp = swap(temp, i, j); //creates a temporary board to perform the swap and evaluate
                            //only really need to check the diagonals and indicies affeceted by i and j swap
                            tempLdiag = swapLdiag(tempLdiag, temp, i, j);
                            tempRdiag = swapRdiag(tempRdiag, temp, i, j);
                            afterSwap = collisionReport(tempLdiag, tempRdiag);
                            beforeSwap = collisionReport(boardLdiag, boardRdiag);
                            collisionCounter++; //Used for empirical analysis
                            if (afterSwap < beforeSwap){  //if the swapped temp board is better, assign it to board
                                globalBoard = temp.clone(); //perform swap on board
                                boardLdiag = tempLdiag.clone();  //update the diagonals
                                boardRdiag = tempRdiag.clone();
                                swapPerformed++;  //record swap count
                                swapCounter++; //Used for empirical analysis
                            }
                        }

                    }
                }
            } while (swapPerformed != 0);  //if no swaps have been performed, then there are no more possible moves and the loop ends
            if(collisionReport(boardLdiag, boardRdiag) > 0){ //checks that there are no remaining collisions
                //System.out.println("Current Board");
                //printBoard(globalBoard);
                //System.out.println("Board Resetting...");
                initialSetup(globalN); //if there are collisions, resets the board and solves again
                solveIterativeRepair();
                resetCounter++; //Used for empirical analysis
            }
        }   
    }
}