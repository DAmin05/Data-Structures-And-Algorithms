package spiderman;

import java.util.*;

public class Path {
    private String anomalyName;
    private String spiderName;
    private ArrayList<Integer> dimensions;

    public Path(String anomalyName , String spiderName , ArrayList<Integer> dimensions)
    {
        this.anomalyName = anomalyName;
        this.spiderName = spiderName;
        this.dimensions = dimensions;
    }

    public void setAnomalyName(String anomalyName)
    {
        this.anomalyName = anomalyName;
    }

    public String getAnomalyName()
    {
        return anomalyName;
    }

    public void setSpiderName(String spiderName)
    {
        this.spiderName = spiderName;
    }

    public String getSpiderName()
    {
        return spiderName;
    }

    public void setDimensionArray(ArrayList<Integer> dimension)
    {
        this.dimensions = dimension;
    }

    public ArrayList<Integer> getDimensionsArray()
    {
        return dimensions;
    }

    public String toString() {
        String r = " ";
        for(int dimension : dimensions) {
            r += dimension + " ";
        }
        r = r.trim();
        if(spiderName != null && anomalyName != null) {
            if(spiderName.equals(anomalyName)) {
                return anomalyName + " " + r + "\n";
            } else {
                return anomalyName + " " + spiderName + " " + r + "\n";
            }
        }
        return anomalyName + " " + r + "\n";
    }

}
