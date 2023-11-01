package examples;

public class LabWeek2 {
    public static void main (String [] args){
        int out = pow(2,4);
        System.out.println("Power is " + out);
    }
    //When managing the accuracy of testing runtime, we need to know what other tasks are being performed
    //And what other computer functions and performance are
    //If you shift to a function calculator, it should result in the same shape/type of function as a time-based trend
    //Similarly, using a counter of only some functions over a total counter will yield the same type of fucntion but with lesser values
    //The shape of the function is what we care about

    //My own power function implementation
    //Assumptions: exp >= 0
    //Remember double uses more memory than float; similar to byte, short, int, and long
    //Comparing integers is usually 100% reliable. Comparing floats is reliant on mathematical precision
    //Ex. computation might return 1.0000000001. Which for calculations would be fine, but would return false when == 1.0
    //Alternative is subtracting difference and measuring difference (ex. x - y = float val; abs(floatVal) < 0.0001; <- Precision value)
    //As such easier to deal with ints for this problem
    public static int pow(int base, int exp){
        //Special cases
        if(exp == 0){ return 1; } //<= make sure not to compare integer values and float values
        else if(exp == 1){ return base; }
        //can add more special cases as deemed appropriate
        else{
            //As handled by the special cases, we know the exponent is -1 > exp or exp > 1
            int tmpRes = 1;
            for(int i = 0; i < exp; i++ ){
                tmpRes = tmpRes * base;
            }
            return tmpRes;
        }
    }
}
