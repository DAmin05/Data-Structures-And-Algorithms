package spiderman;

import java.util.*;

public class SpiderPeople {

    public HashMap<Integer , ArrayList<Person>> spider;

    public HashMap<Integer , ArrayList<Person>> createSpiderHashMap(String inputfile)
    {
        spider = new HashMap<>();

        StdIn.setFile(inputfile);
        int numOfLines = StdIn.readInt();

        for(int i=0 ; i<numOfLines ; i++)
        {
            int currentDimension = StdIn.readInt();
            StdIn.readChar();
            String name = StdIn.readString();
            StdIn.readChar();
            int signDimension = StdIn.readInt();

            Person person = new Person(currentDimension, name, signDimension);
            if(!spider.containsKey(currentDimension))
            {
                spider.put(currentDimension , new ArrayList<Person>());
            }
            spider.get(currentDimension).add(person);
        }

        return spider;
    }
}
