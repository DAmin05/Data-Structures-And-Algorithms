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
 * 
 * Step 2:
 * ClusterOutputFile name is passed in through the command line as args[1]
 * Output to ClusterOutputFile with the format:
 * 1. n lines, listing all of the dimension numbers connected to 
 *    that dimension in order (space separated)
 *    n is the size of the cluster table.
 * 
 * @author Seth Kelley
 */

public class Clusters {

    public HashMap<Integer , ArrayList<Integer>> clusters;
    public static void main(String[] args) {

        if ( args.length < 2 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.Clusters <dimension INput file> <collider OUTput file>");
                return;
        }

        // WRITE YOUR CODE HERE
        Clusters c = new Clusters();
        c.createHashMap(args[0]);
        c.printing(args[1]);

    }

    public void createHashMap(String fileName)
    {
        StdIn.setFile(fileName);
        int nunDimension = StdIn.readInt(); //(a) of the file
        int hashSize = StdIn.readInt(); //(b) of the file
        double threshold = StdIn.readDouble(); //(c) of the file

        clusters = new HashMap<>(hashSize);//hashtable for storing clusters
        int dimAdded = 0;//counters to keep track of dimensions and clusters

        for(int i = 0; i < nunDimension; i++) //go through each dimension
        {
            int dimensionNum = StdIn.readInt();
            int numCanonEvents = StdIn.readInt();
            int dimWeight = StdIn.readInt();
            
            if(!clusters.containsKey(dimensionNum % hashSize))
            {
                clusters.put(dimensionNum % hashSize, new ArrayList<>()); //create new cluster if one does not exist 
            }
            clusters.get(dimensionNum % hashSize).add(0, dimensionNum);//add dimension to cluster 
            dimAdded++;
            
            if(((double) dimAdded / clusters.size()) >= threshold) //rehash if load factor is equal to or more than threshold 
            {
                hashSize *=2; //new size of cluster table is doubled 
                rehashing(hashSize);
            }
        }
        connection();
    }

    private void rehashing(int size)
    {
        HashMap<Integer , ArrayList<Integer>> temp = new HashMap<>(size);
        
        for(ArrayList<Integer> list : clusters.values()) //go through clusters in original cluster table
        {
            for(int dimension : list) //go through dimension in original cluster table
            {
                if(!temp.containsKey(dimension % size)) //create new empty list if hashtable does not contain list 
                {
                    temp.put(dimension % size, new ArrayList<>());
                }
                temp.get(dimension % size).add(0, dimension);
            }
        }
        clusters.clear();//clear original cluster table
        clusters.putAll(temp);
    }

    private void connection()
    {
        for(int i=0 ; i<clusters.size() ; i++)
        {
            int connection1 = 0;
            int connection2 = 0;
            if(i==0)
            {
                connection1 = clusters.get(clusters.size()-1).get(0);
                connection2 = clusters.get(clusters.size()-2).get(0);
                clusters.get(i).add(connection1);
                clusters.get(i).add(connection2);
            }
            else if(i==1)
            {
                connection1 = clusters.get(i-1).get(0);
                connection2 = clusters.get(clusters.size()-1).get(0);
                clusters.get(i).add(connection1);
                clusters.get(i).add(connection2);
            }
            else
            {
                connection1 = clusters.get(i-1).get(0);
                connection2 = clusters.get(i-2).get(0);
                clusters.get(i).add(connection1);
                clusters.get(i).add(connection2);
            }
        }
    }

    private void printing(String fileName)
    {
        StdOut.setFile(fileName);
        for(ArrayList<Integer> list : clusters.values())
        {
            for(int dimension : list)
            {
                StdOut.print(dimension + " ");
            }
            StdOut.println();
        }
    }
}