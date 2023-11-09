package closestPair2D;

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

    //Used for sorting based on x
    public boolean lessThanEqualToX(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        point that = (point) o;
        return this.x <= that.x;
    }
     //Used for sorting based on Y
    public boolean lessThanEqualToY(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        point that = (point) o;
        return this.y <= that.y;
    }
    
}
