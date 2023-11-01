import java.util.Scanner;

public class midterm {
    private static int count1 = 0;
    private static int count2 = 0;
    private static int count3 = 0;
    public static void main (String [] args){
        Scanner scan = new Scanner (System.in);
        String cancel = "N";
        while (cancel != "Y"){
            System.out.print("Input Number -> ");
            boolean out = mysteryFunction( scan.nextInt() );
            if(out){
                System.out.println("It is true");
            } else {
                System.out.println("It is false");
            }
            //Add counters at each basic operation to verify summations
            int count = count1 + count2 + count3;
            System.out.println("Counters:\n1:" + count1 + " 2: " + count2 + " 3: " + count3 + " total " + count);
            count1 = 0;
            count2 = 0;
            count3 = 0;
        }
        scan.close();
        
    }
    public static boolean mysteryFunction( int v ) {
        int o = 0; 
        int n = v;
        //Iterates through each digit, counts number of digits
        count1++;
        while( n != 0 ) {
            count1++;
            o++;
            n = n / 10;
        }
        int t = v; 
        int s = 0;
        //Iterates through each digit and calculates that digit number raised to the number of digits of the input
        count2++;
        while( t != 0 ) {
            count2++;
            //r holds the current LSB digit
            int r = t % 10; 
            //P is the temp value that gets incrementally multiplied to equal the output
            int p = 1;
            if( o != 0 ) {
                for( int i = 0; i < o; i++ ) {
                    count3++;
                    p *= r;
                }
            }
            s += p;
            t = t / 10;
        }
        return s == v;
       }
}
