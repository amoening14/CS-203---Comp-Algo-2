//Alex Moening
//CS203 - Project 2
//Giusseppe Turini
//11-7-2023

//Main Executable
package closestPair2D;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.System;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;


import javax.naming.LimitExceededException;

public final class closestPair2Dsolver {
    private static Random rnd = new Random();

    public static void main (String [] args){
        System.out.println("-------- Closest Pair Problem Solver --------");
        Scanner scan = new Scanner (System.in);
        int userIn = 0;
        while (userIn != 1 && userIn != 2){
            System.out.println("Enter 1 to generate random numbers or 2 to pull from file:");
            userIn = scan.nextInt();
            try {
                if (userIn == 1){
                    System.out.println("Please enter Number of Points:");
                    point [] pGen = generatePoints(scan.nextInt(), 100, 100);
                    point [] pX = pGen.clone();
                    point [] pY = pGen.clone();
                    Sorting.mergeSortX(pX);
                    Sorting.mergeSortY(pY);
                    printArray(pGen);
                } else if (userIn == 2){
                    point [] pFile = readFile("closestPair2D/CSVDemoEven.csv");
                    point [] pX = pFile.clone();
                    point [] pY = pFile.clone();
                    Sorting.mergeSortX(pX);
                    Sorting.mergeSortY(pY);
                    printArray(pFile);
                    solveClosestPair(pX, pY);
                } else {
                    System.out.println("Invalid input please try again;");
                    userIn= scan.nextInt();
                }
            } catch (InputMismatchException ime) {
                System.out.println("Improper input, please try again");
                String garbage = scan.next();
            }   
        }     
    }

    /*
    we are allowed to make any assumptions on inputs, aside from the number of points.
    method of generation, range, decimal vs int, etc. is free game

    assumtion, all points range from 0 to 100 in x and y directions, as whole integer values

    --randomly generate n 2D coordinate
    --n must be >= 2
    if n <= 3, use brute force
    --sort by x (mergesort?)
    --seperately sort by y (in a second list) (perhaps a y sort func, that way can use p as primary storage?)
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
        try{
            if (n > Xmax * Ymax) {
                throw new IllegalArgumentException("Number of points cannot exceed the area of the grid");
            } else if  (n < 2) {
                throw new LimitExceededException("Number of Points must be at least 2");
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
            point [] pointArray = pointList.toArray(new point[0]);
            writePoints(pointArray , "closestPair2D/CSVDemoOut.csv");
            return pointArray;
        } catch (IllegalArgumentException iae) {
            System.out.println(iae.getMessage());
        } catch (LimitExceededException lee) {
            System.out.println(lee.getMessage());
        }
        return new point [0];
        
    }

    public static void writePoints(point [] pointArray, String filepath){
        try (PrintWriter pw = new PrintWriter(new File(filepath))){
            StringBuilder line = new StringBuilder();
            if(pointArray.length < 1){
                throw new NullPointerException("point list is empty");
            }
            for (int i = 0; i < pointArray.length; i++) {
                line.append(pointArray[i].toStringNum());
                if (i != pointArray.length - 1) {
                    line.append("\n");
                }
            }
            pw.write(line.toString());
        } catch (NullPointerException npe){
            System.out.println("Write to csv failed, " + npe.getMessage());
        } catch (IOException ioe){
            ioe.printStackTrace(); 
        }
        
    }

    public static point[] readFile(String filepath){
        try{
            Scanner csv = new Scanner(new File(filepath));  
            String xString;
            String yString;
            int x;
            int y;
            ArrayList<point> pointList = new ArrayList<point>();
            csv.useDelimiter(",");
            while (csv.hasNextLine()) 
            {  
                String line = csv.nextLine();
                String[] values = line.split(",");
                if (values.length == 2) {
                    xString = values[0];
                    yString = values[1];
                    x = Integer.parseInt(xString);
                    y = Integer.parseInt(yString);
                    pointList.add(new point(x, y));
                } else {
                    System.err.println("Invalid number of columns in line: " + line);
                } 
            }   
            csv.close();
            if (pointList.size() >= 2){
                return pointList.toArray(new point[0]);
            } else {
                throw new LimitExceededException("Number of Points must be at least 2");
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("File Not Found");
            return new point [0];
        } catch (LimitExceededException lee) {
            System.out.println(lee.getMessage());
            return new point [0];
        } catch (InputMismatchException ime) {
            System.out.println(ime.getMessage());
            return new point [0];
        }
    }

    public static void printArray(point [] pointArray){
        try {
            for(int i = 0; i < pointArray.length; i++){
                System.out.println(pointArray[i].toString());
            }
        } catch (NullPointerException npe){
            System.out.println("Print failed, " + npe.getMessage());
        }
    }

    public static point [] solveClosestPair(point [] P, point [] Q){
        double d;
        double dmin = Double.MAX_VALUE;
        point [] minPoints = new point [2];
        if (P.length <= 3){
            //check all combinations of points and return the min (bfs)
            //Recursive stop
            for (int i = 0; i < P.length - 1; i++){
                for (int j = i + 1; j < P.length; j++){
                    d = Point2D.distance(P[i].x, P[i].y, P[j].x, P[j].y);
                    if (d < dmin) {
                        dmin = d;
                        minPoints[0] = P[i];
                        minPoints[1] = P[j];
                    }
                }
            }
            return minPoints;
        } else {
            //Recursive performance section
            if (P.length%2 == 0){
                int mid = (P.length)/2;
                point [] Pl = new point [mid];
                point [] Ql = new point [mid];
                point [] Pr = new point [mid];
                point [] Qr = new point [mid];
                System.arraycopy(P, 0, Pl, 0, mid);
                System.arraycopy(P, mid, Pr, 0, mid);
                Ql = Pl.clone();
                Sorting.mergeSortY(Ql);
                Qr = Pr.clone();
                Sorting.mergeSortY(Qr);
            } else if (P.length%2 == 1){
                int mid = (P.length)/2;
                point [] Pl = new point [mid];
                point [] Ql = new point [mid];
                point [] Pr = new point [mid + 1];
                point [] Qr = new point [mid + 1];
                System.arraycopy(P, 0, Pl, 0, mid);
                System.arraycopy(P, mid, Pr, 0, mid + 1);
                Ql = Pl.clone();
                Sorting.mergeSortY(Ql);
                Qr = Pr.clone();
                Sorting.mergeSortY(Qr);
            }
            
            return minPoints;
        }
    }
}