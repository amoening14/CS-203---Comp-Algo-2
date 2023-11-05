package n_queens_amoening;

/*William Raines
CS-203
Iterative Repair
Professor Turini
*/
import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;

public class Will_Assignment1_2 {

   public static void main(String[] args) {
      
      long start = System.nanoTime(); // empeical analysis
      
      System.out.println(Arrays.toString(solveNQueens(8)));
      
      //Empericial Analysis 
      long end = System.nanoTime();
      System.out.println("Miliseconds: " + (end - start) * 1e-6);
      
     
   }
   
   // helper function
   public static int[] solveNQueens(int n) {
      
      int[] solution;
      int counter = 0;
      
      do {
         solution = iterativeRepair(n);
         counter++;
      } while (solution == null);
      
      //System.out.println(counter);
      return solution;
   }
   
   
   
   
   // iterative repair
   private static int[] iterativeRepair(int n) {
         
      // Generate a permutation of a NxN board with N queens
      int[] board = generateBoard(n);
      
      // Diagonal attack arrays
      int[] NegativeD = new int[ (2*n)-1 ];
      int[] PositiveD = new int[ (2*n)-1 ];
      
      // Add diagonal spaces occupied with the current board permutation
      for (int i = 0; i < n; i++) {
         int positionN = negativeDiagonalPos(i, board[i], n);
         int positionP = positiveDiagonalPos(i, board[i]);
         NegativeD[positionN] += 1;
         PositiveD[positionP] += 1;
      }
      
      // Number of queen attacks
      int attacks = 0;
      
      // Check the number of attacks at each positive and negative diagonal
      for (int i = 0; i < 2*n - 1; i++) {
         // if diagonal array index is greater than one, attacks is value - 1
         // ex. 3 - 1 = 2 attacks
         attacks += NegativeD[i] > 1 ? NegativeD[i] - 1 : 0;
         attacks += PositiveD[i] > 1 ? PositiveD[i] - 1 : 0;
      }
      
      while (attacks > 0) {
      
         boolean swapDone = false;
         
         // main for-loop, swap values in permutation until successfull solution or every swap combination is tried
         for (int i = 0; i < n - 1; i++) {
               
            for (int j = i + 1; j < n; j++) {
               
               int iRow = i;
               int iCol = board[i];
               int jRow = j;
               int jCol = board[j];
               
               int iPos = positiveDiagonalPos(iRow, iCol);
               int iNeg = negativeDiagonalPos(iRow, iCol, n);
               int jPos = positiveDiagonalPos(jRow, jCol);
               int jNeg = negativeDiagonalPos(jRow, jCol, n);
               
               
               int currentAttacks = attacks; // temp attack variable for swaps if i and j swap in the board array
                  
               /* remove current attacks with current board[i] and board[j] positions*/
               // if block: special case where iNeg and jNeg diagonal's are equal and only 2, decrement by one attack
               if (iNeg == jNeg && NegativeD[iNeg] == 2) {  
                  currentAttacks -= NegativeD[iNeg] > 1 ? 1 : 0;
               } else {
                  currentAttacks -= NegativeD[iNeg] > 1 ? 1 : 0;
                  currentAttacks -= NegativeD[jNeg] > 1 ? 1 : 0;
               }
               
               // if block: special case where iPos and jPos diagonal's are equal and only 2, decrement by one attack
               if (iPos == jPos && PositiveD[iPos] == 2) {
                  currentAttacks -= PositiveD[iPos] > 1 ? 1 : 0;
               } else {
                  currentAttacks -= PositiveD[iPos] > 1 ? 1 : 0;
                  currentAttacks -= PositiveD[jPos] > 1 ? 1 : 0;
               }
               
               
               
               // get new diagonal array indexes, if board[i] is swapped with board[j]
               int iSwapPos = positiveDiagonalPos(iRow, jCol);
               int iSwapNeg = negativeDiagonalPos(iRow, jCol, n);
               int jSwapPos = positiveDiagonalPos(jRow, iCol);
               int jSwapNeg = negativeDiagonalPos(jRow, iCol, n);
              
   
               
               /* increment new attacks with swapping i and j*/
               // if block: special case where iSwapPos and jSwapPos diagonal's are equal and 0, increment by one attack
               if (iSwapNeg == jSwapNeg && NegativeD[iSwapNeg] == 0) {
                  currentAttacks += 1;
               } else {
                  currentAttacks += NegativeD[iSwapNeg] >= 1 ? 1 : 0;              
                  currentAttacks += NegativeD[jSwapNeg] >= 1 ? 1 : 0;
               }
               
               // if block: special case where iSwapNeg and jSwapNeg diagonal's are equal and 0, increment by one attack
               if (iSwapPos == jSwapPos && PositiveD[iSwapPos] == 0) {
                  currentAttacks += 1;
               } else {
                  currentAttacks += PositiveD[iSwapPos] >= 1 ? 1 : 0;
                  currentAttacks += PositiveD[jSwapPos] >= 1 ? 1 : 0;
               }
               
               
               // by swapping board[i] with board[j], attack count decreases     
               if (currentAttacks < attacks) {
                  
                  attacks = currentAttacks;
                  swapDone = true;
                  
                  // swap board
                  int temp = board[iRow];
                  board[iRow] = board[jRow];
                  board[jRow] = temp;
                  
                  // decrease diagonal arrays
                  NegativeD[iNeg]--;
                  NegativeD[jNeg]--;
                  PositiveD[iPos]--;
                  PositiveD[jPos]--;
                  
                  // increment diagonal arrays
                  NegativeD[iSwapNeg]++;
                  NegativeD[jSwapNeg]++;
                  PositiveD[iSwapPos]++;
                  PositiveD[jSwapPos]++;
                  
                  
               }
               
               // No attacks exists in permutation, finish early and exit function
               if (attacks == 0) {
                  return board;
               }
            } // end of inner for loop
         } // end of outer foot loop
         
         if (!swapDone) {
            return null;
         }  
         
      }
        
      // solution found; return board
      return board;     
   }
   
   private static int[] generateBoard(int n) {
      
      // Generate a permutation of a NxN board with N queens
      int[] board = new int[n];
      
      // Generate a helper board with values [0, 1, 2, ..., n - 1]
      ArrayList<Integer> helperBoard = new ArrayList<>();
     
      for (int i = 0; i < n; i++) {
         helperBoard.add(i);   
      }
            
      for (int i = 0; i < n; i++) {
         
         // Random value between 0 and n - i - 1, inclusive
         int random = (int) (Math.random() * (n - i));
         
         // Add random value fromo helperBoard to board at index i
         board[i] = helperBoard.get(random);
         helperBoard.remove(random);
      }
      
      return board;

   }
    
   //   \ Negative Diagonal: Row - Column + (n - 1)
   private static int negativeDiagonalPos(int row, int col, int n) {
      return row - col + n - 1;
   }
    
   //   / Positive Diagonal: Row + Column
   private static int positiveDiagonalPos(int row, int col) {
      return row + col;
   }
}
