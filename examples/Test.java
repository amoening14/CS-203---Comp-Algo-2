package examples;


public class Test {
    public int a;
    public double b;
    public Test () {
        this.a = 123;
        this.b = 4.5;
    }

    public int increment(){
        this.a++;
        return this.a;
    }

    public void sort ( int[] c){
    //the output is "embedded" into the input since it's performing functions and modifying the reference 
    //even though there is no explicitly returned output
    }

    //Goal: return an array, a Test object and a float
    //Solution 1: custom class representing return type (ex. tuple)
    public double f(int[] c, Test d) {
        //...
        return 1.2;
    }

    public static void Main (String [] args){
    
    int[] A = new int[] {12, 23, 34, 45, 56, 67, 78};

    Test obj1 = new Test();

    //obj1.increment().increment(); //will only work if the increment class has a return referencing the modified object

    int [] D = new int[]{ 1, 2, 3, 4};
    resize(D);

    }

    //rewriting over an existing class, and overriding it (may need an @override expression)
    public String toString(){
        return "ABC";
    }

    //resizes the input array to double the length
    public static int[] resize (int [] B){
        int newSize = B.length * 2; //Standards are usually based on a multiplication factor
        int[] newArray = new int[newSize];
        for(int i = 0; i < B.length; i++){
            newArray[i] =B[i];
        }
        B = newArray;
        return B;
    }
}

