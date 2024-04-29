package spiderman;

import java.util.*;

public class Anomaly {
    public String name;
    public int home;
    public int cannon; 
    public int time;
    public ArrayList<Integer> path;
    public HashMap<Integer , Integer> pred;

    public Anomaly(String name , int home , int cannon , int time , ArrayList<Integer> path , HashMap<Integer , Integer> pred)
    {
        this.name = name;
        this.home = home;
        this.cannon = cannon;
        this.time = time;
        this.path = path;
        this.pred = pred;
    }
}
