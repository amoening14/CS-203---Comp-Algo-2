// CS-203: "Computing and Algorithms III"
// Prof. Giuseppe Turini
// Kettering University
// 2022-03-29

package closestPair2D;

// "Static" class including some simple algorithms to sort.
public final class Sorting {

   // Note: private default constructor to forbid instantiation.
   private Sorting () {}
   
   // Desc.: Algorithm (iterative) to merge 2 sorted arrays into another sorted array.
   // Input: Sorted arrays B and C (full) will be merged into sorted array A (empty, overwritten).
   // Note: Assuming arrays B and C are sorted and full, array A empty (overwritten), and A.length = B.length + C.length.
   //Modified to handle point class rather than int
   public static void MergeSortedArraysX( point[] B, point[] C, point[] A ) {
      // Init temp variables.
      int i = 0;
      int j = 0;
      int k = 0;
      // Scanning sorted arrays B and C, while inserting in A.
      while( ( i < B.length ) && ( j < C.length ) ) {
         //Modified for sorting based on x coordinate
         if( B[i].lessThanEqualToX(C[j]) ) { A[k] = B[i]; i++; }
         else { A[k] = C[j]; j++; }
         k++;
      }
      // One scan has terminated, transfer remaining sorted data in A.
      if( i == B.length ) { System.arraycopy( C, j, A, k, C.length - j ); }
      else { System.arraycopy( B, i, A, k, B.length - i ); }
   }

   // Desc.: Mergesort algorithm (divide-by-2, iterative, bottom-up) to sort input array (non-decreasing).
   // Input: An array of integers (A).
   // Note: Recursive implementation.
   //Modified to handle point class, rather than int
   public static void mergeSortX( point[] A ) {
      // Check if sorting is really necessary.
      if( A.length > 1 ) {
         // Determine size of array halves.
         int halfN = (int) Math.floor( A.length / 2 );
         // Init half 1.
         point[] B = new point[halfN];
         System.arraycopy( A, 0, B, 0, halfN );
         // Init half 2.
         point[] C = new point[ A.length - halfN ];
         System.arraycopy( A, halfN, C, 0, A.length - halfN );
         // Sort (recursively) halves 1 and 2.
         mergeSortX(B);
         mergeSortX(C);
         // Merge sorted halves (arrays B and C) into final sorted array (A).
         MergeSortedArraysX( B, C, A );
      }
   }

   public static void MergeSortedArraysY( point[] B, point[] C, point[] A ) {
      // Init temp variables.
      int i = 0;
      int j = 0;
      int k = 0;
      // Scanning sorted arrays B and C, while inserting in A.
      while( ( i < B.length ) && ( j < C.length ) ) {
         //Modified for sorting based on x coordinate
         if( B[i].lessThanEqualToY(C[j]) ) { A[k] = B[i]; i++; }
         else { A[k] = C[j]; j++; }
         k++;
      }
      // One scan has terminated, transfer remaining sorted data in A.
      if( i == B.length ) { System.arraycopy( C, j, A, k, C.length - j ); }
      else { System.arraycopy( B, i, A, k, B.length - i ); }
   }

   // Desc.: Mergesort algorithm (divide-by-2, iterative, bottom-up) to sort input array (non-decreasing).
   // Input: An array of integers (A).
   // Note: Recursive implementation.
   //Modified to handle point class, rather than int
   public static void mergeSortY( point[] A ) {
      // Check if sorting is really necessary.
      if( A.length > 1 ) {
         // Determine size of array halves.
         int halfN = (int) Math.floor( A.length / 2 );
         // Init half 1.
         point[] B = new point[halfN];
         System.arraycopy( A, 0, B, 0, halfN );
         // Init half 2.
         point[] C = new point[ A.length - halfN ];
         System.arraycopy( A, halfN, C, 0, A.length - halfN );
         // Sort (recursively) halves 1 and 2.
         mergeSortY(B);
         mergeSortY(C);
         // Merge sorted halves (arrays B and C) into final sorted array (A).
         MergeSortedArraysY( B, C, A );
      }
   }
}


