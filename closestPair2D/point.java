package closestPair2D;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class point {
    
    public int x;
    public int y;

    //Constructor
    public point(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString(){
        return "(" + this.x + ", " + this.y + ")";
    }
    public String toStringNum(){
        return this.x + "," + this.y;
    }
    
}
