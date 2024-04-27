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
 * ColliderOutputFile name is passed in through the command line as args[2]
 * Output to ColliderOutputFile with the format:
 * 1. e lines, each with a different dimension number, then listing
 *       all of the dimension numbers connected to that dimension (space separated)
 * 
 * @author Seth Kelley
 */

public class Collider {

    public Clusters Clusters = new Clusters(); //creating Clusters object to access cluster hashmap

    public HashMap<Integer , ArrayList<Integer>> adjacencyHashMap;

    public Person[] person;

    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.Collider <dimension INput file> <spiderverse INput file> <collider OUTput file>");
                return;
        }

        // WRITE YOUR CODE HERE
        Collider collider = new Collider();
        collider.CreateAdjacencyList(args[0]); //dimension file
        collider.creatingPerson(args[1]); //spiderverse.in file
        collider.printing(args[2]); //output file
    }

    public void CreateAdjacencyList(String dimensionfile)
    {
        Clusters.createHashMap(dimensionfile);//creating HashMap of clusters

        adjacencyHashMap = new HashMap<>();//creating adjacencyList

        for(ArrayList<Integer> clustersArray : Clusters.clusters.values()) //accessing arraylist from the hashmap (clusters file)
        {
            for(int dimension : clustersArray)
            {
                if(!Clusters.clusters.containsKey(dimension))
                {
                    adjacencyHashMap.put(dimension, new ArrayList<Integer>()); //creating hashmap with all dimensions as keys
                }
                adjacencyHashMap.get(dimension).add(dimension); //adding dimension at the same dimension key
            }
        }
        connectingDimensions();
    }

    public void connectingDimensions()
    {
        for(ArrayList<Integer> clustersArray : Clusters.clusters.values())
        {
            int key = clustersArray.get(0);//accessing the first number of each line in cluster.out i.e. 1024, 65
            
            for(int dimension : clustersArray)
            {
                if(dimension == key)
                {
                    /*
                    if dimension and key match, then add the full line of clustes.out into the array. 
                    i.e. 1024 == 1024 then add the full line of 1024, 
                    excluding 1024 because it was already added to arraylist
                    This is making connections between d1->d2, d1->d3, d1->d4, etc
                    */
                    for(int i=1 ; i<clustersArray.size() ; i++) 
                    {
                        adjacencyHashMap.get(dimension).add(clustersArray.get(i));
                    }
                }
                else
                {
                    /*
                    * just add the first dimension of the of arraylist. 
                    * this is making connections between d2->d1, d3->d1, d4->d1, etc
                    */
                    adjacencyHashMap.get(dimension).add(clustersArray.get(0));
                }
            }
        }
    }

    public void creatingPerson(String spiderverse)
    {
        StdIn.setFile(spiderverse);
        int size = StdIn.readInt();

        person = new Person[size];

        for(int i=0 ; i<person.length ; i++)
        {
            int currentDimension = StdIn.readInt();
            StdIn.readChar();
            String name = StdIn.readString();
            StdIn.readChar();
            int homeDimension = StdIn.readInt();

            boolean isAnomaly = false;

            if(homeDimension != currentDimension)
            {
                isAnomaly = true;
            }

            Person p = new Person(currentDimension, name, homeDimension, isAnomaly);
            person[i] = p;
        }
    }

    private void printing(String outputfile)
    {
        StdOut.setFile(outputfile);
        for(ArrayList<Integer> list : adjacencyHashMap.values())
        {
            for(int dimension : list)
            {
                StdOut.print(dimension + " ");
            }
            StdOut.println();
        }
    }
}