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
 * HubInputFile name is passed through the command line as args[2]
 * Read from the SpotInputFile with the format:
 * One integer
 *      i.    The dimensional number of the starting hub (int)
 * 
 * Step 4:
 * AnomaliesInputFile name is passed through the command line as args[3]
 * Read from the AnomaliesInputFile with the format:
 * 1. e (int): number of anomalies in the file
 * 2. e lines, each with:
 *      i.   The Name of the anomaly which will go from the hub dimension to their home dimension (String)
 *      ii.  The time allotted to return the anomaly home before a canon event is missed (int)
 * 
 * Step 5:
 * ReportOutputFile name is passed in through the command line as args[4]
 * Output to ReportOutputFile with the format:
 * 1. e Lines (one for each anomaly), listing on the same line:
 *      i.   The number of canon events at that anomalies home dimensionafter being returned
 *      ii.  Name of the anomaly being sent home
 *      iii. SUCCESS or FAILED in relation to whether that anomaly made it back in time
 *      iv.  The route the anomaly took to get home
 * 
 * @author Seth Kelley
 */

public class GoHomeMachine {

    public int hub;
    public HashMap<Integer , Integer> weight = new HashMap<>();
    public HashMap<Integer , ArrayList<Integer>> adjacentList;
    public HashMap<String , Integer> homeDimension = new HashMap<>();
    public HashMap<Integer , Dimension> dimensions = new HashMap<>();
    public ArrayList<Anomaly> outArray = new ArrayList<>();
    
    public static void main(String[] args) {

        if ( args.length < 5 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.GoHomeMachine <dimension INput file> <spiderverse INput file> <hub INput file> <anomalies INput file> <report OUTput file>");
                return;
        }

        // WRITE YOUR CODE HERE

        Collider collider = new Collider();
        collider.CreateAdjacencyList(args[0]);


        CollectAnomalies CA = new CollectAnomalies();
        CA.readHub(args[2]);

        GoHomeMachine GHM = new GoHomeMachine();
        GHM.readDimensions(args[0]);
        GHM.readSpiderVerse(args[1]);
        GHM.hub = CA.hub;
        GHM.adjacentList = collider.adjacencyHashMap;
        GHM.writeAnamoly(args[3] , args[4]);
    }

    private void readDimensions(String dimensionFile)
    {
        StdIn.setFile(dimensionFile);
        int numD = StdIn.readInt();
        StdIn.readInt();
        StdIn.readDouble();

        for(int i=0 ; i<numD ; i++)
        {
            int dimNum = StdIn.readInt();

            int cannonEvent = StdIn.readInt();

            int dimWeight = StdIn.readInt();

            dimensions.put(dimNum , new Dimension(dimNum, cannonEvent, dimWeight));

            weight.put(dimNum, dimWeight);
        }
    }

    public void readSpiderVerse(String SpiderVerse)
    {
        StdIn.setFile(SpiderVerse);

        int fileSize = StdIn.readInt();

        for(int i=0 ; i<fileSize ; i++)
        {
            int currentDim = StdIn.readInt();
            StdIn.readChar();
            String name = StdIn.readString();
            StdIn.readChar();
            int signDim = StdIn.readInt();

            Person person = new Person(currentDim, name, signDim);

            homeDimension.put(name , signDim);
        }
    }

    public HashMap<Integer , Integer> dijkstra()
    {
        HashMap<Integer , Integer> distance = new HashMap<>();

        HashMap<Integer , Integer> pred = new HashMap<>();

        PriorityQueue<Integer> fringe = new PriorityQueue<>(Comparator.comparingInt(distance::get));

        HashMap<Integer , Boolean> d = new HashMap<>();

        for(Integer dim : adjacentList.keySet())
        {
            distance.put(dim , Integer.MAX_VALUE);
            pred.put(dim , null);
            d.put(dim , false);
            weight.putIfAbsent(dim, 1);
        }

        distance.put(hub , 0);
        fringe.add(hub);
        while(!fringe.isEmpty())
        {
            int current = fringe.poll();
            if(d.get(current))
            {
                continue;
            }
            d.put(current , true);

            for(Integer next : adjacentList.get(current))
            {
                if(d.get(next))
                {
                    continue;
                }

                int weightSum = weight.get(current) + weight.get(next);
                int newD = distance.get(current) + weightSum;

                if(newD < distance.get(next))
                {
                    distance.put(next , newD);
                    pred.put(next , current);
                    fringe.add(next);
                }
            }
        }

        return pred;
    }

    public ArrayList<Integer> getPath(int home , HashMap<Integer , Integer> pred)
    {
        ArrayList<Integer> path = new ArrayList<>();
        while(home != hub && (Integer) home!=null)
        {
            path.add(0 , home);
            home = pred.get(home);
        }
        path.add(0, hub);
        return path;
    }

    public void markAnomaly(String outputfile)
    {
        StdOut.setFile(outputfile);
        for(Anomaly anomaly : outArray)
        {
            int cost = 0;
            for(int i=0 ; i<anomaly.path.size()-1 ; i++)
            {
                int from = anomaly.path.get(i);
                int to = anomaly.path.get(i+1);
                cost += weight.get(from) + weight.get(to); 
            }

            if(cost < anomaly.time)
            {
                StdOut.print(anomaly.cannon + " ");
                StdOut.print(anomaly.name + " ");
                StdOut.print("SUCCESS ");
                for(int a : anomaly.path)
                {
                    StdOut.print(a + " ");
                }
                StdOut.println();
            }

            else
            {
                weight.put(anomaly.home , weight.get(anomaly.home)-1);
                StdOut.print(anomaly.cannon-1 + " ");
                StdOut.print(anomaly.name + " ");
                StdOut.print("FAILED ");
                for(int a : anomaly.path)
                {
                    StdOut.print(a + " ");
                }
                StdOut.println();
            }
        }
    }

    public void writeAnamoly(String input , String output)
    {
        HashMap<Integer , Integer> pred = dijkstra();
        StdIn.setFile(input);
        int size = StdIn.readInt();
        for(int i=0 ; i<size ; i++)
        {
            String name = StdIn.readString();
            int time = StdIn.readInt();

            int home = homeDimension.getOrDefault(name, -1);
            if(home == -1) continue;
            int aSign = homeDimension.get(name);
            int cannon = dimensions.get(aSign).cannonEvent;

            ArrayList<Integer> path = getPath(home, pred);
            Anomaly anomaly = new Anomaly(name, home, cannon, time, path, pred);
            outArray.add(anomaly);
        }

        markAnomaly(output);
    }
}
