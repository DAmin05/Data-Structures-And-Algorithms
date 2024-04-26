package spiderman;

import java.util.*;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * DimensionInputFile name is passed through the command line as args[0]
 * Read from the DimensionsInputFile with the format:
 * 1. The first line with three numbers:
 *      i.    a (int): number of dimensions in the graph
 *      ii.   b (int): the initial size of the cluster table prior to rehashing
 *      iii.  c (double): the capacity(threshold) used to rehash the cluster table 
 * 2. a lines, each with:
 *      i.    The dimension number (int)
 *      ii.   The number of canon events for the dimension (int)
 *      iii.  The dimension weight (int)
 * 
 * Step 2:
 * SpiderverseInputFile name is passed through the command line as args[1]
 * Read from the SpiderverseInputFile with the format:
 * 1. d (int): number of people in the file
 * 2. d lines, each with:
 *      i.    The dimension they are currently at (int)
 *      ii.   The name of the person (String)
 *      iii.  The dimensional signature of the person (int)
 * 
 * Step 3:
 * SpotInputFile name is passed through the command line as args[2]
 * Read from the SpotInputFile with the format:
 * Two integers (line seperated)
 *      i.    Line one: The starting dimension of Spot (int)
 *      ii.   Line two: The dimension Spot wants to go to (int)
 * 
 * Step 4:
 * TrackSpotOutputFile name is passed in through the command line as args[3]
 * Output to TrackSpotOutputFile with the format:
 * 1. One line, listing the dimenstional number of each dimension Spot has visited (space separated)
 * 
 * @author Seth Kelley
 */

public class TrackSpot {
    
    public int startDimension;
    public int finalDim;
    public HashMap<Integer , ArrayList<Integer>> adjacencyList;
    public ArrayList<Integer> outputArray = new ArrayList<Integer>();

    public static void main(String[] args) {

        if ( args.length < 4 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.TrackSpot <dimension INput file> <spiderverse INput file> <spot INput file> <trackspot OUTput file>");
                return;
        }

        // WRITE YOUR CODE HERE
        String dimensionFile = args[0];
        String spiderFile = args[1];
        String spotFile = args[2];
        String trackspotOut = args[3];

        TrackSpot spot = new TrackSpot();

        StdIn.setFile(spotFile);//reading spot.in
        spot.startDimension = StdIn.readInt();
        spot.finalDim = StdIn.readInt();

        spot.accessingCollider(dimensionFile);
        spot.printing(trackspotOut);
    }

    public void accessingCollider(String dimensionFile)
    {
        Collider collider = new Collider(); //creating collider object to access the collider's adjacencyList
        collider.CreateAdjacencyList(dimensionFile); //using collider object creating collider's adjacencyList
        adjacencyList = collider.adjacencyHashMap; // setting trackSpot's adjacencyList to collider's adjacencyList
        trackSpot(startDimension); //depth-first search traversal
    }

    public void trackSpot(int currentDim)
    {
        outputArray.add(currentDim); //keeps track of dimension visited

        if(currentDim == finalDim)
        {
            return; //return's if the currentDimension is same as final dimension
        }

        for(int nextDim : adjacencyList.get(currentDim)) //looping through current dimension's arraylist, to access all other dimension in tht arraylist
        {
            if(!outputArray.contains(nextDim)) //if the nextDimension in the arrayList already exist in the outputArray then skip that dimension (nextDim)
            {
                trackSpot(nextDim);//recursion

                if(outputArray.get(outputArray.size()-1) == finalDim) //if the last element in the outputArray is same as finalDim then break the loop.
                {
                    return;
                }
            }
        }
        
    }
    

    private void printing(String outputFile)
    {
        StdOut.setFile(outputFile);
        for(int dimensions : outputArray)
        {
            StdOut.print(dimensions + " ");
        }
    }
}
