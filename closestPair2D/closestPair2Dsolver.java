//Alex Moening
//CS203 - Project 2
//Giusseppe Turini
//11-7-2023

//Main Executable
package closestPair2D;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public final class closestPair2Dsolver {
    private static Random rnd = new Random();

    public static void main (String [] args){
        Scanner scan = new Scanner (System.in);
        System.out.println("-------- Closest Pair Problem Solver --------");
        System.out.println("Please enter Number of Points:");
        //point [] P = generatePoints(scan.nextInt(), 100, 100);
        //printList(P);
        System.out.println("From File");
        String filepath = "closestPair2D/CSVDemo.csv";
        point [] p = readFile(filepath);
        writePoints(p , "closestPair2D/CSVDemoOut.csv");
    }

    /*
    we are allowed to make any assumptions on inputs, aside from the number of points.
    method of generation, range, decimal vs int, etc. is free game

    assumtion, all points range from 0 to 100 in x and y directions, as whole integer values

    randomly generate n 2D coordinate
    n must be >= 2
    if n <= 3, use brute force
    sort by x (mergesort?)
    seperately sort by y (in a second list) (perhaps a y sort func, that way can use p as primary storage?)
    find x median point
    split n/2 points into Pl for lower points and n/2 points into Pu for upper points
    do the same with q
    dl = recursive solve (Pl, Ql)
    du = recursive solve (Pu, Qu)
    d = min(dl, du)
    m = P(n/2 - 1).x
    collect all points in q where |x - m| < d into array S
    dminsq = d^2 
    for i = 0 to num - 2
    */
    

    //Generates integer coordinates bou
    public static point[] generatePoints(int n, int Xmax, int Ymax){
        if (n > Xmax * Ymax) {
            throw new IllegalArgumentException("Number of points cannot exceed the area of the grid");
        }
        ArrayList<point> pointList = new ArrayList<>();

        boolean duplicate = false;
        for(int i = 0; i < n; i++){
            int x = rnd.nextInt(Xmax);
            int y = rnd.nextInt(Ymax);
            for(point p : pointList){
                if (p.x == x && p.y == y){
                    duplicate = true;
                    break;
                }
            }
            if(!duplicate){
                pointList.add(new point(x,y));
            } else {
                i--;
            }
        }
        return pointList.toArray(new point[0]);
    }

    public static void writePoints(point [] pointList, String filepath){
        try (PrintWriter pw = new PrintWriter(new File(filepath))){
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < pointList.length; i++) {
                line.append(pointList[i].toStringNum());
                if (i != pointList.length - 1) {
                    line.append("\n");
                }
            }
            pw.write(line.toString());
        } catch (NullPointerException npe){
            npe.printStackTrace();
        } catch (IOException ioe){
            ioe.printStackTrace(); 
        }
        
    }

    public static void printList(point [] p){
        for(int i = 0; i < p.length; i++){
            System.out.println(p[i].toString());
        }
    }

    public static point[] readFile(String filepath){
        try{
            Scanner csv = new Scanner(new File(filepath));  
            String xString;
            String yString;
            int x;
            int y;
            ArrayList<point> p = new ArrayList<point>();
            csv.useDelimiter(",");
            while (csv.hasNextLine()) 
            {  
                String line = csv.nextLine();
                String[] values = line.split(",");
                if (values.length == 2) {
                    xString = values[0];
                    yString = values[1];
                    System.out.println(xString + "," + yString);
                    x = Integer.parseInt(xString);
                    y = Integer.parseInt(yString);
                    p.add(new point(x, y));
                } else {
                    System.err.println("Invalid number of columns in line: " + line);
                } 
            }   
            csv.close();
            return p.toArray(new point[0]);
        }catch (FileNotFoundException fnfe) {
            System.out.println("File Not Found");
            return null;
        }
    }
}