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
 * Read from the HubInputFile with the format:
 * One integer
 *      i.    The dimensional number of the starting hub (int)
 * 
 * Step 4:
 * CollectedOutputFile name is passed in through the command line as args[3]
 * Output to CollectedOutputFile with the format:
 * 1. e Lines, listing the Name of the anomaly collected with the Spider who
 *    is at the same Dimension (if one exists, space separated) followed by 
 *    the Dimension number for each Dimension in the route (space separated)
 * 
 * @author Seth Kelley
 */

public class CollectAnomalies {

    public HashMap<Integer , ArrayList<Integer>> adjacentList;
    public HashMap<Integer , ArrayList<Person>> spider;
    public int hub;
    public ArrayList<Path> path = new ArrayList<>();

    public static void main(String[] args) {

        if ( args.length < 4 ) {
            StdOut.println(
                "Execute: java -cp bin spiderman.CollectAnomalies <dimension INput file> <spiderverse INput file> <hub INput file> <collected OUTput file>");
                return;
        }

        // WRITE YOUR CODE HERE
        CollectAnomalies CA = new CollectAnomalies();

        Collider collider = new Collider();
        collider.CreateAdjacencyList(args[0]);

        CA.adjacentList = collider.adjacencyHashMap;

        SpiderPeople spiderPeople = new SpiderPeople();
        spiderPeople.createSpiderHashMap(args[1]);
        CA.spider = spiderPeople.spider;

        StdIn.setFile(args[2]);
        CA.hub = StdIn.readInt();

        CA.traversal();
        CA.printing(args[3]);
    }

    public void traversal()
    {
        boolean[] added = new boolean[getMax() + 1];
        Queue<Integer> queue = new LinkedList<>();
        HashMap<Integer , Integer> parent = new HashMap<>();
        queue.add(hub);
        added[hub] = true;

        while(!queue.isEmpty())
        {
            int current = queue.poll();
            ArrayList<Person> spiderPerson = spider.get(current);
            
            if(spiderPerson != null)
            {
                for(Person p : spiderPerson)
                {
                    if(p.currentDimension != hub && p.homeDimension != current)
                    {
                        ArrayList<Integer> aRoute = bfs(parent , current);

                        if(spiderPerson.get(0).homeDimension == current)
                        {
                            ArrayList<Integer> reverse = new ArrayList<>(aRoute);
                            Collections.reverse(reverse);
                            String matchingSpider = null;
                            for(Person per : spiderPerson)
                            {
                                if(per.currentDimension == current)
                                {
                                    matchingSpider = per.name;
                                    break;
                                }
                            }

                            if(matchingSpider != null)
                            {
                                path.add(new Path(p.name, matchingSpider, reverse));
                            }
                        }

                        else
                        {
                            ArrayList<Integer> palidrome = new ArrayList<>(aRoute);
                            for(int i = palidrome.size()-2 ; i>=0 ; i--)
                            {
                                palidrome.add(palidrome.get(i)); 
                            }
                            path.add(new Path(p.name , p.name , palidrome));
                        }
                    }
                }
            }

            ArrayList<Integer> n = adjacentList.get(current);
            for(int neighbor : n)
            {
                if(!added[neighbor])
                {
                    added[neighbor] = true;
                    parent.put(neighbor , current);
                    queue.add(neighbor);
                }
            }
        }
    }

    public ArrayList<Integer> bfs(HashMap<Integer , Integer> parent , int end)
    {
        ArrayList<Integer> route = new ArrayList<>();
        while(parent.containsKey(end))
        {
            route.add(end);
            end = parent.get(end);
        }
        route.add(end);
        Collections.reverse(route);
        return route;
    }

    public int getMax()
    {
        int max = 0;
        for(int dimension : adjacentList.keySet())
        {
            max = Math.max(max, dimension);

            if(adjacentList.get(dimension) != null)
            {
                for(int neighborDim : adjacentList.get(dimension))
                {
                    max = Math.max(max, neighborDim);
                }
            }
        }
        return max;
    }

    private void printing(String output)
    {
        StdOut.setFile(output);
        Set<String> duplicates = new HashSet<>();
        for(Path p : path)
        {
            String duplicate= p.getAnomalyName();
            if(!duplicates.contains(duplicate))
            {
                duplicates.add(duplicate);
                StdOut.print(p.toString());
            }
            else
            {
                path.remove(p);
            }
        }
    }
}
