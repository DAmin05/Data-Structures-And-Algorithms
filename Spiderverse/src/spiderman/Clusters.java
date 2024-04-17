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

    private HashMap<Integer , ArrayList<Integer>> clusters;
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

    private void createHashMap(String fileName)
    {
        StdIn.setFile(fileName);
        int a_numDimension = StdIn.readInt();
        int b_hashSize = StdIn.readInt();
        double c_threshold = StdIn.readDouble();

        clusters = new HashMap<>(b_hashSize);

        int dimAdded = 0;
        
        for(int i=0 ; i<a_numDimension ; i++)
        {
            int dimension = StdIn.readInt();
            int canonEvent = StdIn.readInt();
            int dimWeight = StdIn.readInt();

            if(!clusters.containsKey(dimension % b_hashSize))
            {
                clusters.put(dimension % b_hashSize , new ArrayList<>());
            }
            clusters.get(dimension % b_hashSize).add(0 , dimension);
            dimAdded++;

            if((double)dimAdded/clusters.size() >= c_threshold)
            {   
                int size = clusters.size()*2;
                rehashing(size);
            }

            connection();
        }
    }

    private void rehashing(int size)
    {
        HashMap<Integer , ArrayList<Integer>> temp = new HashMap<>(size);
        
        for(ArrayList<Integer> list : clusters.values())
        {
            for(int dimension : list)
            {
                if(!temp.containsKey(dimension % size))
                {
                    temp.put(dimension % size , new ArrayList<>());
                }
                temp.get(dimension % size).add(0 , dimension);
            }
        }
        clusters.clear();
        clusters.putAll(temp);
    }

    private void connection()
    {
        return;
    }

    private void printing(String fileName)
    {
        //StdOut.setFile(fileName);
        for(ArrayList<Integer> list : clusters.values())
        {
            for(int dimension : list)
            {
                System.out.print(dimension + " ");
            }
            System.out.println();
        }
    }
}